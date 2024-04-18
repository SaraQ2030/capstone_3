package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Data@AllArgsConstructor@Entity @Table(name ="Tenant")
    public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name not empty")
    @Size(min =5 ,max=10,message = "length of name should be more than 10")
    //@NotEmpty
    //@Pattern(regexp = "[a-zA-z]")
   // @Column(columnDefinition = "varchar(10) not null check(length(name)>4 and name Like '[a-zA-Z][a-zA-Z][a-zA-Z]')")
    private String name;
    //@NotEmpty
   // @Column(columnDefinition = "varchar(30) not null unique check (email like '%_@__%.__%')")
    //@Email(message = "Email must be valid Email@com")
    private String email;
    @NotNull(message = "age can not be null")
    @Min(value = 21,message = "age more than 21")
    @Max(value = 60,message = "age less than 60 ")
  //  @Column(columnDefinition = "int check(age >21 or age <60)")
    private Integer age;
    //@PastOrPresent
    //  @Column(columnDefinition = "datetime not null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate moveInDate;
    //  @Column(columnDefinition = "datetime ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@FutureOrPresent
    private LocalDate moveOutDate;


}
