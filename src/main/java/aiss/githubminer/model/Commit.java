package aiss.githubminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commits")
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "authorEmail")
    private String authorEmail;

    @Column(name = "authoredDate")
    private LocalDateTime authoredDate;

    @Column(name = "webUrl")
    private String webUrl;


    public Commit() {

    }

    public Commit(String title, String message, String authorName, String authorEmail, LocalDateTime authoredDate, String webUrl) {
        setTitle(title);
        setMessage(message);
        setAuthorName(authorName);
        setAuthorEmail(authorEmail);
        setAuthoredDate(authoredDate);
        setWebUrl(webUrl);
    }


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getAuthorName() { return authorName; }

    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }

    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public LocalDateTime getAuthoredDate() { return authoredDate; }

    public void setAuthoredDate(LocalDateTime authoredDate) { this.authoredDate = authoredDate; }

    public String getWebUrl() { return webUrl; }

    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }
}
