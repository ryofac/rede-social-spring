package com.ryofac.livbook.livbook.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTOs.PostDetails;
import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Repositories.IHashtagRepository;
import com.ryofac.livbook.livbook.Repositories.IPostRepository;
import com.ryofac.livbook.livbook.Services.exceptions.ObjectNotFoundException;
import com.ryofac.livbook.livbook.Utils.DTOMapper;

import jakarta.transaction.Transactional;

@Service
public class PostService {
    private IPostRepository postRepository;
    private IHashtagRepository hashtagRepository;

    @Autowired
    public PostService(IPostRepository postRepository, IHashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @Transactional // ùtil para operações de inserção de dados, onde há um update no banco
    // Geralmente pesa muito, porque ele cria uma conexão separada e guarda em
    // memória
    // NÃO é recomendado colocar em tudo!

    // O método de criar post também lida bastante, por consequência, com a lógica
    // de salvar hashtags:
    // Se o título da hashtag já estiver no banco de dados, ela não é salva
    // novamente
    // Aqui também atualiza- se a quantidade de vezes que a hastag foi usada

    public PostDetails createPost(Post toBeSaved) {
        toBeSaved.setId(null);
        if (!toBeSaved.getHashtags().isEmpty()) {
            List<Hashtag> managedHashtags = this.manageHashtagsOnPost(toBeSaved);
            toBeSaved.setHashtags(managedHashtags);
        }
        return DTOMapper.toPostDetails(postRepository.save(toBeSaved));
    }

    @Transactional
    public Post updatePost(Post alteredPost) {
        Post found = findPostById(alteredPost.getId());
        found.setText(alteredPost.getText());
        hashtagRepository.saveAll(alteredPost.getHashtags());
        found.setHashtags(alteredPost.getHashtags());
        found.setComments(alteredPost.getComments());
        return postRepository.save(found);
    }

    public Post findPostById(@NonNull Long id) {
        Post found = postRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Post with id " + id + "not found, Tipo: " + Profile.class.getName()));
        return found;

    }

    @Transactional
    public void deletePost(@NonNull Long id) {
        findPostById(id);
        postRepository.deleteById(id);
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public List<Post> findPostsByOwnerId(Long id) {
        List<Post> posts = postRepository.findByOwnerId(id);
        return posts;
    }

    /**
     * Função auxiliar para administrar a forma como as hashtags são salvas em cada
     * post
     * 
     * @param post o post a ser salvo ( que terá as hashtags a serem criadas )
     * @return uma lista de hashtags tratadas, prontas para serem salvas
     */
    private List<Hashtag> manageHashtagsOnPost(Post post) {
        List<Hashtag> managedHashtags = new ArrayList<>();
        for (Hashtag hashtag : post.getHashtags()) {
            hashtag.setId(null);
            Hashtag managedHashtag = hashtagRepository.findByTitle(hashtag.getTitle())
                    .orElseGet(() -> hashtagRepository.save(hashtag));
            if (managedHashtag.getTimesUsed() == null) {
                managedHashtag.setTimesUsed(0);
            }
            managedHashtag.setTimesUsed(managedHashtag.getTimesUsed() + 1);
            managedHashtags.add(managedHashtag);
        }
        return managedHashtags;

    }

}
