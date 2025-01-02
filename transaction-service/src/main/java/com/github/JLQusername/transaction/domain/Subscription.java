package com.github.JLQusername.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Subscription extends Transaction{
    private double subscriptionAmount;

    public Subscription(Long transactionId, long tradingAccountId, int productId,
                        Date transactionTime, boolean isCancel, double subscriptionAmount, long fundAccount) {
        super(transactionId, tradingAccountId,fundAccount, productId, transactionTime, isCancel);
        this.subscriptionAmount = subscriptionAmount;
    }
}
