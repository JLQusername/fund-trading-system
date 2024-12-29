package com.github.JLQusername.transaction.domain.dto;

import lombok.Data;

@Data
public class RedemptionDTO {
    private String tradingAccountId;
    private int productId;
    private double shares;
}
