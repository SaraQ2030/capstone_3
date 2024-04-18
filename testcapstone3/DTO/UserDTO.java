package com.example.testcapstone3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    @NotNull(message = "the id cannot be empty")
    private Integer id;
    @NotEmpty(message = "the name cannot be empty")
    private String name;
    @NotEmpty(message = "the specialty cannot be empty")
    private String specialty;
    @NotEmpty(message = "lawyerlicense can't be empty!")
    private String lawyerlicense;
    @NotNull(message = "Years Of Experience can't be empty!")
    private Integer yearsOfExperience;


}
