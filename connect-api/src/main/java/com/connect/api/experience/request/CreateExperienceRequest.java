package com.connect.api.experience.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class CreateExperienceRequest {
    @NotNull(message = "company name can not be null")
    @Size(min = 1, max = 200, message = "company must be at most 200")
    private String company;

    @NotNull(message = "title can not be null")
    @Size(min = 1, max = 200, message = "title must be at most 200")
    private String title;

    @NotNull(message = "start date can not be null")
    private String start;

    private String until;
}
