package ee.vovtech.backend4cash.repository;

import ee.vovtech.backend4cash.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
}
