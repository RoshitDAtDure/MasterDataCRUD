package com.example.app.controller;

import java.util.ArrayList;

//main class which defines how all APIs will function. 


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.model.Tutorial;
import com.example.app.repository.TutorialRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api") //to identify different controllers created. In this case only one is created.   


public class TutorialController {

  @Autowired
  TutorialRepository tutorialRepository;

  //We use @GetMapping annotation for handling GET HTTP requests, then Repository’s
  //getAllTutorials(): returns List of Tutorials, if there is title parameter, it returns a List in that each Tutorial contains the title
  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		  try {
		    List<Tutorial> tutorials = new ArrayList<Tutorial>(); //creates a new element of tutorial

		    if (title == null) //if no title is specified all tutorials instances are added to repo
		      tutorialRepository.findAll().forEach(tutorials::add);
		    else //otherwise title is matched and then added to repo
		      tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

		    if (tutorials.isEmpty()) { //if tutorial has empty fields given, it shows no content error
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		    return new ResponseEntity<>(tutorials, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
  }

  //We use @GetMapping annotation for handling GET HTTP requests, then Repository’s
  //getTutorialById(): returns Tutorial by given id
  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
	//_tutorial is a temporary variable created for finding tutorial instance matching the given id
	  Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

	  if (tutorialData.isPresent()) {
	    return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}


  
  //CREATE OPERATION
  //We use @PostMapping annotation for handling POST HTTP requests.
  @PostMapping("/tutorials") 
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
	    try {	
	      Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
	      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	    } 
	    catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
 

  //@PutMapping will help us handle PUT HTTP requests.
  //– updateTutorial() receives id and a Tutorial payload.
  //– from the id, we get the Tutorial from database using findById() method.
  //– then we use the payload and save() method for updating the Tutorial.


  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
	  Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

	  if (tutorialData.isPresent()) {
	    Tutorial _tutorial = tutorialData.get();
	    _tutorial.setTitle(tutorial.getTitle());
	    _tutorial.setDescription(tutorial.getDescription());
	    _tutorial.setPublished(tutorial.isPublished());
	    return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}

  
  /*We use @DeleteMapping for DELETE HTTP requests.
  There are 2 methods:

	  deleteTutorial(): delete a Tutorial document with given id
	  deleteAllTutorials(): remove all documents in tutorials collection
   The operations is done with the help of MongoRepository’s deleteById() and deleteAll() method.
  */
  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
	  try {
	    tutorialRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	  try {
	    tutorialRepository.deleteAll();
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

  //We use @GetMapping annotation for handling GET HTTP requests, then Repository’s
  //findByPublished(): return published Tutorials
  @GetMapping("/tutorials/published")
  public ResponseEntity<List<Tutorial>> findByPublished() {
	  try {
	    List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

	    if (tutorials.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(tutorials, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
}
