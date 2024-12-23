package com.github.JLQusername.account.controller;

import com.github.JLQusername.common.domain.Result;
import com.github.JLQusername.account.domain.dto.CustomerDTO;
import com.github.JLQusername.account.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String hello(){
        return "hello";
    }

    @PostMapping("/create")
    public Result createAccount(@RequestBody CustomerDTO customerDTO){
        boolean result = customerService.createAccount(customerDTO);
        return result ? Result.success() : Result.error("该手机号或身份证号已开户");
    }

    @PatchMapping("/risk_level")
    public Result updateRiskLevel(@RequestParam Long fundAccount, @RequestParam int riskLevel){
        boolean result = customerService.updateRiskLevel(fundAccount, riskLevel);
        return result ? Result.success() : Result.error("更新失败");
    }





}
