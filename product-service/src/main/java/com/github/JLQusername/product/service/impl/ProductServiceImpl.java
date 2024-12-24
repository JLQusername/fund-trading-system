package com.github.JLQusername.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.JLQusername.product.domain.Product;
import com.github.JLQusername.product.mapper.ProductMapper;
import com.github.JLQusername.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
