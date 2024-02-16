package com.ryofac.livbook.livbook.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDetails {
    private Long id;
    private String photoAttachementURL;
    private String text;
    private ProfileMinimal owner;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private List<HashtagMinimal> hashtags;
    private List<CommentDTO> comments;
}
