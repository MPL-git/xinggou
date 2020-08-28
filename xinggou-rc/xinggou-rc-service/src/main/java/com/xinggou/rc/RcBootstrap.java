package com.xinggou.rc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringBootApplication(scanBasePackages = {"com.xinggou.rc", "com.xinggou.common"})
@SpringCloudApplication
public class RcBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(RcBootstrap.class);
    }
}