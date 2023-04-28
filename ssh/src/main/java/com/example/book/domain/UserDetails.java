package com.example.book.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TBL_USER_DETAILS")
public class UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "DETAILS_ID")
    private Long id;

    //@Column(nullable = false)
    @Column(name = "FIRST_NAME")
    @Size(max = 20, message = "{first name length should less than 20}")
    private String firstName;

    //@Column(nullable = false)
    @Column(name = "LAST_NAME")
    @Size(max = 20, message = "{last name length should less than 20}")
    private String lastName;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "details", cascade = CascadeType.ALL)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}