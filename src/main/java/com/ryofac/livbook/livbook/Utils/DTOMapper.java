package com.ryofac.livbook.livbook.Utils;

import java.util.stream.Collectors;

import com.ryofac.livbook.livbook.DTOs.CommentDTO;
import com.ryofac.livbook.livbook.DTOs.HashtagDetails;
import com.ryofac.livbook.livbook.DTOs.HashtagMinimal;
import com.ryofac.livbook.livbook.DTOs.PostDetails;
import com.ryofac.livbook.livbook.DTOs.PostMinimal;
import com.ryofac.livbook.livbook.DTOs.ProfileDTO;
import com.ryofac.livbook.livbook.Models.Comment;
import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Models.Profile;

/**
 * Classe que contém funções acessórias para a transformação de Entidades em DTO's
 */
public class DTOMapper {

    /**
     * Converte Entidades Post em PostDetails
     * 
     * @param post uma instância da entidade Post
     * @return um Objeto de transferência de Post
     */
    static public PostDetails toPostDetails(Post post) {
        return PostDetails.builder()
                      .id(post.getId())
                      .createdAt(post.getCreatedAt())
                      .editedAt(post.getEditedAt())
                      .owner(toProfileDTO(post.getOwner()))
                      .text(post.getText())
                      .photoAttachementURL(post.getPhotoAttachementURL())
                      .hashtags(post.getHashtags().stream().map(DTOMapper::toHashtagMinimal).collect(Collectors.toList()))
                      .comments(post.getComments().stream().map(DTOMapper::toCommentDTO).collect(Collectors.toList()))
                      .build();   
    }



    /**
     * Converte Entidades Post em PostMinimal
     * 
     * @param post uma instância da entidade Post
     * @return um Objeto de transferência de Post
     */
    static public PostMinimal toPostMinimal(Post post) {
        return PostMinimal.builder()
                      .id(post.getId())
                      .createdAt(post.getCreatedAt())
                      .editedAt(post.getEditedAt())
                      .text(post.getText())
                      .photoAttachementURL(post.getPhotoAttachementURL())
                      .hashtags(post.getHashtags().stream().map(DTOMapper::toHashtagMinimal).collect(Collectors.toList()))
                      .build();   
    }

    /**
     * Converte Entidades Hashtag em HashtagDetails
     * 
     * @param hashtag uma instância da entidade Hashtag
     * @return um Objeto de transferência de Hashtag
     */
    static public HashtagDetails toHashtagDetails(Hashtag hashtag) {
        return HashtagDetails.builder().title(hashtag.getTitle())
                                   .id(hashtag.getId())
                                   .timesUsed(hashtag.getTimesUsed())
                                   .build();
    }


    
    /**
     * Converte Entidades Hashtag em HashtagMinimal
     * 
     * @param hashtag uma instância da entidade Hashtag
     * @return um Objeto de transferência de Hashtag
     */
     static public HashtagMinimal toHashtagMinimal(Hashtag hashtag) {
        return HashtagMinimal.builder().title(hashtag.getTitle())
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
                        .posts(prof.getPosts().stream().map(DTOMapper::toPostMinimal).collect(Collectors.toList()))
                        .build();
    }


     /**
     * Converte Entidades Comment em CommentDTO
     * 
     * @param comment uma instância da entidade Profile
     * @return um Objeto de transferência de Comentários
     */
    static public CommentDTO toCommentDTO(Comment comment){
        return CommentDTO.builder()
                         .owner(toProfileDTO(comment.getOwner()))
                         .content(comment.getContent())
                         .build();
    }
}
