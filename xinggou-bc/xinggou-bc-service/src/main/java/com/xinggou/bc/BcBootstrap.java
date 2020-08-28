package com.xinggou.bc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringBootApplication(scanBasePackages = {"com.xinggou.bc", "com.xinggou.common"})
@SpringCloudApplication
public class BcBootstrap {

    public static void main(String[] args) {
        //new SpringApplicationBuilder(BcBootstrap.class).web(WebApplicationType.NONE).run(args);
        SpringApplication.run(BcBootstrap.class);
    }
}