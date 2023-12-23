package com.ryofac.livbook.livbook.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTO.HashtagDTO;
import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.Exceptions.PostException.PostException;
import com.ryofac.livbook.livbook.Exceptions.PostException.PostNotFoundException;
import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Repository.IHashtagRepository;
import com.ryofac.livbook.livbook.Repository.IPostRepository;
import com.ryofac.livbook.livbook.Utils.DTOParser;

import jakarta.transaction.Transactional;

// TODO: Descobrir e tratar o porqueê quando o post tem hashtags não consegue serializar os dados para retornar na api

@Service
public class PostService {
    private IPostRepository postRepository;
    private IHashtagRepository hashtagRepository;

    //TODO: Implementar HashtagService como uma extensão de postService, já que são um "casal"

    @Autowired
    public PostService(IPostRepository postRepository, IHashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @Transactional // ùtil para operações de inserção de dados, onde há um update no banco
    // Geralmente pesa muito, porque ele cria uma conexão separada e guarda em memória
    // NÃO é recomendado colocar em tudo!

    // O método de criar post também lida bastante, por consequência, com a lógica de salvar hashtags:
    // Se o título da hashtag já estiver no banco de dados, ela não é salva novamente
    // Aqui também atualiza- se a quantidade de vezes que a hastag foi usada
    // TODO: Criar funções auxiliares aqui
    public PostDTO createPost(Post toBeSaved){
        toBeSaved.setId(null);
        if(!toBeSaved.getHashtags().isEmpty()){
            List<Hashtag> managedHashtags = new ArrayList<>();
            for (Hashtag hashtag : toBeSaved.getHashtags()) {
                hashtag.setId(null);
                Hashtag managedHashtag = hashtagRepository.findByTitle(hashtag.getTitle())
                    .orElseGet(() -> hashtagRepository.save(hashtag));
                    if (managedHashtag.getTimesUsed() == null) {
                        managedHashtag.setTimesUsed(0);
                    }
                    managedHashtag.setTimesUsed(managedHashtag.getTimesUsed() + 1);
                managedHashtags.add(managedHashtag);
            }
            toBeSaved.setHashtags(managedHashtags);
        }
        return DTOParser.toPostDTO(postRepository.save(toBeSaved));
    }   


    @Transactional
    public PostDTO updatePost(Post alteredPost) {
        Post found = findPostById(alteredPost.getId());
        found.setText(alteredPost.getText());
        found.setHashtags(alteredPost.getHashtags());
        return DTOParser.toPostDTO(postRepository.save(found));
    }

    public Post findPostById(Long id){
        Post found = postRepository.findById(id).orElseThrow(() ->
         new PostNotFoundException("Post with id " + id + "not found, Tipo: " + Profile.class.getName()));
        return found;
        
    }

    public PostDTO findPostDTObyid(Long id){
        return DTOParser.toPostDTO(findPostById(id));
    }

    @Transactional
    public void deletePost(Long id){
        try {
            findPostById(id);
            postRepository.deleteById(id);
        } catch(Exception e){
            throw new PostException("Post can't be deleted:" + e.getMessage());
        }
       
    }


    public List<PostDTO> findPostsByOwnerId(Long id){
        List<Post> posts = postRepository.findByOwnerId(id);
        List<PostDTO> convert = posts.stream().map(DTOParser::toPostDTO).collect(Collectors.toList());
        return convert;
    }


   

    
}
