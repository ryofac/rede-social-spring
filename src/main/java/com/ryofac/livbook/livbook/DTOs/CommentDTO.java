package com.ryofac.livbook.livbook.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentDTO {
    private String content;
    private ProfileDTO owner;
}
