package com.github.JLQusername.transaction.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public abstract class Transaction {
    @TableId(type = IdType.ASSIGN_ID)
    private Long transactionId;
    private long tradingAccountId;
    private long fundAccount;
    private int productId;
    private Date applicationTime;
    private boolean isCancel;

    public Transaction() {
    }
}
