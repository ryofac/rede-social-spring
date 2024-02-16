package com.ryofac.livbook.livbook.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ryofac.livbook.livbook.Models.Comment;

public interface ICommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.owner.id = :ownerId")
    public List<Comment> findAllByOwnerId(@Param("ownerId") Long ownerId);
    
}
