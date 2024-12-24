package com.github.JLQusername.settle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.JLQusername.api.OurSystem;
import com.github.JLQusername.settle.mapper.SystemMapper;
import com.github.JLQusername.settle.service.SettleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettleServiceImpl implements SettleService {
    private final SystemMapper systemMapper;

    @Override
    public OurSystem getSystem() {
        QueryWrapper<OurSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("transaction_date").last("limit 1");
        return systemMapper.selectOne(queryWrapper);
    }

}
