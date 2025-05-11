package aiss.githubminer.service;

import aiss.githubminer.model.commits.Commit;
import aiss.githubminer.model.commits.MapCommit;
import aiss.githubminer.model.projects.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public Commit getCommitById(String sha, String repo, String owner) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/commits";

        if (getAllCommits(repo, owner) != null) {
            Optional<Commit> commitOpt = getAllCommits(repo, owner)
                    .stream()
                    .filter(commit -> commit.getId().equals(sha))
                    .findFirst();
            return commitOpt.orElse(null); // Devuelve null si no se encuentra
        }

        return null;
    }
}
