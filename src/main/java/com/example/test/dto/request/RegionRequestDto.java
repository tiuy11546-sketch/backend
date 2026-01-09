package com.example.test.dto.request;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegionRequestDto implements Serializable {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
