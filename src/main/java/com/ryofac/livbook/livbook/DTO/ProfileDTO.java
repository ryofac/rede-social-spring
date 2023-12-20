package com.ryofac.livbook.livbook.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String profilePhotoUrl;
    
}
