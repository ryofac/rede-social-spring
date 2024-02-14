package com.ryofac.livbook.livbook.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Repositories.IHashtagRepository;
import com.ryofac.livbook.livbook.Services.exceptions.ObjectNotFoundException;

@Service
public class HashtagService {
    public IHashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(IHashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }


    public Hashtag findHashtagById(@NonNull Long id) {
        Hashtag found = hashtagRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Hashtag with id " + id + "not found, Tipo: " + Hashtag.class.getName()));
        return found;
    }

    public Hashtag createHashtag(Hashtag toBeSaved) {
        toBeSaved.setId(null);
        Hashtag saved = hashtagRepository.save(toBeSaved);
        return saved;
    }

    public Hashtag deleteHashtag(@NonNull Long id){
        Hashtag found = findHashtagById(id);
        hashtagRepository.deleteById(id);
        return found;

    }

    public Hashtag foundHashtagByTitle(String title){
        return hashtagRepository.findByTitle(title).orElseThrow(() -> new ObjectNotFoundException("Hashtag " + title + "Not found"));
    }

}
