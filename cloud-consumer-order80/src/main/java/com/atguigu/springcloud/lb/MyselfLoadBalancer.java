package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyselfLoadBalancer implements LoadBalacer{
    public AtomicInteger atomicInteger = new AtomicInteger(0);

    public int getAndIncreament(){
        int current;
        int next;

        do{
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current+1;

        }while (!this.atomicInteger.compareAndSet(current, next));//自旋锁
        System.out.println("...next" + next);
        return next;
    }

    //负载均衡算法，rest接口第几次请求 % 服务集群总数量 = 实际调用服务器位置下标，每次服务器重启后，rest接口从1开始
    @Override
    public ServiceInstance instances(List<ServiceInstance> instanceList) {
        int index = getAndIncreament() % instanceList.size();
        return instanceList.get(index);
    }
}
