package com.ryofac.livbook.livbook.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTO.ProfileDTO;
import com.ryofac.livbook.livbook.Models.Profile;
import com.ryofac.livbook.livbook.Repository.IPostRepository;
import com.ryofac.livbook.livbook.Repository.IProfileRepository;

@Service
public class ProfileService implements IProfileService{
    private IPostRepository postRepository;
    private IProfileRepository profileRepository;


    @Autowired
    public ProfileService(IPostRepository postRepository, IProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }


    @Override
    public List<ProfileDTO> getAllProfiles() {
        List<ProfileDTO> profiles = profileRepository.findAll().stream().map(prof -> toProfileDTO(prof)).collect(Collectors.toList());
        return profiles;
        
    }

    // Mapper para o objeto de transferência de perfil
    public ProfileDTO toProfileDTO(Profile profile){
        return ProfileDTO.builder()
                        .id(profile.getId())
                        .email(profile.getEmail())
                        .username(profile.getUsername()) 
                        .profilePhotoUrl(profile.getProfilePhotoUrl())
                        .build();
    }

    
}
