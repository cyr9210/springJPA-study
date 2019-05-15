package me.bong.springjpa06.post;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommentSpec {

    public static Specification<Comment> isBest(){
        return (Specification<Comment>)
                (root, criteriaQuery, builder) -> builder.isTrue(root.get(Comment_.best)); //여기서 root comment  코멘트가 베스트인지
    }

    public static Specification<Comment> isGood(){
        return (Specification<Comment>)
                (root, criteriaQuery, builder) -> builder.greaterThan(root.get(Comment_.up), 10); // 코멘트의 up이 10개보다 큰것
    }
}
