package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Tenant ID must be not null")
  //  @Column(columnDefinition = "int not null")
    private Integer tenantId;
    @NotNull(message = "Lease ID must be not null")
  //  @Column(columnDefinition = "int not null")
    private Integer leaseId;
    //@Min(value = 1000,message = "minimum price 1000 per month")
  //  @Max(value = 5000, message = "maximum price 5000 per month")
  //  @Column(columnDefinition = "int not null")
    private Integer price;
  //  @Column(columnDefinition = "datetime not null")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate issueDate;//get StartDate of lease
    @Pattern(regexp = "NOT_PAID|PAID|OVERDUE|PARTIALLY_PAID")
    private String status;


}
