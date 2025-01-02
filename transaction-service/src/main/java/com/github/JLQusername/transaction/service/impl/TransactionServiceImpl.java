package com.github.JLQusername.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.JLQusername.api.Bankcard;
import com.github.JLQusername.api.OurSystem;
import com.github.JLQusername.api.client.AccountClient;
import com.github.JLQusername.api.client.ProductClient;
import com.github.JLQusername.api.client.SettleClient;
import com.github.JLQusername.transaction.domain.Holding;
import com.github.JLQusername.transaction.domain.Redemption;
import com.github.JLQusername.transaction.domain.Subscription;
import com.github.JLQusername.transaction.domain.dto.RedemptionDTO;
import com.github.JLQusername.transaction.domain.dto.SubscriptionDTO;
import com.github.JLQusername.transaction.mapper.HoldingMapper;
import com.github.JLQusername.transaction.mapper.RedemptionMapper;
import com.github.JLQusername.transaction.mapper.SubscriptionMapper;
import com.github.JLQusername.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final SubscriptionMapper subscriptionMapper;
    private final RedemptionMapper redemptionMapper;
    private final HoldingMapper holdingMapper;
    private final ProductClient productClient;
    private final AccountClient accountClient;
    private final SettleClient settleClient;

    @Override
    public long submitSubscription(SubscriptionDTO subscriptionDTO) {
//        if(productClient.getLevel(subscriptionDTO.getProductId()) > subscriptionDTO.getRiskLevel())
//            return 0; // 风险等级不够
        Bankcard bankcard = accountClient.getBankcard(Long.parseLong(subscriptionDTO.getTradingAccountId()));
        if(bankcard.getBalance() < subscriptionDTO.getAmount())
            return 1L; // 余额不足
        Date date = getDate();
        bankcard.setBalance(bankcard.getBalance() - subscriptionDTO.getAmount());
        Subscription subscription = new Subscription(null, subscriptionDTO.getAmount(),
                Long.parseLong(subscriptionDTO.getTradingAccountId()), subscriptionDTO.getFundAccount(),
                subscriptionDTO.getProductId(),subscriptionDTO.getProductName(),date,false);
        subscriptionMapper.insert(subscription);
        accountClient.updateBalance(bankcard);
        return subscription.getTransactionId();
    }

    private Date getDate() {
        Date date = settleClient.getSystem().getTransactionDate();
        if(settleClient.getSystem().isHasStoppedApplication()){
            // 判断date是周几
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            // dayOfWeek 的值范围是 1（星期日）到 7（星期六）
            if(day == 6)
                date.setTime(date.getTime() + 24 * 60 * 60 * 1000 * 3);
            else
                date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
        }
        return date;
    }

    @Override
    public Holding getHolding(long tradingAccountId, int productId) {
        return holdingMapper.selectOne(new QueryWrapper<Holding>()
                .eq("trading_account_id", tradingAccountId).eq("product_id", productId));
    }

    @Override
    public long submitRedemption(RedemptionDTO redemptionDTO) {
        Holding holding = getHolding(Long.parseLong(redemptionDTO.getTradingAccountId()), redemptionDTO.getProductId());
        if(holding == null || holding.getShares() < redemptionDTO.getShares())
            return 1L; //份额不足
        Date date = getDate();
        Redemption redemption = new Redemption(null,redemptionDTO.getShares(),
                Long.parseLong(redemptionDTO.getTradingAccountId()),redemptionDTO.getProductId(),date,
                false,redemptionDTO.getFundAccount(), redemptionDTO.getProductName());
        redemptionMapper.insert(redemption);
        holding.setShares(holding.getShares() - redemptionDTO.getShares());
        holdingMapper.updateById(holding);
        return redemption.getTransactionId();
    }

    @Override
    public boolean cancelTransaction(long transactionId) {
//        Subscription subscription = subscriptionMapper.selectById(transactionId);
//        OurSystem system = settleClient.getSystem();
//        if(subscription != null) {
//            if(system.isHasExportedApplicationData() ||
//                    system.getTransactionDate().getTime() - subscription.getApplicationTime().getTime() >= 1000 * 60 * 60 * 24)
//                return false;
//            Bankcard bankcard = accountClient.getBankcard(subscription.getTradingAccountId());
//            bankcard.setBalance(bankcard.getBalance() + subscription.getSubscriptionAmount());
//            accountClient.updateBalance(bankcard);
//            return subscriptionMapper.insert(new Subscription(null,transactionId,subscription.getProductId()
//                    ,system.getTransactionDate(),true, subscription.getSubscriptionAmount())) > 0;
//        }else {
//            Redemption redemption = redemptionMapper.selectById(transactionId);
//            if(system.isHasExportedApplicationData() ||
//                    system.getTransactionDate().getTime() - redemption.getApplicationTime().getTime() >= 1000 * 60 * 60 * 24)
//                return false;
//            Holding holding = getHolding(redemption.getTradingAccountId(), redemption.getProductId());
//            holding.setShares(holding.getShares() + redemption.getRedemptionShares());
//            holdingMapper.updateById(holding);
//            return redemptionMapper.insert(new Redemption(null, redemption.getRedemptionShares(),transactionId
//                    ,redemption.getProductId(),system.getTransactionDate(),true)) > 0;
//        }
        Subscription subscription = subscriptionMapper.selectById(transactionId);
        OurSystem system = settleClient.getSystem();
        if(subscription != null) {
            if(system.isHasExportedApplicationData() ||
                    system.getTransactionDate().getTime() - subscription.getApplicationTime().getTime() >= 1000 * 60 * 60 * 24)
                return false;
            Bankcard bankcard = accountClient.getBankcard(subscription.getTradingAccountId());
            bankcard.setBalance(bankcard.getBalance() + subscription.getSubscriptionAmount());
            accountClient.updateBalance(bankcard);
            UpdateWrapper<Subscription> wrapper = new UpdateWrapper<>();
            wrapper.eq("transaction_id", transactionId).set("is_cancel", true);
            return subscriptionMapper.update(null, wrapper) > 0;
        }else {
            Redemption redemption = redemptionMapper.selectById(transactionId);
            if(system.isHasExportedApplicationData() ||
                    system.getTransactionDate().getTime() - redemption.getApplicationTime().getTime() >= 1000 * 60 * 60 * 24)
                return false;
            Holding holding = getHolding(redemption.getTradingAccountId(), redemption.getProductId());
            holding.setShares(holding.getShares() + redemption.getRedemptionShares());
            holdingMapper.updateById(holding);
            UpdateWrapper<Redemption> wrapper = new UpdateWrapper<>();
            wrapper.eq("transaction_id", transactionId).set("is_cancel", true);
            return redemptionMapper.update(null, wrapper) > 0;
        }
    }
}
