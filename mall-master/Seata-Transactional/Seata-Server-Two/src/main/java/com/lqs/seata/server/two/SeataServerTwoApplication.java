package com.lqs.seata.server.two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class SeataServerTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataServerTwoApplication.class, args);
    }

}
