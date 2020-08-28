package com.xinggou.common.config;

import com.xinggou.common.constant.EnvConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvConstInit implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @Value("${app.domain.job}")
    public String jobUrl;

    @Value("${logging.file.path}")
    public String logFile;

    @Override
    public void run(String... args) {
        EnvConst.ENV = env;
        EnvConst.JOB_URL = jobUrl;
        EnvConst.LOG_FILE = logFile;
    }
}