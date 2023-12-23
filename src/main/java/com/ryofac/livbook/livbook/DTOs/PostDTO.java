package com.ryofac.livbook.livbook.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    private Long id;
    private ProfileDTO owner;
    private String photoAttachementURL;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private List<HashtagDTO> hashtags;
}
