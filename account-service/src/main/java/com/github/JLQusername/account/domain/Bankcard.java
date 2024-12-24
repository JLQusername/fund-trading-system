package com.github.JLQusername.account.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bankcard {
    private String bankcardNumber;
    private double balance;
}
