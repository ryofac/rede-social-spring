package com.ryofac.livbook.livbook.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.DTO.ProfileDTO;
import com.ryofac.livbook.livbook.Exceptions.ProfileException.ProfileException;
import com.ryofac.livbook.livbook.Exceptions.ProfileException.ProfileNotFoundException;
import com.ryofac.livbook.livbook.Models.Profile;
import com.ryofac.livbook.livbook.Repository.IPostRepository;
import com.ryofac.livbook.livbook.Repository.IProfileRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfileService {
    private IPostRepository postRepository;
    private IProfileRepository profileRepository;


    @Autowired
    public ProfileService(IPostRepository postRepository, IProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Profile createProfile(Profile profile) {
        profile.setId(null);
        postRepository.saveAll(profile.getPosts());
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile alteredProfile) {
        Profile found = findbyProfileId(alteredProfile.getId());
        found.setEmail(alteredProfile.getEmail());
        found.setUsername(alteredProfile.getUsername());
        found.setProfilePhotoUrl(alteredProfile.getProfilePhotoUrl());
        return profileRepository.save(found);
    }

    // A exceção Exception é capturada porque ela pode ser gerada pelo fato do usuário estar relacionado com vários posts
    public void removeProfile(Profile toBeRemoved){
        Profile found = findbyProfileId(toBeRemoved.getId());
        try{
            profileRepository.deleteById(found.getId());
        } catch(Exception e){
            throw new ProfileException("Profile can't be deleted:" + e.getMessage());
        }
    }

    public List<ProfileDTO> getAllProfiles() {
        List<ProfileDTO> profiles = profileRepository.findAll().stream().map(prof -> toProfileDTO(prof)).collect(Collectors.toList());
        return profiles;
    
    }

    public Profile findbyProfileId(Long id) {
        Profile found = profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException("Profile with id " + id + "not found, Tipo: " + Profile.class.getName()));
        return found;
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
