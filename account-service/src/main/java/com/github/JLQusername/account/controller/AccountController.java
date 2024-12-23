package com.github.JLQusername.account.controller;

import com.github.JLQusername.account.domain.Customer;
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

    @PutMapping("/info")
    public Result updateInfo(@RequestBody Customer customer){
        boolean result = customerService.updateInfo(customer);
        return result ? Result.success() : Result.error("该手机号或身份证号已有用户使用");
    }

    @GetMapping("/customers")
    public Result getCustomers(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "") String key){
        return Result.success(customerService.getCustomers(pageNum, pageSize, key));
    }
}
