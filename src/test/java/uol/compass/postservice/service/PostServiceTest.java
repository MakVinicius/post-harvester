package uol.compass.postservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uol.compass.postservice.entity.History;
import uol.compass.postservice.entity.Post;
import uol.compass.postservice.feignclient.PostConsumerFeign;
import uol.compass.postservice.repository.PostRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uol.compass.postservice.constants.PostConstants.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostConsumerFeign postConsumerFeign;

    @Mock
    private HistoryService historyService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDisablePost_ExistingId() {
        Integer postId = 1;

        when(postRepository.findById(postId)).thenReturn(Optional.of(POST_01));
        when(historyService.addHistory(any(Post.class), any(History.class))).thenReturn(POST_01);
        when(postRepository.save(any(Post.class))).thenReturn(POST_01);

        Post disabledPost = postService.disablePostById(postId);

        assertEquals(POST_01_DISABLED, disabledPost);
    }


}
