package com.newsfeed

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.web.config.EnableSpringDataWebSupport

@EnableJpaAuditing
@SpringBootApplication
class NewsFeedApplication {

    static void main(String[] args) {
        SpringApplication.run(NewsFeedApplication, args)
    }

}
