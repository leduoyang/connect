package com.connect.api.project.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CreateProjectDto {
    private Long id;

    private String title;

    private String description;

    private int status;

    private String tags;

    private int boosted;
}
