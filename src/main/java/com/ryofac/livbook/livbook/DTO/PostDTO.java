package com.ryofac.livbook.livbook.DTO;

import java.time.LocalDateTime;

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
}
