package com.connect.api.project.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DeleteProjectDto {
    private Long id;
}
