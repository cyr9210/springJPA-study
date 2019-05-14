package me.bong.springjpa06.post;

import org.h2.store.PageOutputStream;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

//    @Test
//    public void getPost() throws Exception {
//        Post post = new Post();
//        post.setTitle("jpa");
//        postRepository.save(post);
//
//        mockMvc.perform(get("/posts/" + post.getId()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("jpa"));
//    }
//
//    @Test
//    public void getPosts() throws Exception {
//        createPost("jpa");
//
//
//        mockMvc.perform(get("/posts/")
//                .param("page", "0")
//                .param("size", "10")
//                .param("sort", "created,desc")
//                .param("sort", "title"))
//                .andDo(print())
//                .andExpect(status().isOk())
//        .andExpect(jsonPath("$.content[0].title", is("jpa")));
//    }
//
//    private void createPost(String title) {
//        int postcount = 0;
//
//        while(postcount <= 100){
//            Post post = new Post();
//            post.setTitle(title);
//            postRepository.save(post);
//            postcount++;
//        }
//    }

    @Test
    public void crud(){
        Post post = new Post();
        post.setTitle("jpa");
        postRepository.save(post);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void save(){
        Post post = new Post();
        post.setTitle("jpa");
        Post savePost = postRepository.save(post);

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savePost)).isTrue();
        assertThat(post == savePost).isTrue();

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        Post updatePost = postRepository.save(postUpdate);

        assertThat(entityManager.contains(updatePost)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(postUpdate == updatePost).isFalse();

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByTitleStartsWith(){
        Post post = new Post();
        post.setTitle("Spring Data Jpa");
        postRepository.save(post);

        List<Post> all = postRepository.findByTitleStartsWith("Spring");
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByTitle(){
        Post post = new Post();
        post.setTitle("Spring");
        postRepository.save(post);

        List<Post> all = postRepository.findByTitle("Spring", JpaSort.unsafe("LENGTH(title)"));
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void updateTitle(){
        Post post = new Post();
        post.setTitle("spring");
        Post spring = postRepository.save(post);

        int update = postRepository.updateTitle(spring.getId(), "hibernate");
        assertThat(update).isEqualTo(1);

        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId.get().getTitle()).isEqualTo("hibernate");


    }
}