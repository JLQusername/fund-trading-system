package com.github.JLQusername.transaction.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Redemption extends Transaction{
    private double redemptionShares;

    public Redemption(Long transactionId, double redemptionShares,long tradingAccountId,
                      int productId,Date transactionTime, boolean isCancel, long fundAccount, String productName) {
        super(transactionId, tradingAccountId, fundAccount,productId,productName, transactionTime, isCancel);
        this.redemptionShares = redemptionShares;
    }
}
