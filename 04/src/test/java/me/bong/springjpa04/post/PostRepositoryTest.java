package me.bong.springjpa04.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void crud(){
        //given
        Post post = new Post();
        post.setTitle("spring");

        assertThat(postRepository.contains(post)).isFalse();

        //
        postRepository.save(post);
        assertThat(postRepository.contains(post)).isTrue();

//        postRepository.findMyPost();
//        postRepository.delete(post);
//        postRepository.flush(); // 어자피 기본적으로 Test는 롤백이기 때문에 delete할 필요가 없어서 delete쿼리문을 날리지 않기 때문에 강제로 flush 한다.
//        postRepository.findMyPost(); //select문을 넣어주면 영향을 주기 때문에 delete 쿼리문을 날린다.
    }
}