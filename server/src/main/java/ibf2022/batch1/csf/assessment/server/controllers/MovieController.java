package ibf2022.batch1.csf.assessment.server.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch1.csf.assessment.server.models.Comment;
import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.services.MovieService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

@RestController
@RequestMapping(path="/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {
	// TODO: Task 3 : write a getrequest
private Logger logger = LoggerFactory.getLogger(MovieController.class);

@Autowired
private MovieService movieSvc;

@GetMapping("/search")
public ResponseEntity<String> getMovies(@RequestParam(required = true) String movieName) throws IOException{
	JsonArray result = null;
	Optional<List<Review>> or = this.movieSvc.getMovies(movieName);
	List<Review> results = or.get();
	JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
	for (Review r : results)
	result = arrBuilder.build();
	return ResponseEntity
		.status(HttpStatus.OK)
		.contentType(MediaType.APPLICATION_JSON)
		.body(result.toString());
}


	
	
	
	//Task 4, 
	
	
	//Task 8

	@PostMapping(path="comments/{id}")
	public ResponseEntity<String> postComment(@RequestBody Comment comment,@PathVariable (required = true) String id) {
		Comment c = new Comment();
		c.setComment(comment.getComment());
		c.setId(id);
		Comment r = this.movieSvc.insertComment(c);
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(r.toJSON().toString());
	}

}
