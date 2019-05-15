package me.bong.springjpa06.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getCommnet() {
        Optional<Comment> byId = commentRepository.findById(1l);

        System.out.println("=====================================");

        Optional<Comment> comment = commentRepository.getById(1l);

    }

    @Test
    public void summary(){
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("hibernate");
        comment.setPost(savedPost);
        comment.setUp(2);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(savedPost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("=================");
            System.out.println(c.getComment());
//            System.out.println(c.getVotes());
        });

//        commentRepository.findByPost_Id(savedPost.getId(), CommentSummary_Class.class).forEach(c -> {
//            System.out.println("=================");
//            System.out.println(c.getComment());
//            System.out.println(c.getVotes());
//        });
    }

    @Test
    public void specs(){
        commentRepository.findAll(CommentSpec.isBest().and(CommentSpec.isGood()), PageRequest.of(0, 10));
    }

    @Test
    public void qbe(){
        Comment probe = new Comment();
        probe.setBest(true);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withIgnorePaths("up", "down");

        Example<Comment> example = Example.of(probe, exampleMatcher);

        commentRepository.findAll(example);
    }
}