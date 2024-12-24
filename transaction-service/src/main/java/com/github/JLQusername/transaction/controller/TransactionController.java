package com.github.JLQusername.transaction.controller;

import com.github.JLQusername.common.domain.Result;
import com.github.JLQusername.transaction.domain.Redemption;
import com.github.JLQusername.transaction.domain.dto.RedemptionDTO;
import com.github.JLQusername.transaction.domain.dto.SubscriptionDTO;
import com.github.JLQusername.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/subscription")
    public Result submitSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        int result = transactionService.submitSubscription(subscriptionDTO);
        if(result == 0)
            return Result.error("风险等级不匹配");
        else if(result == 1)
            return Result.error("余额不足");
        return Result.success();
    }

    @PostMapping("/redemption")
    public Result submitRedemption(@RequestBody RedemptionDTO redemptionDTO) {
        return transactionService.submitRedemption(redemptionDTO) ? Result.success() : Result.error("份额不足");
    }

    @PostMapping("/cancel")
    public Result cancelTransaction(@RequestParam long transactionId) {
        return transactionService.cancelTransaction(transactionId) ? Result.success() : Result.error("当前交易已超时");
    }

}
