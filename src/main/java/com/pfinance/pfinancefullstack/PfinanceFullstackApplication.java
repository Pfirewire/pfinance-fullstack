package com.pfinance.pfinancefullstack;

import com.pfinance.pfinancefullstack.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class PfinanceFullstackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfinanceFullstackApplication.class, args);
    }

}
