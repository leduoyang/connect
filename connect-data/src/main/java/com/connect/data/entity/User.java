package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class User {
    private Long id;

    private String userId;

    private String password;

    private int status;

    private int role;

    private String description;

    private String email;

    private String phone;

    private String profileImage;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
