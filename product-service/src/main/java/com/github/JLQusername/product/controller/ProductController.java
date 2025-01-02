package com.github.JLQusername.product.controller;

import com.github.JLQusername.product.service.ProductService;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.github.JLQusername.product.domain.Product;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/level")
    public int getLevel(Integer productId) {
        return productService.getById(productId).getRiskLevel();
    }

    @GetMapping
    public String getProduct() {
        return "product";
    }

    /*获取所有产品列表*/
    @GetMapping("/list")
    public List<Product> getAllProducts() {
        return productService.list();
    }

    //传入keyword获取搜索后的产品列表
    @GetMapping("/search/{keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.searchByKeyword(keyword);
    }

    //传入productId和date获取产品净值
    @GetMapping("/netvalue/{productId}/{date}")
    public List<Product> getNetValueByProductIdAndDate(
            @PathVariable int productId,
            @PathVariable String date) {
        // 将日期字符串转换为Date对象，假设格式为yyyy-MM-dd
        java.util.Date parsedDate = java.sql.Date.valueOf(date);
        return productService.getByProductIdAndDate(productId, parsedDate);
    }

    @PostMapping("/add")
    public boolean addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product product) {
        product.setProductId(productId);
        productService.updateProduct(product);
        return product;
    }
}
