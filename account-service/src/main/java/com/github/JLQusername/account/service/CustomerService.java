package com.github.JLQusername.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.JLQusername.account.domain.Customer;
import com.github.JLQusername.account.domain.dto.CustomerDTO;

public interface CustomerService extends IService<Customer> {
    boolean createAccount(CustomerDTO customerDTO);

    boolean updateRiskLevel(Long fundAccount, int riskLevel);
}
