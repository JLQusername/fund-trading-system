package com.github.JLQusername.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.JLQusername.api.OurSystem;
import com.github.JLQusername.api.client.SettleClient;
import com.github.JLQusername.product.domain.Product;
import com.github.JLQusername.product.mapper.ProductMapper;
import com.github.JLQusername.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private SettleClient settleClient;

    @Override
    public List<Product> list() {
        return super.list(null); // 使用 MyBatis Plus 提供的默认 list 方法，null 表示没有查询条件
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        // 使用 MyBatis Plus 的 QueryWrapper 构建查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("product_name", keyword).or().like("product_status", keyword); // 假设 Product 表中有 name 和 description 字段
        return list(queryWrapper);
    }

    @Override
    public Double getNetValueByProductIdAndDate(int productId, Date date) {
        return this.baseMapper.getNetValueByProductIdAndDate(productId, date);
    }

    @Override
    public boolean saveProduct(Product product) {
        return this.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        baseMapper.updateById(product);
    }





}
