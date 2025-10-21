package com.sc.bankapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "first_name", nullable = false)
    private  String firstName;

    @Column(name = "last_name")
    private  String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private  String email;

    @Column(name = "phone")
    private  String phone;

    @Column(name = "active")
    private  String  active;

    @Column(name = "password")
    private  String password;

    @Column(name = "role")
    private  String role;

    // one customer -> many accounts
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Account> accounts;

}
