package com.yoonsunmi.flashCoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yoonsunmi.flashCoupon")
@EntityScan(basePackages = "com.yoonsunmi.flashCoupon")
public class FlashCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlashCouponApplication.class, args);
    }

}
