package aiss.githubminer.service;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.issues.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GitHubService {

   @Autowired
   RestTemplate restTemplate;

   private static final String BASE_URL = "https://api.github.com/repos/";

   public List<Commit> getCommits(String owner, String repo) {
      String uri = BASE_URL + owner + "/" + repo + "/commits";
      Commit[] commits = restTemplate.getForObject(uri, Commit[].class);
      if (commits != null) {
         return Arrays.stream(commits).toList();
      }
      return Collections.emptyList();
   }

   public List<Issue> getIssues(String owner, String repo) {
      String uri = BASE_URL + owner + "/" + repo + "/issues";
      Issue[] issues = restTemplate.getForObject(uri, Issue[].class);
      if (issues != null) {
         return Arrays.stream(issues).toList();
      }
      return Collections.emptyList();
   }



}
