package com.ryofac.livbook.livbook.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryofac.livbook.livbook.Models.Hashtag;

public interface IHashtagRepository extends JpaRepository<Hashtag, Long>{
    
    public Optional<Hashtag> findByTitle(String title);  
}
