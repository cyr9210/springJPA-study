package me.bong.springjpa04.post;

import org.springframework.context.event.EventListener;

public class PostPublishedEventListener{

    @EventListener
    public void onApplicationEvent(PostPublishedEvent event) {
        System.out.println("------------------");
        System.out.println(event.getPost().getTitle() + " is published!");
        System.out.println("------------------");
    }
}
