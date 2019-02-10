package com.crawler.review;

import com.crawler.annotation.EnableRedis;
import com.customer.base.annotation.EnableBaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBaseService
@EnableScheduling
@SpringBootApplication
@EnableRedis
public class AukeyCrawlerReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(AukeyCrawlerReviewApplication.class, args);
    }

}