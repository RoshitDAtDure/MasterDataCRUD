package com.example.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tutorials") //name of data model is tutorial with 4 fields : id,title,description,published 
public class Tutorial {
  @Id
  private String id;
  private String idnew;

  private String title;
  private String description;
  private boolean published;

  public Tutorial() {

  }

  //self declaring functions for adding to entries to the database
  public Tutorial(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }

  // get post methods for id,title,description and published is a boolean value thus has set and is methods
  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean isPublished) {
    this.published = isPublished;
  }

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }
}