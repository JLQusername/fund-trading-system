package com.github.JLQusername.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@AllArgsConstructor
public class Bankcard {
    @TableId
    private String bankcardNumber;
    private double balance;
}
