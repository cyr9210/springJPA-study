package me.bong.springjpa04.post;

import java.util.List;

public interface PostCustomRepository<T> {
    List<Post> findMyPost();

    void delete(T entity);
}
