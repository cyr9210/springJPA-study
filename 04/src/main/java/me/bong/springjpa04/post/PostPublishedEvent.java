package me.bong.springjpa04.post;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostPublishedEvent extends ApplicationEvent {

    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }


}
