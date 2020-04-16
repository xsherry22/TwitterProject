package com.tts.TwitterProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TwitterProject.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>  {
    Tag findByPhrase(String phrase);
}