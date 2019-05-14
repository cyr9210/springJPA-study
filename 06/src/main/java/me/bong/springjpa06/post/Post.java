package me.bong.springjpa06.post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "posts")
@Getter @Setter
//@NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post AS p WHERE p.title = ?1")
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
}
