package bong.springjpa02.post;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.FetchMode;
import org.hibernate.loader.plan.spi.Fetch;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comment = new HashSet<>();

    public void addCommnet(Comment commnet){
        this.getComment().add(commnet);
        commnet.setPost(this);
    }
}
