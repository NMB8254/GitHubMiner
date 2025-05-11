package aiss.githubminer.service;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.issues.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GitMinerService {

    @Autowired
    RestTemplate restTemplate;

    private static final String BASE_URL = "https://localhost:8080/gitminer";

    public void sendDataToGitMiner(List<Commit> commits, List<Issue> issues) {
        restTemplate.postForObject(BASE_URL + "/commits", commits, Void.class);
        restTemplate.postForObject(BASE_URL + "/issues", issues, Void.class);
    }
}
