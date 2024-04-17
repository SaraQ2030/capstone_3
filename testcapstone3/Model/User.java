package com.example.testcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

  //  @NotEmpty(message = "user name can't be empty!")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

   // @NotEmpty(message = "email can't be empty!")
    @Email
    private String email;

  //  @NotNull(message = "years of experience can't be null!")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;

  //  @NotEmpty(message = "specialty can't be empty!")
    @Column(columnDefinition = "varchar(20) not null")
    private String specialty;

    @NotEmpty(message = "role can't be empty!")
    @Pattern(regexp = "^Lawyer|Admin$")
    @Column(columnDefinition = "varchar(7) not null check(role='Lawyer' or role='Admin')")
    private String role;

    @NotEmpty(message = "user name can't be empty!")
    @Column(columnDefinition = "varchar(20) not null")
    private String lawyerlicense;

    //====================Relations====================================
    //relation here is many users have many clients!(ManyToMany)
    @ManyToMany(mappedBy = "users")
    private Set<Client> client;
    //delete comment sign later!

    //also 1 user has many cases! (OneToMany)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usser")
    private Set<Task> tasks;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "usser")
    private Set<Casse> casses;




}

//================================================================

