package aiss.githubminer.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private String state;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "closedAt")
    private String closedAt;

    @ElementCollection
    @CollectionTable() //no se que hay que poner aqui
    @Column(name = "labels")
    private List<String> labels;

    @Column(name = "votes")
    private Integer votes;


    public Issue() {

    }

    public Issue(String title, String description, String state, String createdAt, String updatedAt, String closedAt, List<String> labels, Integer votes) {
        setTitle(title);
        setDescription(description);
        setState(state);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setClosedAt(closedAt);
        setLabels(labels);
        setVotes(votes);
    }


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getClosedAt() { return closedAt; }

    public void setClosedAt(String closedAt) { this.closedAt = closedAt; }

    public List<String> getLabels() { return labels; }

    public void setLabels(List<String> labels) { this.labels = labels; }

    public Integer getVotes() { return votes; }

    public void setVotes(Integer votes) { this.votes = votes; }
}
