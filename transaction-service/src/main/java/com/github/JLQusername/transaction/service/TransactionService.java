package com.github.JLQusername.transaction.service;

import com.github.JLQusername.transaction.domain.Holding;
import com.github.JLQusername.transaction.domain.dto.RedemptionDTO;
import com.github.JLQusername.transaction.domain.dto.SubscriptionDTO;

public interface TransactionService {
    int submitSubscription(SubscriptionDTO subscriptionDTO);
    Holding getHolding(long tradingAccountId, int productId);
    boolean submitRedemption(RedemptionDTO redemptionDTO);
    boolean cancelTransaction(long transactionId);
}
