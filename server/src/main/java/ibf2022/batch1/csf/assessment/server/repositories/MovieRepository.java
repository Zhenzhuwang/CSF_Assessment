package ibf2022.batch1.csf.assessment.server.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import ibf2022.batch1.csf.assessment.server.models.Comment;
import ibf2022.batch1.csf.assessment.server.models.Review;

@Repository
public class MovieRepository {

	@Autowired 
	private MongoTemplate mongoTemplate;

	
	private static final String COMMENTS_COL = "comments";

	// TODO: Task 5
	// You may modify the parameter but not the return type
	// Write the native mongo database query in the comment below

	/* 
	 * db.comments.find({movieName: ?}).count()
	 */
	public int countComments(String movieName) {
		Query query = Query.query(Criteria.where("movieName").is(movieName));
		Long comment = mongoTemplate.count(query, COMMENTS_COL);
		Integer result = comment.intValue();
		return result;
	}

	// TODO: Task 8
	// Write a method to insert movie comments comments collection
	// Write the native mongo database query in the comment below
	//
	public Comment insertComment(Comment c) {
        return mongoTemplate.insert(c, COMMENTS_COL);
    }

    public List<Comment> getAllComments(String id) {
		Pageable pageable = PageRequest.of(0,10);
		Query query = new Query()
						.addCriteria(Criteria.where("id").is(id))
						.with(pageable);
        List<Comment> lc = mongoTemplate.find(query, Comment.class, COMMENTS_COL);
		Page<Comment> pQuery = PageableExecutionUtils.getPage(query, pageable,()-> mongoTemplate.count(query,Comment.class));
		return pQuery.toList();
    }
}
