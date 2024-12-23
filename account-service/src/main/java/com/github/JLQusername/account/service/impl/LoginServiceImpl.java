package com.github.JLQusername.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.JLQusername.account.domain.Admin;
import com.github.JLQusername.account.domain.Customer;
import com.github.JLQusername.account.domain.dto.LoginDTO;
import com.github.JLQusername.account.mapper.AdminMapper;
import com.github.JLQusername.account.mapper.CustomerMapper;
import com.github.JLQusername.account.service.LoginService;
import com.github.JLQusername.common.utils.Md5Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final CustomerMapper customerMapper;

    private final AdminMapper adminMapper;

    @Override
    public int checkPassword(LoginDTO loginDTO) {
        String password;
        if (loginDTO.getUserType() == 1) { // Customer
            QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone_number", loginDTO.getPhoneNumber());
            Customer customer = customerMapper.selectOne(queryWrapper);
            if (customer == null)
                return 1; // 用户不存在
            password = customer.getPassword();
        } else if (loginDTO.getUserType() == 2) { // Admin
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone_number", loginDTO.getPhoneNumber());
            Admin admin = adminMapper.selectOne(queryWrapper);
            if (admin == null)
                return 1; // 用户不存在
            password = admin.getPassword();
        } else
            return 3; // 非法的用户类型
        return Md5Util.checkPassword(loginDTO.getPassword(), password) ? 0 : 2; // 密码错误
    }

    @Override
    public boolean isVaildPhoneNumber(String phoneNumber) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phoneNumber);
        return customerMapper.selectCount(queryWrapper) == 1; // 该手机号码存在且唯一
    }

    @Override
    public boolean isVaildAdminPhoneNumber(String phoneNumber) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phoneNumber);
        return adminMapper.selectCount(queryWrapper) == 1; // 该手机号码存在且唯一
    }

    @Override
    public boolean changePassword(LoginDTO loginDTO) {
        if(loginDTO.getUserType() == 1)
            return customerMapper.update(null, new UpdateWrapper<Customer>().eq("phone_number",
                        loginDTO.getPhoneNumber()).set("password", Md5Util.getMD5String(loginDTO.getPassword()))) == 1;
        else if(loginDTO.getUserType() == 2)
            return adminMapper.update(null, new UpdateWrapper<Admin>().eq("phone_number",
                        loginDTO.getPhoneNumber()).set("password", Md5Util.getMD5String(loginDTO.getPassword()))) == 1;
        else return false;
    }
}
