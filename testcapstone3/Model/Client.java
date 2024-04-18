package com.example.testcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    @NoArgsConstructor
    //Sarah
    public class Client {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @NotEmpty(message = "the client name cannot be empty")
        @Column(columnDefinition = "varchar(15) not null")
        private String name ;

        @NotNull(message = "the age cannot be empty")
        @Min(value = 18,message = "you should be at least 18 years old ")
        private Integer age;
        @NotEmpty(message = "the gender cannot be empty")
        @Column(columnDefinition = "varchar(6) not null check(gender='male' or gender='female')")//check the database constraint
        @Pattern(regexp = "^male|female$")
        private String gender ;
        @NotEmpty(message = "the phone number cannot be empty")
        @Column(columnDefinition = "varchar(15) not null unique")
        // @Pattern(regexp = "^05\\d{8}$"  , message = "Number should start with 05xxxxxxxx and contain 10 numbers")
        private String phoneNumber;
        @Email
        @NotEmpty(message = "the email cannot be empty")
        @Column(columnDefinition = "varchar(15) not null unique")
        private String email;
        @NotEmpty(message = "the address cannot be empty")
        @Column(columnDefinition = "varchar(30) not null")
        private String address ;
//--------------------------------Relational----------------------

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "clients")
        private Set<Casse> cases;

        //----------------------------------------------------------------
        @ManyToMany
        @JsonIgnore
        private Set<User> users;

    }
