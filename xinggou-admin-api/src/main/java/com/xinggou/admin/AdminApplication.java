package com.xinggou.admin;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringBootApplication(
        scanBasePackages = {"com.xinggou.admin", "com.xinggou.common"},
        exclude={DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class}
)
@SpringCloudApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
