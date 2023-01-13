package com.lqs.seata.server.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableFeignClients
@SpringBootApplication
public class SeataServerOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataServerOneApplication.class, args);
    }

}
