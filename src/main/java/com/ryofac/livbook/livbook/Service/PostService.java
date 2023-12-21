package com.ryofac.livbook.livbook.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.Exceptions.PostException.PostNotFoundException;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Repository.IPostRepository;

@Service
public class PostService implements IPostService{
    private IPostRepository postRepository;

    @Autowired
    public PostService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO findPostById(Long id){
        Post found = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post with this id not found"));
        return toPostDTO(found);
        
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                    .map(post -> toPostDTO(post))
                    .collect(Collectors.toList());

    }

     // Mapper para o objeto de transferência de post
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
