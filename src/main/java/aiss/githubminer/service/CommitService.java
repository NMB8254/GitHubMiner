package aiss.githubminer.service;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.commits.MapCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    public List<Commit> getAllCommits(String repo, String owner) {
        List<Commit> commits = new ArrayList<>();
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";
        MapCommit[] mapCommits = restTemplate.getForObject(uri, MapCommit[].class);

        if (mapCommits != null) {
            for (MapCommit mc : mapCommits) {
                Commit commit = new Commit();
                commit.setId(mc.getSha());
                commit.setTitle(null); // aqui no se que poner porque no viene ningun atributo llamado tittle
                commit.setMessage(mc.getCommit().getMessage());
                commit.setAuthorName(mc.getCommit().getAuthor().getName());
                commit.setAuthorEmail(mc.getCommit().getAuthor().getEmail());
                commit.setAuthoredDate(mc.getCommit().getAuthor().getDate());
                commit.setWebUrl(mc.getUrl());

                commits.add(commit);
            }
            return commits;
        }

        return Collections.emptyList();
    }
}
