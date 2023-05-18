package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.PaymentMapper;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentMapper paymentMapper;
    /*@Resource
    SqlSessionFactory sessionFactory;*/
    @Override
    public Payment getPaymentByID(Long id) {
        //Configuration configuration = sessionFactory.getConfiguration();
        return paymentMapper.getPaymentByID(id);
    }

    @Override
    public int create(Payment payment) {
        return paymentMapper.create(payment);
    }
}
