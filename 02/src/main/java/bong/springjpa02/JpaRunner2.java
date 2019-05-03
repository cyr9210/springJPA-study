package bong.springjpa02;

import bong.springjpa02.post.Comment;
import bong.springjpa02.post.Post;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class JpaRunner2 implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Post post = new Post();
//        post.setTitle("스프링 즈파");
//
//        Comment comment = new Comment();
//        comment.setComment("빨리봐야지");
//        post.addCommnet(comment);
//
//        Comment comment2 = new Comment();
//        comment2.setComment("금방봐야지");
//        post.addCommnet(comment2);

        Session session = entityManager.unwrap(Session.class);

//        session.save(post);
        Post post = session.get(Post.class, 8l);
        session.delete(post);

    }
}
