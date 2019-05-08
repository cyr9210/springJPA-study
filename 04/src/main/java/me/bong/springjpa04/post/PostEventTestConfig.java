package me.bong.springjpa04.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostEventTestConfig {

    @Bean
    public PostPublishedEventListener postPublishedEventListener(){
        return new PostPublishedEventListener();
    }
}
