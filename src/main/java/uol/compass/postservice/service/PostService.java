package uol.compass.postservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.postservice.dto.AllPostsDTO;
import uol.compass.postservice.entity.*;
import uol.compass.postservice.feignclient.PostConsumerFeign;
import uol.compass.postservice.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostConsumerFeign postConsumerFeign;
    private final HistoryService historyService;
    private final CommentService commentService;

    public Post savePost(Integer postId) {

        if (postId < 1 || postId > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post id should be a number between 1 and 100");
        }

        Optional<Post> existingPost = postRepository.findById(postId);

        if (existingPost.isPresent()) {

            if (existingPost.get().getState() == State.UPDATING) {
                Post post = enrichPost(existingPost.get());

                post.setReprocessed(true);
                post = postRepository.save(post);

                return post;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The post id already has been created and is not in the UPDATING state");
            }

        } else {
            History history = new History(LocalDateTime.now(), "CREATED");

            Post post = new Post(postId, State.CREATED, List.of(history));
            post = historyService.addHistory(post, history);

            post = enrichPost(post);

            return post;
        }
    }

    private Post enrichPost(Post post) {

        post.setState(State.POST_FIND);
        historyService.addHistory(post, new History(LocalDateTime.now(), "POST_FIND"));

        Post foundPost = postConsumerFeign.getPostById(post.getId());

        if (foundPost.getBody() == null || foundPost.getBody().isEmpty()) {
            post.setState(State.FAILED);
            historyService.addHistory(post, new History(LocalDateTime.now(), "FAILED"));

            post.setState(State.DISABLED);
            historyService.addHistory(post, new History(LocalDateTime.now(), "DISABLED"));
            postRepository.save(post);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data from post is either null or empty");
        } else {
            post.setState(State.POST_OK);
            historyService.addHistory(post, new History(LocalDateTime.now(), "POST_OK"));

            post.setUserId(foundPost.getUserId());
            post.setTitle(foundPost.getTitle());
            post.setBody(foundPost.getBody());

            post = insertComments(post);

            return post;
        }
    }

    private Post insertComments(Post post) {
        post.setState(State.COMMENTS_FIND);
        historyService.addHistory(post, new History(LocalDateTime.now(), "COMMENTS_FIND"));

        List<Comment> foundComments = postConsumerFeign.getCommentsByPostId(post.getId());

        if (foundComments.isEmpty() || foundComments == null) {
            post.setState(State.FAILED);
            historyService.addHistory(post, new History(LocalDateTime.now(), "FAILED"));

            post.setState(State.DISABLED);
            historyService.addHistory(post, new History(LocalDateTime.now(), "DISABLED"));
            postRepository.save(post);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comments returned are either null or empty");
        } else {
            post.setComments(foundComments);

            post.setState(State.COMMENTS_OK);
            historyService.addHistory(post, new History(LocalDateTime.now(), "COMMENTS_OK"));
            commentService.addAllComments(post, foundComments);

            post.setState(State.ENABLED);
            historyService.addHistory(post, new History(LocalDateTime.now(), "ENABLED"));
            post = postRepository.save(post);

            return post;
        }
    }

    public Post getPostById(Integer postId) {
        if (postId < 1 || postId > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post id should be a number between 1 and 100");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The post with the specified id has not been created yet")
                );

        return post;
    }

    public AllPostsDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        Pageable pageable;

        if (direction.equals("desc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, sortBy);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortBy);
        }
        Page<Post> posts = postRepository.findAll(pageable);

        AllPostsDTO allPostsDTO = new AllPostsDTO<>(
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalPages(),
                posts.getTotalElements(),
                posts.isLast(),
                posts.get().collect(Collectors.toList())
        );

        return allPostsDTO;
    }

    public Post reprocessPost(Integer postId) {

        if (postId < 1 || postId > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post id should be a number between 1 and 100");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The post with the specified id has not been created yet")
                );

        if (post.getState() != State.ENABLED && post.getState() != State.DISABLED) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Only posts in the ENABLED or DISABLED state can be reprocessed");
        }

        post.setState(State.UPDATING);
        historyService.addHistory(post, new History(LocalDateTime.now(), "UPDATING"));
        post = postRepository.save(post);

        return post;
    }

    public Post disablePostById(Integer postId) {

        if (postId < 1 || postId > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post id should be a number between 1 and 100");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The post with the specified id has not been created yet")
                );

        if (post.getState() != State.ENABLED) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Post State is not in the ENABLED mode");
        }

        post.setState(State.DISABLED);
        historyService.addHistory(post, new History(LocalDateTime.now(), "DISABLED"));
        post = postRepository.save(post);

        return post;
    }
}
