package com.myapps.store.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(name = "createdOn")
    private Date date;

    private boolean active;

    // Relationship with cart
    @OneToOne(mappedBy = "user")
    private Cart cart;

}
