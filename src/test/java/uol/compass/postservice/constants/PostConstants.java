package uol.compass.postservice.constants;

import uol.compass.postservice.entity.Comment;
import uol.compass.postservice.entity.History;
import uol.compass.postservice.entity.Post;
import uol.compass.postservice.entity.State;

import java.time.LocalDateTime;
import java.util.List;

public class PostConstants {
    public static History HISTORY_CREATED = new History(LocalDateTime.now(), "CREATED");
    public static History HISTORY_POST_FIND = new History(LocalDateTime.now(), "POST_FIND");
    public static History HISTORY_POST_OK = new History(LocalDateTime.now(), "POST_OK");
    public static History HISTORY_COMMENTS_FIND = new History(LocalDateTime.now(), "COMMENTS_FIND");
    public static History HISTORY_COMMENTS_OK = new History(LocalDateTime.now(), "COMMENTS_OK");

    public static History HISTORY_ENABLED = new History(LocalDateTime.now(), "ENABLED");


    public static Comment COMMENT_01 = new Comment(1, "Name 1", "email1@mail.com", "Example Body 1", new Post());
    public static Comment COMMENT_02 = new Comment(2, "Name 2", "email2@mail.com", "Example Body 2", new Post());
    public static Comment COMMENT_03 = new Comment(3, "Name 3", "email3@mail.com", "Example Body 3", new Post());
    public static Comment COMMENT_04 = new Comment(4, "Name 4", "email4@mail.com", "Example Body 4", new Post());
    public static Comment COMMENT_05 = new Comment(5, "Name 5", "email5@mail.com", "Example Body 5", new Post());

    public static Post POST_01 = new Post(
            1,
            1,
            "Example Title",
            "Example Body",
            State.ENABLED,
            false,
            List.of(),
            List.of(COMMENT_01, COMMENT_02, COMMENT_03, COMMENT_04, COMMENT_05),
            List.of(HISTORY_CREATED, HISTORY_POST_FIND, HISTORY_POST_OK, HISTORY_COMMENTS_FIND, HISTORY_COMMENTS_OK, HISTORY_ENABLED)
    );

    public static Post POST_01_DISABLED = new Post(
            1,
            1,
            "Example Title",
            "Example Body",
            State.DISABLED,
            false,
            List.of(),
            List.of(COMMENT_01, COMMENT_02, COMMENT_03, COMMENT_04, COMMENT_05),
            List.of(HISTORY_CREATED, HISTORY_POST_FIND, HISTORY_POST_OK, HISTORY_COMMENTS_FIND, HISTORY_COMMENTS_OK, HISTORY_ENABLED)
    );
}
