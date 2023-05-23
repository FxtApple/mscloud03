package com.atguigu.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//该文件不能放在被ComponentScan注解扫描的包下
public class MySelfRule {

    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}
