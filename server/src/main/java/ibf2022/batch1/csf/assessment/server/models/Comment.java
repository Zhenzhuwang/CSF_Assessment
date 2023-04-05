package ibf2022.batch1.csf.assessment.server.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String id;
    private String comment;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public static Comment create(Document d) {
        Comment c = new Comment();
        c.setId(d.getObjectId("Id").toString());
        c.setComment(d.getString("comment"));
        return c;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("charId", getId())
                .add("comment", getComment())
                .build();
    }
    
    
}
