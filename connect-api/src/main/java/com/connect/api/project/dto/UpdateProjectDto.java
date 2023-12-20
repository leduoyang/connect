package com.connect.api.project.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateProjectDto {
    private Long id;

    private String title;

    private String description;

    private Integer status;

    private String tags;

    private Integer boosted;
}
