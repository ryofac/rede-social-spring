package com.ryofac.livbook.livbook.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashtagDTO {
     private Long id;
    private String title;
    private Integer timesUsed;
    
}
