package test.crawling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.crawling.entity.LessonDetails;

@Repository
public interface LessonDetailsRepository extends JpaRepository<LessonDetails, Long> {
}
