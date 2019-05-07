package me.bong.springjpa04.post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Post {
    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob // 255자가 넘을꺼 같을 때
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
}
