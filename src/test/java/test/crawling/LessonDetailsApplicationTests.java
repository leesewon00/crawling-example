package test.crawling;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.crawling.entity.Lesson;
import test.crawling.entity.LessonDetails;
import test.crawling.repository.LessonDetailsRepository;
import test.crawling.repository.LessonRepository;
import test.crawling.service.CrawlingService;

import java.util.List;

@SpringBootTest
class LessonDetailsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    CrawlingService crawlingService;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    LessonDetailsRepository lessonDetailsRepository;

    @Test
    @Transactional
    void test() {
        Lesson lesson = new Lesson();
        lesson.setUserName("tmp");
        lessonRepository.save(lesson);

        Lesson byUserName = lessonRepository.findByUserName(lesson.getUserName());
        System.out.println(byUserName.getId());
        System.out.println(byUserName.getUserName());
        List<LessonDetails> lessonDetailsList = byUserName.getLessonDetailsList();
        System.out.println(lessonDetailsList.size());


    }

}
