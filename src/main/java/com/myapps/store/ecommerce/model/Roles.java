package com.myapps.store.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "Role", nullable = false)
    private String roleName;

//    @ManyToMany(mappedBy = "role")
//    Set<User> user = new HashSet<>();
}
