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
import javax.persistence.TypedQuery;
import java.util.List;

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

//        Session session = entityManager.unwrap(Session.class);
//
////        session.save(post);
//
//        Comment comment = session.get(Comment.class, 12l);
////        session.delete(post);
//        System.out.println("========================");
//        System.out.println(comment.getComment());
//        System.out.println(comment.getPost().getTitle());

//        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post As p", Post.class);
//        List<Post> posts = query.getResultList();
//        posts.forEach(System.out::println);

        List<Post> posts = entityManager.createNativeQuery("SELECT * FROM Post", Post.class).getResultList();
        posts.forEach(p -> {
            System.out.println(p);
        });

    }
}
