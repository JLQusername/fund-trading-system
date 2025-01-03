package com.github.JLQusername.product.controller;

import com.github.JLQusername.common.domain.Result;
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
    public Result getAllProducts() {
        return Result.success(productService.list());
    }

    //传入keyword获取搜索后的产品列表
    @GetMapping("/search/{keyword}")
    public Result searchProducts(@PathVariable String keyword) {
        List<Product> products = productService.searchByKeyword(keyword);
        return Result.success(products);
    }

    //传入productId和date获取产品净值
    @GetMapping("/netvalue/{productId}/{date}")
    public Result getNetValueByProductIdAndDate(
            @PathVariable int productId,
            @PathVariable String date) {
        try {
            // 将日期字符串转换为Date对象，假设格式为yyyy-MM-dd
            java.util.Date parsedDate = java.sql.Date.valueOf(date);
            Double netValue = productService.getNetValueByProductIdAndDate(productId, parsedDate);
            if (netValue == null) {
                return Result.error("No net value found for the given product and date.");
            }
            return Result.success(netValue);
        } catch (IllegalArgumentException e) {
            // 捕获日期解析失败的情况
            return Result.error("Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            // 处理其他可能的异常
            return Result.error("An error occurred while processing your request.");
        }
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
