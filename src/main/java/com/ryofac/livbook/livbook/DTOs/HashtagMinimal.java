package com.ryofac.livbook.livbook.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashtagMinimal {
    private Long id;
    private String title;
    private Integer timesUsed;
}
