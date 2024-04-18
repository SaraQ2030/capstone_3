package com.example.testcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

//Ghaliah
public class Appeal {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "title appeal can not be null")
    @Column(columnDefinition = "varchar(50) not null")
    private String title;
    @NotEmpty(message = "description appeal can not be null")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;
    @Column(columnDefinition = "datetime")
    //  @Column(columnDefinition = "datetime ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@FutureOrPresent
    private LocalDate startDate;
    @NotEmpty(message = "status can't be empty!")
   // @Column(columnDefinition = "varchar(15) not null check(status='intake' or status='investigation' or status='trial')")
    @Pattern(regexp = "^intake|investigation|trial$")
    private String status;
    //
//-----------------------------Relational-------------------------------
    // each case can be had one Appeal
    @OneToOne
    @MapsId//only oneToOne relation its =    @PrimaryKeyJoinColumn
    @JsonIgnore // infinite loop so when get case ignore case
    private Casse casse;
    // @Column(columnDefinition = "boolean ")
    private boolean closed;
    @Pattern(regexp = "^Approved|NOT_Approved$")
    private String result;
    @OneToMany(mappedBy = "appeal", cascade = CascadeType.ALL)
    private Set<Evidence> evidenceList;


//=============================================





}
