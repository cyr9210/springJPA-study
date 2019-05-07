package me.bong.springjpa04.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class PostCustomRepositoryImpl implements PostCustomRepository{

    @Autowired
    EntityManager entityManager;

//    @Autowired
//    JdbcTemplate jdbcTemplate;

    @Override
    public List<Post> findMyPost() {
       return entityManager.createQuery("SELECT p FROM Post AS p").getResultList();
    }

    @Override
    public void delete(Object entity) {
        System.out.println("custom delete");
        entityManager.remove(entity);
    }
}
