package com.connect.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class ExperienceDto {
    private Long id;

    private String company;

    private String title;

    private Date start;

    private Date until;
}
