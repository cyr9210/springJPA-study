package me.bong.springjpa04.post;

import me.bong.springjpa04.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends MyRepository<Post, Long>{
}
