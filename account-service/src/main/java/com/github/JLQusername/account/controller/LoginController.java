package com.github.JLQusername.account.controller;

import com.github.JLQusername.account.domain.dto.LoginDTO;
import com.github.JLQusername.account.service.LoginService;
import com.github.JLQusername.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginDTO loginDTO) {
        int login = loginService.checkPassword(loginDTO);
        if(login == 0)
            return Result.success();
        else if(login == 1)
            return Result.error("手机号没有匹配的账号");
        else if(login == 2)
            return Result.error("密码错误");
        else if(login == 3)
            return Result.error("用户类型错误");
        return Result.error("未知错误");
    }

    @GetMapping("/verification")
    public Result validPhoneNumber(@RequestParam String phoneNumber) {
        boolean validPhoneNumber = loginService.isVaildPhoneNumber(phoneNumber);
        return validPhoneNumber ? Result.success() : Result.error("手机号没有匹配的账号");
    }

    @GetMapping("/admin_verification")
    public Result validAdminPhoneNumber(@RequestParam String phoneNumber) {
        boolean validPhoneNumber = loginService.isVaildAdminPhoneNumber(phoneNumber);
        return validPhoneNumber ? Result.success() : Result.error("手机号没有匹配的账号");
    }

    @PatchMapping("/change_password")
    public Result changePassword(@RequestBody LoginDTO loginDTO) {
        boolean changePassword = loginService.changePassword(loginDTO);
        return changePassword ? Result.success() : Result.error("修改密码失败");
    }

}

