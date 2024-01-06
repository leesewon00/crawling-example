package test.crawling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.crawling.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByUserName(String userName);
}
