package me.bong.springjpa03.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest //Repository 관련 빈만 등록한다. (빠름)
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    @Rollback(false)
    public void crudRepository(){
        //Given
        Post post = new Post();
        post.setTitle("hello Spring Data Common!!");

        //When
        Post newPost = postRepository.save(post);

        //Then
        assertThat(newPost.getId()).isNotNull();

        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(newPost);
    }

    @Test
    public void pagingAndSortingRepository(){
        //Given
        Post post = new Post();
        post.setTitle("hello Spring Data Common!!");
        Post post1 = new Post();
        post1.setTitle("Bye Spring Data Comment!!");

        //When
        Post newPost = postRepository.save(post);
        postRepository.save(post1);

        //Then
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10)); // 모두 찾아서 페이징 처리하겠다. (0페이지 시작, 한페이지에 10개)
        assertThat(page.getTotalElements()).isEqualTo(2); // 전체 갯수
        assertThat(page.getNumber()).isEqualTo(0); //현재 페이지의 넘버
        assertThat(page.getSize()).isEqualTo(10); // 페이지에 들어갈 수 있는 엘리먼트 갯수
        assertThat(page.getNumberOfElements()).isEqualTo(2); // 현재페이지에 들어가 있는 엘리먼트 갯수

        //When
        Page<Post> hello = postRepository.findByTitleContains("hello", PageRequest.of(0, 10));

        //Then
        assertThat(hello.getTotalElements()).isEqualTo(1);

        //When
        long spring = postRepository.countByTitleContains("Spring");

        //Then
        assertThat(spring).isEqualTo(2);


    }

}