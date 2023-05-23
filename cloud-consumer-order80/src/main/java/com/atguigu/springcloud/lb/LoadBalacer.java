package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalacer {
    public ServiceInstance instances(List<ServiceInstance> instanceList);
}
