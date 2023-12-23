package com.ryofac.livbook.livbook.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryofac.livbook.livbook.Models.Profile;

public interface IProfileRepository  extends JpaRepository<Profile, Long> {
    
}
