package com.example.xuanapiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class XuanapiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuanapiInterfaceApplication.class, args);
    }

}
