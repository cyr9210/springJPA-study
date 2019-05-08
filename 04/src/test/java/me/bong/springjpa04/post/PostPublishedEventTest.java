package me.bong.springjpa04.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostEventTestConfig.class)
public class PostPublishedEventTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    PostRepository postRepository;

    @Test
    public void event(){
        Post post = new Post();
        post.setTitle("event");

        PostPublishedEvent event = new PostPublishedEvent(post);
        applicationContext.publishEvent(event);
    }

    @Test
    public void springDataEvent(){
        Post post = new Post();
        post.setTitle("event2");

        postRepository.save(post.publish());
    }

}