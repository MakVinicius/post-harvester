package uol.compass.postservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uol.compass.postservice.entity.History;
import uol.compass.postservice.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService {
    public Post addHistory(Post post, History history) {
        history.setPost(post);
        List<History> existingHistory = new ArrayList<>();
        existingHistory.addAll(post.getHistory());
        existingHistory.add(history);
        post.setHistory(existingHistory);

        return post;
    }
}
