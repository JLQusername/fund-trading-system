package com.github.JLQusername.transaction.domain.dto;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private String tradingAccountId;
    private int productId;
    private double amount;
}
