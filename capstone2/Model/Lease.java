package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Data@AllArgsConstructor@Entity
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "property_id can not null")
    private Integer propertyId;
    @NotNull(message = "tenant_id can not null")
    private Integer tenantId;
   // @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
  //  @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    //@FutureOrPresent
    //private LocalDate renewalDate;
    private Integer rentAmount;
   // @NotNull(message = "isRenewable can not null")
   // private boolean isRenewable;
}
