package com.github.JLQusername.account.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Customer {
    @TableId(type = IdType.ASSIGN_ID)
    private Long fundAccount;
    private String name;
    private String phoneNumber;
    private String password;
    private String idNumber;
    private int riskLevel;
}
