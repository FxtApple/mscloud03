package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping(value="/payment/discovery")
    public Object getDiscoveryClinet(){
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: "+element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance: instances){
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" +instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLb(){
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        // 业务逻辑处理正确，但是需要耗费3秒钟
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
