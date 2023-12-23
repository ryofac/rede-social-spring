package com.ryofac.livbook.livbook.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryofac.livbook.livbook.Exceptions.PostException.HashtagNotFoundException;
import com.ryofac.livbook.livbook.Models.Hashtag;
import com.ryofac.livbook.livbook.Repository.IHashtagRepository;

@Service
public class HashtagService {
    public IHashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(IHashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }


    public Hashtag findHashtagById(Long id) {
        Hashtag found = hashtagRepository.findById(id).orElseThrow(() -> new HashtagNotFoundException("Hashtag with id " + id + "not found, Tipo: " + Hashtag.class.getName()));
        return found;
    }

    public Hashtag createHashtag(Hashtag toBeSaved) {
        toBeSaved.setId(null);
        Hashtag saved = hashtagRepository.save(toBeSaved);
        return saved;
    }

    public Hashtag deleteHashtag(Long id){
        Hashtag found = findHashtagById(id);
        try {
            hashtagRepository.deleteById(id);
            return found;
        } catch(Exception e){
            throw new HashtagNotFoundException("Hashtag can't be deleted:" + e.getMessage());
        }

    }

    public Hashtag foundHashtagByTitle(String title){
        return hashtagRepository.findByTitle(title).orElseThrow(() -> new HashtagNotFoundException("Hashtag " + title + "Not found"));
    }

}
