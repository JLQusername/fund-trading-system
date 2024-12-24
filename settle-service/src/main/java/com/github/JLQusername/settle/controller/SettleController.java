package com.github.JLQusername.settle.controller;

import com.github.JLQusername.api.OurSystem;
import com.github.JLQusername.settle.service.SettleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settle")
@RequiredArgsConstructor
public class SettleController {
    private final SettleService settleService;

    @PostMapping("/system")
    public OurSystem getSystem() {
        return settleService.getSystem();
    }
}
