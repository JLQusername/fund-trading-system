package com.github.JLQusername.account.controller;

import com.github.JLQusername.account.domain.Customer;
import com.github.JLQusername.account.domain.TradingAccount;
import com.github.JLQusername.account.domain.dto.BankcardDTO;
import com.github.JLQusername.account.service.TradingAccountService;
import com.github.JLQusername.common.domain.Result;
import com.github.JLQusername.account.domain.dto.CustomerDTO;
import com.github.JLQusername.account.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final CustomerService customerService;
    private final TradingAccountService tradingAccountService;

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

    @PostMapping("/add_bankcard")
    public Result addBankcard(@RequestBody BankcardDTO bankcardDTO){
        long result = tradingAccountService.addBankcard(bankcardDTO);
        if(result > 2)
            return Result.success(result);
        else if(result == 1)
            return Result.error("该卡号已有交易账户");
        return Result.error("未知错误");
    }

    @DeleteMapping("/delete_bankcard")
    public Result deleteBankcard(@RequestParam long tradingAccountId){
        return tradingAccountService.deleteBankcard(tradingAccountId) ? Result.success() : Result.error("删除失败");
    }

    @GetMapping("/bankcards")
    public Result getBankcards(@RequestParam long fundAccount){
        return Result.success(tradingAccountService.getBankcards(fundAccount));
    }
}
