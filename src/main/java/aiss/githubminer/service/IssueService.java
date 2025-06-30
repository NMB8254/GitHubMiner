package aiss.githubminer.service;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.commits.MapCommit;
import aiss.githubminer.model.issues.Issue;
import aiss.githubminer.model.issues.Label;
import aiss.githubminer.model.issues.MapIssue;
import aiss.githubminer.model.projects.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    public List<Issue> getAllIssues(String owner, String repo, int sinceIssues, int maxPages) {
        List<Issue> issues = new ArrayList<>();
        String baseUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";
        LocalDateTime sinceDateTime = LocalDateTime.now().minusDays(sinceIssues);
        String sinceIso = sinceDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        for (int page = 1; page <= maxPages; page++) {
            String uri = baseUrl + "?since=" + sinceIso + "&page=" + page + "&per_page=100";
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

                    List<String> labels = mi.getLabels().stream().map(Label::getName).toList();
                    issue.setLabels(labels);

                    issue.setVotes(mi.getComments());

                    issues.add(issue);
                }
                return issues;
            }
        }
        return Collections.emptyList();
    }

    public Issue getIssueById(String id, String repo, String owner, int sinceIssues, int maxPages) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";

        if (getAllIssues(repo, owner, sinceIssues, maxPages) != null) {
            Optional<Issue> issueOpt = getAllIssues(repo, owner, sinceIssues, maxPages)
                    .stream()
                    .filter(issue -> issue.getId().equals(id))
                    .findFirst();
            return issueOpt.orElse(null); // Devuelve null si no se encuentra
        }

        return null;
    }

    public List<Issue> getIssuesByAuthorId(String authorId, String repo, String owner, int sinceIssues, int maxPages) {
        List<Issue> allIssues = getAllIssues(repo, owner, sinceIssues, maxPages);
        if (allIssues == null || allIssues.isEmpty()) {
            return Collections.emptyList();
        }

        return allIssues.stream()
                .filter(issue -> issue.getAuthor() != null && authorId.equals(issue.getAuthor().getId()))
                .toList();

    }

    public List<Issue> getIssuesByState(String state, String repo, String owner, int sinceIssues, int maxPages) {
        List<Issue> allIssues = getAllIssues(repo, owner, sinceIssues, maxPages);
        if (allIssues == null || allIssues.isEmpty()) {
            return Collections.emptyList();
        }

        return allIssues.stream()
                .filter(issue -> issue.getState() != null &&
                        issue.getState().equalsIgnoreCase(state))
                .toList();
    }
}
