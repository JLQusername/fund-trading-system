package com.github.JLQusername.account.domain.vo;

import com.github.JLQusername.account.domain.Customer;
import lombok.Data;

@Data
public class CustomerVO {
    private Long fundAccount;
    private String name;
    private String phoneNumber;
    private int riskLevel;

    public CustomerVO(Customer customer) {
        this.fundAccount = customer.getFundAccount();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
        this.riskLevel = customer.getRiskLevel();
    }
}
