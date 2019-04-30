package bong.springjpa02.study;

import bong.springjpa02.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Study {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Account owner;


}
