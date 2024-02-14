package com.ryofac.livbook.livbook.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ryofac.livbook.livbook.DTOs.PostDTO;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Models.Post.CreatePost;
import com.ryofac.livbook.livbook.Models.Post.UpdatePost;
import com.ryofac.livbook.livbook.Services.PostService;

import jakarta.validation.Valid;

// TODO : Adcionar e tratar mais tipos de Exceções

@RequestMapping("/posts")
@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findPostById(@PathVariable Long id){
        try {
            PostDTO found = postService.findPostDTObyid(id);
            return ResponseEntity.ok(found);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDTO>> findByOwnerId(@PathVariable Long id) {
        List<PostDTO> allPosts = postService.findPostsByOwnerId(id);
        return ResponseEntity.ok(allPosts);
    }

    // Create
    @PostMapping
    @Validated(CreatePost.class)
    public ResponseEntity<Void> createPost(@Valid @RequestBody Post toBeCreated){
        postService.createPost(toBeCreated);
        URI postLoc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(toBeCreated.getId()).toUri();
        return ResponseEntity.created(postLoc).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdatePost.class)
    public ResponseEntity<Void> updatePost(@Valid @RequestBody Post newPost, @PathVariable Long id) {
        newPost.setId(id);
        postService.updatePost(newPost);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }



    
}
