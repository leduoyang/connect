package com.connect.api.project.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class UpdateProjectRequest {
    @Size(min = 1, max = 200, message = "title must be at most 200")
    private String title;

    @Size(min = 1, max = 2000, message = "description must be at most 2000")
    private String description;

    @Min(value = 0, message = "status must be at least 0 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private Integer status;

    @Size(max = 200, message = "tags must be at most 200")
    private String tags;

    @Min(value = 0, message = "status must be at least 0 (0 - default, 1 - boosted)")
    @Max(value = 1, message = "status must be at most 1 (0 - default, 1 - boosted)")
    private Integer boosted;
}
