package aiss.githubminer.service;

import aiss.githubminer.model.User;
import aiss.githubminer.model.comments.Comment;
import aiss.githubminer.model.comments.MapComment;
import aiss.githubminer.model.comments.MapUserComment;
import aiss.githubminer.model.projects.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getAllCommentsFromIssue(String repo, String owner) {
        List<Comment> comments = new ArrayList<>();
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/comments";
        MapComment[] mapComments = restTemplate.getForObject(uri, MapComment[].class);

        if (mapComments != null) {
            for (MapComment mc : mapComments) {
                Comment comment = new Comment();
                comment.setId(String.valueOf(mc.getId()));
                comment.setBody(mc.getBody());
                comment.setCreatedAt(mc.getCreatedAt());
                comment.setUpdatedAt(mc.getUpdatedAt());

                MapUserComment mapUser = mc.getUser();
                if (mapUser != null) {
                    User user = new User();
                    user.setId(String.valueOf(mapUser.getId()));
                    user.setUsername(mapUser.getLogin());
                    user.setAvatarUrl(mapUser.getAvatarUrl());
                    user.setWebUrl(mapUser.getHtmlUrl());
                    user.setName(null);
                    comment.setAuthor(user);
                }

                comments.add(comment);
            }
            return comments;
        }

        return Collections.emptyList();
    }

    public Comment getCommentFromIssueById(String id, String repo, String owner) {
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/comments";

        if (getAllCommentsFromIssue(repo, owner) != null) {
            Optional<Comment> commentOpt = getAllCommentsFromIssue(repo, owner)
                    .stream()
                    .filter(comment -> comment.getId().equals(id))
                    .findFirst();
            return commentOpt.orElse(null); // Devuelve null si no se encuentra
        }

        return null;
    }
}
