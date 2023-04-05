package ibf2022.batch1.csf.assessment.server.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

// DO NOT MODIFY THIS CLASS
public class Review {
	// display_title
	private String title;
	// mpaa_rating
	private String rating;
	// byline
	private String byline;
	// headline
	private String headline;
	// summary_short 
	private String summary;
	// link.url
	private String reviewURL;
	// multimedia.src
	private String image = null;

	private int commentCount = 0;

	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return this.title; }

	public void setRating(String rating) { this.rating = rating; }
	public String getRating() { return this.rating; }

	public void setByline(String byline) { this.byline = byline; }
	public String getByline() { return this.byline; }

	public void setHeadline(String headline) { this.headline = headline; }
	public String getHeadline() { return this.headline; }

	public void setSummary(String summary) { this.summary = summary; }
	public String getSummary() { return this.summary; }

	public void setReviewURL(String reviewURL) { this.reviewURL = reviewURL; }
	public String getReviewURL() { return this.reviewURL; }

	public void setImage(String image) { this.image = image; }
	public String getImage() { return this.image; }
	public boolean hasImage() { return null != this.image; }

	public void setCommentCount(int commentCount) { this.commentCount = commentCount; };
	public int getCommentCount() { return this.commentCount; }


	@Override
	public String toString() {
		return "Review{title:%s, rating:%s}".formatted(title, rating);
	}

	public static Review createJson(JsonObject o){
		Review m = new Review();
		// JsonObject t = o.getJsonObject("src");
		// String path = t.getString("path");
		// String ext = t.getString("extension");
		
		// m.title = o.getString("display_title");
		// m.rating = o.getString("mpaa_rating");
		// m.byline = o.getString("byline");
		// m.headline = o.getString("headline");
		// m.summary = o.getString("summary_short");
		// m.reviewURL = o.getString("url");
		// m.image = path + '.' + ext;

		m.setTitle(o.getString("display_title"));
		m.setRating(o.getString("mpaa_rating"));
		m.setByline(o.getString("byline"));
		m.setHeadline(o.getString("headline"));
		m.setSummary(o.getString("summary_short"));
		JsonObject link = o.getJsonObject("link");
		m.setReviewURL(link.getString("url"));

		return m;
	}

	public static List<Review> create(String json) throws IOException {
		List<Review> r = new LinkedList<>();
		try(InputStream is = new ByteArrayInputStream(json.getBytes())){
			JsonReader js = Json.createReader(is);
			JsonObject o = js.readObject();
			JsonObject oo = o.getJsonObject("data");
			if(oo.getJsonArray("results") != null)
				r = oo.getJsonArray("results").stream()
												.map(v -> (JsonObject)v)
												.map(v-> Review.createJson(v))
												.toList();
		}
		return r;
		
	}


	public JsonObject toJson() {
		return Json.createObjectBuilder()
					.add("title", getTitle())
					.add("rating", getRating())
					.add("byline", getByline())
					.add("headline", getHeadline())
					.add("summary", getSummary())
					.add("reviewURL", getReviewURL())
					.add("image", getImage())
					.build();
	}
	
}
