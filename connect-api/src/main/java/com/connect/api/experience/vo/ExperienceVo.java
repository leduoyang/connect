package com.connect.api.experience.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class ExperienceVo {
    private String company;

    private String title;

    private Date start;

    private Date until;
}
