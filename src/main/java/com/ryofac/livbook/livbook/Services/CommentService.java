package com.ryofac.livbook.livbook.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.Models.Comment;
import com.ryofac.livbook.livbook.Repositories.ICommentRepository;
import com.ryofac.livbook.livbook.Services.exceptions.ObjectNotFoundException;


@Service

public class CommentService {

    @Autowired
    ICommentRepository commentRepository;

    public void createComment(@NonNull Comment toBeCreated) {
        commentRepository.save(toBeCreated);
    }

    public void updateComment(@NonNull Comment toBeUpdated){
        Comment existent = findCommentById(toBeUpdated.getId());
        existent.setContent(toBeUpdated.getContent());
        commentRepository.save(existent);
    }

    public void deleteComment(@NonNull Comment toBeDeleted) {
        Comment existent = findCommentById(toBeDeleted.getId());
        commentRepository.deleteById(existent.getId());
    }

    public Comment findCommentById(@NonNull Long id){
        return commentRepository.findById(id).orElseThrow(() 
        -> new ObjectNotFoundException(String.format("Comment with this id %d not found", id)));
    }

    public List<Comment> findCommentsByOwnerId(@NonNull Long ownerId) {
        List<Comment> found =  commentRepository.findAllByOwnerId(ownerId);
        if(found.isEmpty()) throw new ObjectNotFoundException("Comments with this owner not found");
        return found;
    }

}