package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入查询结果：" + result);
        if(result > 0){
            return new CommonResult(200, "OK + serverPort: " + serverPort, result);
        }else {
            return new CommonResult(444, "error", "插入新增记录失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentByID(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentByID(id);
        log.info("查询结果：" + result);
        if(result != null){
            return new CommonResult(200, "OK + serverPort: " + serverPort, result);
        }else {
            return new CommonResult(444, "error", "插入新增记录失败");
        }
    }
}
