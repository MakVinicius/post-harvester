package uol.compass.postservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.postservice.dto.AllPostsDTO;
import uol.compass.postservice.dto.ResponseDTO;
import uol.compass.postservice.entity.Post;
import uol.compass.postservice.service.PostService;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> processPost(@PathVariable Integer postId) {
        Post post = postService.savePost(postId);
        return new ResponseEntity<>(
                new ResponseDTO<>(post),
                HttpStatus.OK
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Integer postId) {
        Post post = postService.getPostById(postId);
        return new ResponseEntity<>(
                new ResponseDTO<>(post),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<AllPostsDTO> getAllPosts(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "4", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc", required = false) String direction
    ) {
        AllPostsDTO allPosts = postService.getAllPosts(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> reprocessPost(@PathVariable Integer postId) {
        Post post = postService.reprocessPost(postId);
        return new ResponseEntity<>(
                new ResponseDTO<>(post),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> disablePost(@PathVariable Integer postId) {
        Post post = postService.disablePostById(postId);
        return new ResponseEntity<>(
                new ResponseDTO<>(post),
                HttpStatus.OK
        );
    }
}
