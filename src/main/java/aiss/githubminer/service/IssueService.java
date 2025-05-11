package aiss.githubminer.service;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.commits.MapCommit;
import aiss.githubminer.model.issues.Issue;
import aiss.githubminer.model.issues.Label;
import aiss.githubminer.model.issues.MapIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    public List<Issue> getAllIssues(String repo, String owner) {
        List<Issue> issues = new ArrayList<>();
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";
        MapIssue[] mapIssues = restTemplate.getForObject(uri, MapIssue[].class);

        if (mapIssues != null) {
            for (MapIssue mi : mapIssues) {
                Issue issue = new Issue();
                issue.setId(String.valueOf(mi.getId()));
                issue.setTitle(mi.getTitle());
                issue.setDescription(mi.getBody());
                issue.setState(mi.getState());
                issue.setCreatedAt(mi.getCreatedAt());
                issue.setUpdatedAt(mi.getUpdatedAt());
                issue.setClosedAt(mi.getClosedAt());

                List<Label> labels = null;
                if (mi.getLabels() != null) {
                    labels = mi.getLabels().stream().toList();
                }
                issue.setLabels(labels);

                issue.setVotes(mi.getComments());

                issues.add(issue);
            }
            return issues;
        }

        return Collections.emptyList();
    }
}
