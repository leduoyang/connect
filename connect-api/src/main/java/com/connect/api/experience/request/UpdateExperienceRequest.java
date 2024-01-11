package com.connect.api.experience.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class UpdateExperienceRequest {
    @Size(min = 1, max = 200, message = "company must be at most 200")
    private String company;

    @Size(min = 1, max = 200, message = "title must be at most 200")
    private String title;

    private String start;

    private String until;
}
