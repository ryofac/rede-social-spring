package com.ryofac.livbook.livbook.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PostMinimal { 
    private Long id;
    private String photoAttachementURL;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private List<HashtagMinimal> hashtags;
}
