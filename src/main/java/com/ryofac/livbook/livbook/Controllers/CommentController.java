package com.ryofac.livbook.livbook.Controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ryofac.livbook.livbook.DTOs.CommentDTO;
import com.ryofac.livbook.livbook.Models.Comment;
import com.ryofac.livbook.livbook.Services.CommentService;
import com.ryofac.livbook.livbook.Utils.DTOMapper;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> findCommentById(@PathVariable Long id) {
        Comment found = commentService.findCommentById(id);
        CommentDTO converted = DTOMapper.toCommentDTO(found);
        return ResponseEntity.ok().body(converted);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<List<CommentDTO>> findCommentsByOwnerId(@PathVariable Long id) {
        List<Comment> found = commentService.findCommentsByOwnerId(id);
        List<CommentDTO> converted = found.stream().map(DTOMapper::toCommentDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(converted);
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody Comment toBeCreated) {
        toBeCreated.setId(null);
        commentService.createComment(toBeCreated);
        URI commentLoc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(toBeCreated.getId()).toUri();
        return ResponseEntity.created(commentLoc).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@RequestBody Comment toBeDeleted) {
        commentService.deleteComment(toBeDeleted);
        URI commentLoc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(toBeDeleted.getId()).toUri();
        return ResponseEntity.created(commentLoc).build();
    }

}
