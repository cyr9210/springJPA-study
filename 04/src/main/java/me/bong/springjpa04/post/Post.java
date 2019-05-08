package me.bong.springjpa04.post;

import javafx.geometry.Pos;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Post extends AbstractAggregateRoot<Post> {
    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob // 255자가 넘을꺼 같을 때
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Post publish(){
        this.registerEvent(new PostPublishedEvent(this));
        return this;
    }


}
