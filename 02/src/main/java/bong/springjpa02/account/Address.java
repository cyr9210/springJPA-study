package bong.springjpa02.account;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;

    private String city;

    private String state;

    private String zipcode;
}
