package com.github.JLQusername.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.JLQusername.account.domain.Customer;
import com.github.JLQusername.account.domain.dto.CustomerDTO;
import com.github.JLQusername.account.mapper.CustomerMapper;
import com.github.JLQusername.account.service.CustomerService;
import com.github.JLQusername.common.utils.Md5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public boolean createAccount(CustomerDTO customerDTO) {
        //查询phoneNumber或idNumber是否已在数据库存在
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", customerDTO.getPhoneNumber())
                .or().eq("id_number", customerDTO.getIdNumber());
        if (count(queryWrapper) > 0)
            return false;
        customerDTO.setPassword(Md5Util.getMD5String(customerDTO.getPassword()));
        System.out.println(customerDTO.getPassword());
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return save(customer);
    }

    @Override
    public boolean updateRiskLevel(Long fundAccount, int riskLevel) {
        UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("fund_account", fundAccount)
                .set("risk_level", riskLevel);
        return update(updateWrapper);
    }

}
