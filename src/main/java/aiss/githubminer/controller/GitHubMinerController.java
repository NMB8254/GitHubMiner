package aiss.githubminer.controller;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.issues.Issue;
import aiss.githubminer.service.GitHubService;
import aiss.githubminer.service.GitMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubMinerController {

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private GitMinerService gitMinerService;

    @PostMapping("/{owner}/{repoName}")
    public String fetchAndSendData(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages) {

        List<Commit> commits = gitHubService.getCommits(owner, repoName, sinceCommits, maxPages);
        List<Issue> issues = gitHubService.getIssues(owner, repoName, sinceIssues, maxPages);

        gitMinerService.sendDataToGitMiner(commits, issues);

        return "Datos enviados correctamente a GitMiner";
    }

    @GetMapping("/{owner}/{repoName}")
    public Map<String, Object> fetchPreview(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages) {

        Map<String, Object> preview = new HashMap<>();
        preview.put("commits", gitHubService.getCommits(owner, repoName, sinceCommits, maxPages));
        preview.put("issues", gitHubService.getIssues(owner, repoName, sinceIssues, maxPages));
        return preview;
    }

}