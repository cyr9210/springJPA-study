package me.bong.springjpa03.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommnetRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud(){
        //given
        Comment comment = new Comment();
        comment.setComment("Hello comment");
        commentRepository.save(comment);

        //when
        List<Comment> all = commentRepository.findAll();
        long count = commentRepository.count();

        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(count).isEqualTo(1);

        //when
        Optional<Comment> byId = commentRepository.findById(100l);

        //given
        assertThat(byId).isEmpty();

        //optional
        assertThat(byId.isPresent()).isFalse(); //값이 있는지 없느지 체크
        Comment other = byId.orElse(new Comment()); //값이 있으면 값을 쓰고, 없으면 새로운 Comment를 생
        other.setComment("Bye comment");
        assertThat(other.getComment()).contains("Bye");


    }

    @Test
    public void crudPractice(){
        //likecount가 10보다 크고 코멘트에 "spring"이라는 키워드가 대소문자 상관없이 검색
        //given
        createComment(11, "spring data jpa");
        Comment test = createComment(100, "hibernate Spring");
        createComment(5, "Spring");

//        //when
//        List<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("Spring", 10);
//
//        //given
//        assertThat(comments.size()).isEqualTo(2);
//
//        //"spring"키워드가 대소문자 상관없이 likecount순으로 정렬하여 가장 높은 값을 체크
//        List<Comment> comments1 = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountAsc("spring");
////        assertThat(comments1).first().hasFieldOrProperty("likeCount").isEqualTo(test);
//        assertThat(comments1).first().hasFieldOrPropertyWithValue("likeCount", 5);

        //"spring"키워드를 대소무자 무시하고, 검색하여 페이징 처리(2개씩들어감)하여 두번째 페이지의 갯수확인
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "likeCount"));

//        Page<Comment> commentPage = commentRepository.findByCommentContainsIgnoreCase("spring", pageRequest);
//        assertThat(commentPage.getNumberOfElements()).isEqualTo(1);
//        assertThat(commentPage).first().hasFieldOrPropertyWithValue("likeCount", 5);
        //stream
        try (Stream<Comment> commentStream = commentRepository.findByCommentContainsIgnoreCase("spring", pageRequest);){
            Comment firstComment = commentStream.findFirst().get();
            assertThat(firstComment.getLikeCount()).isEqualTo(5);
        }



    }

    private Comment createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setLikeCount(likeCount);
        return commentRepository.save(newComment);
    }
}