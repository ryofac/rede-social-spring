package com.ryofac.livbook.livbook.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.Exceptions.PostException.PostException;
import com.ryofac.livbook.livbook.Exceptions.PostException.PostNotFoundException;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Repository.IHashtagRepository;
import com.ryofac.livbook.livbook.Repository.IPostRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
    private IPostRepository postRepository;
    private IHashtagRepository hashtagRepository;

    @Autowired
    public PostService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional // ùtil para operações de inserção de dados, onde há um update no banco
    // Geralmente pesa muito, porque ele cria uma conexão separada e guarda em memória
    // NÃO é recomendado colocar em tudo!
    public Post createPost(Post toBeSaved){
        toBeSaved.setId(null); // Devo setar para nulo antes de salvar, assim garantindo que quem cuida disso é o meu próprio banco de dados
        Post saved = postRepository.save(toBeSaved);
        hashtagRepository.saveAll(saved.getHashtags()); // Salvando as hashtags também criadas com o post
        return saved;
    }

    public Post updatePost(Post alteredPost) {
        Post found = findPostById(alteredPost.getId());
        found.setText(alteredPost.getText());
        found.setHashtags(alteredPost.getHashtags());
        return postRepository.save(found);
    }

    public Post findPostById(Long id){
        Post found = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post with id " + id + "not found, Tipo: " + Profile.class.getName()));
        return found;
        
    }

    public void deletePost(Long id){
        findPostById(id);
        try {
            postRepository.deleteById(id);

        } catch(Exception e){
            throw new PostException("Post can't be deleted:" + e.getMessage());
        }
       
    }

    public List<PostDTO> findPostsByOwnerId(Long id){
        List<Post> posts = postRepository.findByOwnerId(id);
        return posts.stream()
                    .map(post -> toPostDTO(post))
                    .collect(Collectors.toList());
    }


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
