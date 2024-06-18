package com.example.hotel_cms.model;

import jakarta.persistence.*;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
@Table(name = "authorities")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private RoleType authority;

    @OneToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "username")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public GrantedAuthority toAuthority(){
        return new SimpleGrantedAuthority(authority.name());
    }

    public static Role from(RoleType type){
        var role = new Role();
        role.setAuthority(type);
        return role;
    }

}
