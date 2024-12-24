package com.github.JLQusername.transaction.domain.dto;

import lombok.Data;

@Data
public class RedemptionDTO {
    private long tradingAccountId;
    private int productId;
    private double shares;
}
