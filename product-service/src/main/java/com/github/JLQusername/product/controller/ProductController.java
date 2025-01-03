package com.github.JLQusername.product.controller;

import com.github.JLQusername.api.OurSystem;
import com.github.JLQusername.common.domain.Result;
import com.github.JLQusername.api.NetValue;
import com.github.JLQusername.product.service.ProductService;
import com.github.JLQusername.api.client.SettleClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.github.JLQusername.product.domain.Product;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Autowired
    private SettleClient settleClient;

    @PostMapping("/level")
    public int getLevel(Integer productId) {
        return productService.getById(productId).getRiskLevel();
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


    @GetMapping("/system/transaction-date")
    public Result getTransactionDate() {
        try {
            // 获取 OurSystem 对象
            OurSystem ourSystem = settleClient.getSystem();

            // 如果 getTransactionDate 返回的是一个 ISO 8601 字符串，请直接使用 OffsetDateTime.parse()
            // 如果是 java.util.Date.toString() 格式的字符串，则按如下方式解析
            String rawDateStr = ourSystem.getTransactionDate().toString();

            // 定义输入日期格式
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

            // 解析输入字符串为 ZonedDateTime 对象
            ZonedDateTime dateTime = ZonedDateTime.parse(rawDateStr, inputFormatter);

            // 定义输出格式：只有日期部分
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 将日期格式化为字符串并返回
            String formattedDate = dateTime.format(outputFormatter);

            return Result.success(formattedDate);
        } catch (DateTimeParseException e) {
            return Result.error("Failed to parse date: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("An error occurred while fetching the transaction date: " + e.getMessage());
        }

    }

    @PostMapping("/net_values")
    public List<NetValue> getLatestNetValues() {
        return productService.getLatestNetValues();
    }

    @PostMapping("/net_value/insert")
    public boolean insertNetValue(@RequestBody NetValue netValue){
        return productService.insertNetValue(netValue);

    }
    @PostMapping("/net_value/{productId}/{date}")
    public Double getNetValue(@PathVariable("productId") Integer productId,
                       @PathVariable("date") Date date){
        return productService.getNetValueByProductIdAndDate(productId,date);
    };

}
