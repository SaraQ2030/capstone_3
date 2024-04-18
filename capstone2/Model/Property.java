package com.example.capstone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@Data@AllArgsConstructor@Entity @Table(name ="Property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@NotEmpty(message = "address cant null")
  //  @Column(columnDefinition = "varchar(30) not null")
    private String address;
    //@NotEmpty(message = "type cant null")
//    @Column(columnDefinition = "varchar(30) not null check(type='apartment' or type='house')")
    @Pattern(regexp = "Apartment|House")
    private String type; // like apartment, house
    @NotNull(message = "bedroom cant be null")
    @Positive(message = "bedroom positive")
    @Min(value = 1,message = "bedroom at least 1")
   // @Column(columnDefinition = "int not null check(bedrooms>=1)")
    private Integer bedrooms;
    @NotNull(message = "bathroom cant be null")
    @Positive(message = "bathrooms positive")
    @Min(value = 1,message = "bathrooms at least 1")
   // @Column(columnDefinition = "int not null check(bathrooms>=1)")
    private Integer bathrooms;
    @NotNull(message = "area cant be null")
    @Positive(message = "area positive")
    @Min(value = 50,message = "area at least 50")
   // @Column(columnDefinition = "int not null check(area>=50)")
    private Integer area;
    @Positive(message = "rent_price positive")
    @Min(value = 1500,message ="rent_price at least 1500" )
//@Column(columnDefinition = "int not null check ( rent_amount>10000 )")
    private Integer rentPrice;
}
