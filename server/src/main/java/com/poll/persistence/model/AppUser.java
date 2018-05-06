package com.poll.persistence.model;

import com.poll.security.authentication.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
public class AppUser implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    private Role role;
//    @ElementCollection(fetch = FetchType.EAGER)
//    List<Role> roles;

    public AppUser(){
        role = Role.ANONYMOUS;
    }

    public boolean auth(String email, String password){
        return this.email.equals(email) && this.password.equals(password);
    }

}
