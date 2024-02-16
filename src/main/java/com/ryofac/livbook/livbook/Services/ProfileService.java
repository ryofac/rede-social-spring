package com.ryofac.livbook.livbook.Services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


import com.ryofac.livbook.livbook.Models.Profile;
import com.ryofac.livbook.livbook.Repositories.IPostRepository;
import com.ryofac.livbook.livbook.Repositories.IProfileRepository;
import com.ryofac.livbook.livbook.Services.exceptions.ObjectNotFoundException;
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
        found.setProfilePhotoUrl(alteredProfile.getProfilePhotoUrl());
        found.setPassword(alteredProfile.getPassword());
        return profileRepository.save(found);
    }

    // A exceção Exception é capturada porque ela pode ser gerada pelo fato do usuário estar relacionado com vários posts
    public void removeProfile(@NonNull Long id){
        Profile found = findbyProfileId(id);
        postRepository.deleteAll(found.getPosts());
        profileRepository.deleteById(found.getId());
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles;
    
    }

    public Profile findbyProfileId(@NonNull Long id) {
        Profile found = profileRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Profile with id " + id + "not found, Tipo: " + Profile.class.getName()));
        return found;
    }

    public Profile findbyProfileDTOId(Long id){
        return findbyProfileId(id);
    }


    
}
