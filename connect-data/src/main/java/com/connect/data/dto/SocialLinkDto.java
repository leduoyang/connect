package com.connect.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class SocialLinkDto {
    private Long id;

    private String platform;

    private String platformId;
}
