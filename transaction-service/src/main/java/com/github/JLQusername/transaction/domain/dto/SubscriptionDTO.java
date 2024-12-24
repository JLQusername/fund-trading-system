package com.github.JLQusername.transaction.domain.dto;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private long tradingAccountId;
    private int productId;
    private int riskLevel;
    private double amount;
}
