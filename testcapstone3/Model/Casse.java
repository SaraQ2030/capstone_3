package com.example.testcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Casse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(30) not null")
    @NotEmpty(message = "name appeal can not be null")
    private String name;
    @NotEmpty(message = "description appeal can not be null")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;
    @Pattern(regexp = "^personal|labor|commercial|criminal$")
    private String typeOflawsuits;
    @AssertTrue
    private boolean isAppeal;

//===============================================================

    //1
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "casse")
    @PrimaryKeyJoinColumn// هذا تابع
    private Appeal appeal;//done

    @OneToMany(mappedBy = "casse", cascade = CascadeType.ALL)
    private Set<Evidence> evidenceList;
    @ManyToOne
    @JsonIgnore
    private User usser;
}
