package com.ryofac.livbook.livbook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryofac.livbook.livbook.Models.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>{
    
}
