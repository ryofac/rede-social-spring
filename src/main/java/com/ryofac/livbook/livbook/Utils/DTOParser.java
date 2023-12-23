package com.ryofac.livbook.livbook.Utils;

import java.util.stream.Collectors;

import com.ryofac.livbook.livbook.DTO.HashtagDTO;
import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.DTO.ProfileDTO;
import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Models.Profile;

public class DTOParser {
      // Mapper para o objeto de transferência de post
    static public PostDTO toPostDTO(Post post) {
        return PostDTO.builder()
                      .id(post.getId())
                      .owner(toProfileDTO(post.getOwner()))
                      .createdAt(post.getCreatedAt())
                      .editedAt(post.getEditedAt())
                      .text(post.getText())
                      .photoAttachementURL(post.getPhotoAttachementURL())
                      .hashtags(post.getHashtags().stream().map(DTOParser::toHashtagDTO).collect(Collectors.toList()))
                      .build();   
    }

    static public HashtagDTO toHashtagDTO(Hashtag hashtag) {
        return HashtagDTO.builder().title(hashtag.getTitle())
                                   .id(hashtag.getId())
                                   .timesUsed(hashtag.getTimesUsed())
                                   .build();
    }
    
    static public ProfileDTO toProfileDTO(Profile prof){
        return ProfileDTO.builder()
                        .id(prof.getId())
                        .email(prof.getEmail())
                        .username(prof.getUsername()) 
                        .profilePhotoUrl(prof.getProfilePhotoUrl())
                        .build();
    }

    
}
