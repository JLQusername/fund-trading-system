package com.github.JLQusername.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.JLQusername.product.domain.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends  BaseMapper<Product> {
}
