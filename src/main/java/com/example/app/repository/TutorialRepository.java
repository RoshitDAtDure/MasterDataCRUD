package com.example.app.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository; 
//allows us to use methods from mongorepo such as  save(), findOne(), findById(), findAll(), count(), delete(), deleteById()

import com.example.app.model.Tutorial;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {
  List<Tutorial> findByTitleContaining(String title);
  List<Tutorial> findByPublished(boolean published);
}
