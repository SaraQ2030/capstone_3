package com.example.testcapstone3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data//sara
public class DocumentDTO {
    @NotNull(message = "the case id cannot be empty")
    private Integer case_id;
    @NotEmpty(message = "the title cannot be empty")
    private String title;
    @NotEmpty(message = "the description cannot be empty")
    private String description;
    @NotEmpty(message = "the file path cannot be empty")
    private String filePath;
}
