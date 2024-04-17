package com.example.testcapstone3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "task description can't be empty!")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;
    private LocalDate start_at;
    @NotEmpty(message = "task status can't be empty!")
    @Column(columnDefinition = "varchar(15) not null check(status='intake' or status='investigation' or status='trial')")
    @Pattern(regexp = "^intake|investigation|trial$")
    private String status;

    @NotNull(message = "task cost can't be null!")
    @Column(columnDefinition = "double not null")
    private Double cost;



    //====================Relations================================
    //relaiton here is many tasks to 1 user!
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User usser;


//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "task_id",referencedColumnName = "id")
//    private Casse casses;
//==============================================================

}