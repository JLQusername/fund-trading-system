package com.github.JLQusername.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.JLQusername.product.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.Date;
import java.util.List;

@Mapper
public interface ProductMapper extends  BaseMapper<Product> {


    @Select("SELECT netvalue FROM product WHERE product_id = #{productId} AND date = #{date}")
    List<Product> getByProductIdAndDate(int productId, Date date);
}
