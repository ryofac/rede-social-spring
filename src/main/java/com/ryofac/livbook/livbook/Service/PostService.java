package com.ryofac.livbook.livbook.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Repository.PostRepository;

@Service
public class PostService implements IPostService{
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                    .map(post -> toPostDTO(post))
                    .collect(Collectors.toList());

    }

    private PostDTO toPostDTO(Post post) {
        return PostDTO.builder()
                      .id(post.getId())
                      .createdAt(post.getCreatedAt())
                      .editedAt(post.getEditedAt())
                      .text(post.getText())
                      .photoAttachementURL(post.getPhotoAttachementURL())
                      .build();   
    } 

    
}
