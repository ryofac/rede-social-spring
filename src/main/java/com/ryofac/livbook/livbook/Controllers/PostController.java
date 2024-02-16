package com.ryofac.livbook.livbook.Controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

import com.ryofac.livbook.livbook.DTOs.PostDetails;
import com.ryofac.livbook.livbook.Models.Post;
import com.ryofac.livbook.livbook.Models.Post.CreatePost;
import com.ryofac.livbook.livbook.Models.Post.UpdatePost;
import com.ryofac.livbook.livbook.Services.PostService;
import com.ryofac.livbook.livbook.Utils.DTOMapper;

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

    @GetMapping
    public ResponseEntity<List<PostDetails>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        List<PostDetails> converted = posts.stream().map(DTOMapper::toPostDetails).collect(Collectors.toList());
        return ResponseEntity.ok(converted);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetails> findPostById(@PathVariable @NonNull Long id){
        Post found = postService.findPostById(id);
        PostDetails converted = DTOMapper.toPostDetails(found);
        return ResponseEntity.ok(converted);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDetails>> findByOwnerId(@PathVariable Long id) {
        List<Post> allPosts = postService.findPostsByOwnerId(id);
        return ResponseEntity.ok(allPosts.stream().map(DTOMapper::toPostDetails).collect(Collectors.toList()));
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
