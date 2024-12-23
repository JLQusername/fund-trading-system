package com.github.JLQusername.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.JLQusername.account.domain.Customer;
import com.github.JLQusername.account.domain.dto.CustomerDTO;
import com.github.JLQusername.account.domain.dto.LoginDTO;
import com.github.JLQusername.account.domain.vo.CustomerVO;

import java.util.List;

public interface CustomerService extends IService<Customer> {
    boolean createAccount(CustomerDTO customerDTO);

    boolean updateRiskLevel(Long fundAccount, int riskLevel);

    boolean updateInfo(Customer customer);

    List<CustomerVO> getCustomers(int pageNum, int pageSize, String key);

}
