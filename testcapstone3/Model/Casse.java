package com.example.testcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
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
   // @Column(columnDefinition = "varchar(30) not null")
    @NotEmpty(message = "name appeal can not be null")
    private String name;
    @NotEmpty(message = "description appeal can not be null")
   // @Column(columnDefinition = "varchar(200) not null")
    private String description;
    @Pattern(regexp = "^personal|labor|commercial|criminal|digital crime$")
    private String typeOflawsuits;
    //@AssertTrue
    @Column(columnDefinition = "boolean ")
   private Boolean isAppeal;
    // @Column(columnDefinition = "varchar(8) not null")
    @Pattern(regexp = "^untaken|taken|closed$")
    private String status;
   // @Column(columnDefinition = "varchar(8) not null")
    @Pattern(regexp = "^investigation|trial$")
    private String statusLawsuit;
    //  @Column(columnDefinition = "datetime ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@FutureOrPresent
    private LocalDate startDate;
    private String result;
//===============================================================

    //1
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "casse")
    @PrimaryKeyJoinColumn// هذا تابع
    private Appeal appeal;//done

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "casse")
    @PrimaryKeyJoinColumn// هذا تابع
    private Document document;//done


    @ManyToOne
    @JsonIgnore
    private User usser;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    private Client clients;


    public boolean getIsAppeal(){
        return this.isAppeal;
    }
}
