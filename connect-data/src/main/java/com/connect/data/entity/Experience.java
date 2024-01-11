package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Accessors(chain = true)
@Data
public class Experience {
    private Long id;

    private String company;

    private String title;

    private LocalDate start;

    private LocalDate until;

    private Long userId;

    private Integer status;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
