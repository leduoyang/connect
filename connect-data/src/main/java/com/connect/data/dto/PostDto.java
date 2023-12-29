package com.connect.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class PostDto {
    private Long id;

    private String username;

    private Integer status;

    private String content;

    private Long referenceId;

    private String tags;

    private Integer stars;

    private Integer views;

    private Integer version;

    private Date dbModifyTime;
}
