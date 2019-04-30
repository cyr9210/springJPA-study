package bong.springjpa02.account;

import bong.springjpa02.study.Study;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();

    public void add(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date created;
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "street", column = @Column(name = "home_street")),
//            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
//            @AttributeOverride(name = "zipcode", column = @Column(name = "home_zipcode")),
//            @AttributeOverride(name = "state", column = @Column(name = "home_state"))
//
//    })
//    private Address home_address;
//
//    private Address office_address;


}
