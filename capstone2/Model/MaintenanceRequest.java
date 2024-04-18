package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Data@AllArgsConstructor@Entity@Table(name = "MaintenanceRequest")

public class MaintenanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@NotEmpty(message = "description not empty")
  //  @Column(columnDefinition = "VARCHAR(255) not null")
    private String description;
  //  @Column(columnDefinition = "VARCHAR(20) not null check  ( status ='PENDING' or status='IN_PROGRESS' or status='COMPLETED')")
    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED")
    private String status;
  //  @Column(columnDefinition = "datetime not null")
    //LocalDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRequested;
    @NotNull(message = "property_id not null")
  //  @Column(columnDefinition = "int not null")
    private Integer propertyId;
    @NotNull(message = "tenant_id not null")
    //@Column(columnDefinition = "int not null")
    private Integer tenantId;

}
