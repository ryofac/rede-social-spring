package com.ryofac.livbook.livbook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryofac.livbook.livbook.Models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
