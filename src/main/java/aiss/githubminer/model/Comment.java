package aiss.githubminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commits")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "body")
    private String body;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;


    public Comment() {

    }

    public Comment(String title, String body, String createdAt, String updatedAt) {
        setBody(body);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
