package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class UserVerification {
    private Long id;

    private String email;

    private String code;

    private int status;
}
