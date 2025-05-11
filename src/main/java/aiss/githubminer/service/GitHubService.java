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
import java.util.List;

@Service
public class GitHubService {

   @Autowired
   RestTemplate restTemplate;

   private static final String BASE_URL = "https://api.github.com";

   public List<Commit> getCommits(String owner, String repo, int sinceCommits, int maxPages) {
      List<Commit> allCommits = new ArrayList<>();
      String since = LocalDate.now().minusDays(sinceCommits).toString();

      for (int page = 1; page <= maxPages; page++) {
         String url = BASE_URL + "/repos/" + owner + "/" + repo + "/commits"
                 + "?since=" + since + "T00:00:00Z&page=" + page;
         ResponseEntity<Commit[]> response = restTemplate.getForEntity(url, Commit[].class);
         Commit[] commits = response.getBody();

         if (commits == null || commits.length == 0) break;

         allCommits.addAll(Arrays.asList(commits));
      }
      return allCommits;
   }

   public List<Issue> getIssues(String owner, String repo, int sinceIssues, int maxPages) {
      List<Issue> allIssues = new ArrayList<>();
      String since = LocalDate.now().minusDays(sinceIssues).toString();

      for (int page = 1; page <= maxPages; page++) {
         String url = BASE_URL + "/repos/" + owner + "/" + repo + "/issues"
                 + "?since=" + since + "T00:00:00Z&page=" + page;
         ResponseEntity<Issue[]> response = restTemplate.getForEntity(url, Issue[].class);
         Issue[] issues = response.getBody();

         if (issues == null || issues.length == 0) break;

         allIssues.addAll(Arrays.asList(issues));
      }
      return allIssues;
   }


}
