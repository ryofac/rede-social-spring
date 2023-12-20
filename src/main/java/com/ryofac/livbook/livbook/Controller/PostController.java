package com.ryofac.livbook.livbook.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryofac.livbook.livbook.DTO.PostDTO;
import com.ryofac.livbook.livbook.Service.PostService;

import ch.qos.logback.core.model.Model;

@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Exemplo: Pegar todos os posts!
    // Eu recebo uma entidade do tipo Model no meu método
    // Essa entidade que vai me permitir retornar os dados para serem retornados
    @GetMapping("/posts") // Especificando a "rota"
    public String listPosts(Model model){
        List<PostDTO> posts = postService.getAllPosts();
        return "";
    }

    
}
