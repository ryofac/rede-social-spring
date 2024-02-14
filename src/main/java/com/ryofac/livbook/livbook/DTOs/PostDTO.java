package com.ryofac.livbook.livbook.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String photoAttachementURL;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private List<HashtagDTO> hashtags;
}
