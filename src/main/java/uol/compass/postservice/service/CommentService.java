package uol.compass.postservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uol.compass.postservice.entity.Comment;
import uol.compass.postservice.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    public Post addAllComments(Post post, List<Comment> comments) {

        List<Comment> updatedComments = new ArrayList<>();
        for (Comment comment : comments) {
            comment.setPost(post);
            updatedComments.add(comment);
        }

        post.setComments(updatedComments);
        return post;
    }
}
