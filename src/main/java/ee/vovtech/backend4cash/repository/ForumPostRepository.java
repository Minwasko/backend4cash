package ee.vovtech.backend4cash.repository;

import ee.vovtech.backend4cash.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
}
