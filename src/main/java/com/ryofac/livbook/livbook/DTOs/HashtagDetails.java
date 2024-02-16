package com.ryofac.livbook.livbook.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashtagDetails {
    private Long id;
    private String title;
    private Integer timesUsed;
    private PostMinimal posts;
}
