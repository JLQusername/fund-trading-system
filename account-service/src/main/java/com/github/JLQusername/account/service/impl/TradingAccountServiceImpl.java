package com.github.JLQusername.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.JLQusername.account.domain.Bankcard;
import com.github.JLQusername.account.domain.TradingAccount;
import com.github.JLQusername.account.domain.dto.BankcardDTO;
import com.github.JLQusername.account.domain.vo.BankcardVO;
import com.github.JLQusername.account.mapper.BankcardMapper;
import com.github.JLQusername.account.mapper.TradingAccountMapper;
import com.github.JLQusername.account.service.TradingAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradingAccountServiceImpl implements TradingAccountService {

    private final BankcardMapper bankcardMapper;
    private final TradingAccountMapper tradingAccountMapper;

    @Override
    public long addBankcard(BankcardDTO bankcardDTO) {
        QueryWrapper<TradingAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bankcard_number", bankcardDTO.getBankcardNumber())
                .eq("is_deleted", false);
        int count = tradingAccountMapper.selectCount(queryWrapper);
        if(count > 0)
            return 1;// 该卡号现在已有交易账户
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fund_account", bankcardDTO.getFundAccount())
                .eq("bankcard_number", bankcardDTO.getBankcardNumber());
        TradingAccount tradingAccount = tradingAccountMapper.selectOne(queryWrapper);
        if (tradingAccount == null) {
            tradingAccount = new TradingAccount(null, bankcardDTO.getFundAccount(),
                    bankcardDTO.getBankcardNumber(), false);
            tradingAccountMapper.insert(tradingAccount);
            if(bankcardMapper.selectCount(new QueryWrapper<Bankcard>()
                    .eq("bankcard_number", bankcardDTO.getBankcardNumber())) == 0)
                bankcardMapper.insert(new Bankcard(bankcardDTO.getBankcardNumber(), 200));
            //返回插入后的主键
            return tradingAccount.getTradingAccountId();
        } else if(tradingAccount.isDeleted()){
            tradingAccount.setDeleted(false);
            tradingAccountMapper.updateById(tradingAccount);
            return tradingAccount.getTradingAccountId();
        }else{
            return 2; // 未知错误
        }
    }

    @Override
    public boolean deleteBankcard(long tradingAccountId) {
        UpdateWrapper<TradingAccount> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trading_account_id", tradingAccountId)
                .set("is_deleted", true);
        return tradingAccountMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public List<BankcardVO> getBankcards(long fundAccount) {
        QueryWrapper<TradingAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fund_account", fundAccount).eq("is_deleted", false);
        return tradingAccountMapper.selectList(queryWrapper).stream()
                .map(BankcardVO::new).collect(Collectors.toList());
    }
}
