package com.ryofac.livbook.livbook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryofac.livbook.livbook.Models.Profile;

public interface UserRepository  extends JpaRepository<Profile, Long> {
    
}
