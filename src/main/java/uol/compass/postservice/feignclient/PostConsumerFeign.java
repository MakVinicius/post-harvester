package uol.compass.postservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uol.compass.postservice.entity.Comment;
import uol.compass.postservice.entity.Post;

import java.util.List;

@FeignClient(value = "POST-HARVESTER", url = "https://jsonplaceholder.typicode.com/")
public interface PostConsumerFeign {

    @GetMapping(value = "/posts/{postId}")
    Post getPostById(@PathVariable("postId") Integer postId);


    @GetMapping(value = "/posts/{postId}/comments")
    List<Comment> getCommentsByPostId(@PathVariable("postId") Integer postId);
}
