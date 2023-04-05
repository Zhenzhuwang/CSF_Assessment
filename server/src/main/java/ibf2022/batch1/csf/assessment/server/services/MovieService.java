package ibf2022.batch1.csf.assessment.server.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch1.csf.assessment.server.models.Comment;
import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;;

@Service
public class MovieService {

public static final String NYTURL = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";

	@Autowired
	private MovieRepository movieRepository;

	@Value("${CSFAssessment.review.api.url}")
	private String reviewapiUrl;

	@Value("${CSFAssessment.review.api.key}")
	private String reviewPublicApiKey;

	@Value("${CSFAssessment.review.priv.key}")
	private String reviewPrivateApiKey;

	private String[] getReviewApiHash(){
		String[] result = new String[2];
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long tsVal = timestamp.getTime();
		String hashVal = tsVal + reviewPrivateApiKey + reviewPublicApiKey;
		result[0] = tsVal + "";
		result[1] = DigestUtils.md5Hex(hashVal);
		return result;
	}



	// TODO: Task 4
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	public List<Review> searchReviews(String query) {
		ResponseEntity<String> resp = null;
		List<Review> r = null;
		String[] l = getReviewApiHash();
		String reviewMovieApiUrl = UriComponentsBuilder
									.fromUriString(reviewapiUrl + "movies")
									.queryParam("ts", l[0].trim())
									.queryParam("apikey", reviewPublicApiKey.trim())
									.queryParam("hash", l[1])
									.queryParam("titleStartsWith", query.replaceAll(" ", "+"))
									.toUriString();
		RestTemplate template = new RestTemplate();
		resp = template.getForEntity(reviewMovieApiUrl, String.class);
		try {
			r = Review.create(resp.getBody());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (r != null) 
			return r;
		return null;
		}
		

	

	public Optional<List<Review>> getMovies(String movieName) throws IOException {
		ResponseEntity<String> resp = null;
		Review r = null;
		String[] l = getReviewApiHash();
		String reviewMovieApiUrl = UriComponentsBuilder
										.fromUriString(reviewapiUrl + "movies"/ + movieName)
										.queryParam("ts", l[0].trim())
										.queryParam("apikey", reviewPublicApiKey.trim())
										.queryParam("hash", l[1])
										.toUriString();
		RestTemplate template = new RestTemplate();
		resp = template.getForEntity(reviewMovieApiUrl, String.class);
		List<Review> lr = Review.create(resp.getBody());
		r = lr.get(0);
		if(r != null)
			return r;
		return null;
	}



    public Comment insertComment(Comment c) {
        return movieRepository.insertComment(c);
    }

	public List<Comment> getAllComments(String id){
		return movieRepository.getAllComments(id);
	}

}
