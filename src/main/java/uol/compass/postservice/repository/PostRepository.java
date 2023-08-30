package uol.compass.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.postservice.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
