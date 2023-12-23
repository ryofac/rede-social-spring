package com.ryofac.livbook.livbook.Utils;

import java.util.stream.Collectors;

import com.ryofac.livbook.livbook.DTO.HashtagDTO;
import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.DTO.ProfileDTO;
import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Models.Profile;

/**
 * Classe que contém funções acessórias para a transformação de Entidades em DTO's
 */
public class DTOParser {
    /**
     * Converte Entidades Post em PostDTO
     * 
     * @param post uma instância da entidade Post
     * @return um Objeto de transferência de Post
     */
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

    /**
     * Converte Entidades Hashtag em HashtagDTO
     * 
     * @param hashtag uma instância da entidade Hashtag
     * @return um Objeto de transferência de Hashtag
     */
    static public HashtagDTO toHashtagDTO(Hashtag hashtag) {
        return HashtagDTO.builder().title(hashtag.getTitle())
                                   .id(hashtag.getId())
                                   .timesUsed(hashtag.getTimesUsed())
                                   .build();
    }
    
    /**
     * Converte Entidades Profile em ProfileDTO
     * 
     * @param prof uma instância da entidade Profile
     * @return um Objeto de transferência de Profile
     */
    static public ProfileDTO toProfileDTO(Profile prof){
        return ProfileDTO.builder()
                        .id(prof.getId())
                        .email(prof.getEmail())
                        .username(prof.getUsername()) 
                        .profilePhotoUrl(prof.getProfilePhotoUrl())
                        .build();

    }
}
