package me.bong.springjpa03.post;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Stream;

//@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends MyRepository<Comment, Long>{

//    Comment save(Comment comment);
//
//    List<Comment> findAll();

//    @Query(value = "SELECT c FROM Comment AS c", nativeQuery = true)
//    List<Comment> findByTitleContains(String keyword);

    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likeCount);

    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountAsc(String keyword);

//    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    Stream<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);
}
