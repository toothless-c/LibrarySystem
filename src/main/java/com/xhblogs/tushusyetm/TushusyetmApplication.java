package com.xhblogs.tushusyetm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// Enablecaching 开启二级缓存
public class TushusyetmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TushusyetmApplication.class, args);
    }

}
