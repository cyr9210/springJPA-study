package me.bong.springjpa06.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") Post post){
//        Optional<Post> byId = postRepository.findById(id);
//        Post post = byId.get();
        return post.getTitle();
    }

    @GetMapping("/posts")
    public PagedResources<Resource<Post>> getPosts (Pageable pageable, PagedResourcesAssembler<Post> assembler){
//        return postRepository.findAll(pageable);
        return assembler.toResource(postRepository.findAll(pageable));
    }



}
