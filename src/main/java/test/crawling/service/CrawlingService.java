package test.crawling.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.crawling.entity.Lesson;
import test.crawling.entity.LessonDetails;
import test.crawling.repository.LessonDetailsRepository;
import test.crawling.repository.LessonRepository;

import java.util.List;

import static test.crawling.converter.CrawlingHelper.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CrawlingService {

    private final LessonRepository lessonRepository;
    private final LessonDetailsRepository lessonDetailsRepository;

    @Transactional
    public Lesson action(String url) {

        log.info("enter CrawlingService");

        // setupCode
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        /* username 탈취 */
        String username = getUserName(driver);

        // save first
        Lesson tmp_lesson = new Lesson();
        tmp_lesson.setUserName(username);
        Lesson lesson = lessonRepository.save(tmp_lesson);
        List<LessonDetails> lessonDetailsList = lesson.getLessonDetailsList();

        /* crawling logic */
        List<LessonDetails> crawlingResults = crawl(driver, lesson, lessonDetailsList);

        // WebDriver 종료
        driver.quit();

        lesson.setLessonDetailsList(crawlingResults);
        return lessonRepository.save(lesson);
    }

    public String getUserName(WebDriver driver) {

        WebElement hamburger = driver.findElement(By.cssSelector(".title"));
        String hamburger_outer = hamburger.getAttribute("outerHTML");
        Document hamburger_doc = org.jsoup.Jsoup.parse(hamburger_outer);

        return hamburger_doc.text();
    }

    public List<LessonDetails> crawl(WebDriver driver, Lesson lesson, List<LessonDetails> lessonDetailsList) {
        /* crawling logic */
        WebElement tableBody = driver.findElement(By.cssSelector(".tablebody"));
        String outer = tableBody.getAttribute("outerHTML");
        Document doc = org.jsoup.Jsoup.parse(outer); // HTML 파싱
        Elements table_cols = doc.select(".cols"); // .cols -> 각 열 추출

        int days_idx = 0;
        String[] days = new String[]{"월", "화", "수", "목", "금", "토", "일"};
        for (Element table_col : table_cols) {

            String now = days[days_idx];

            String col_html = table_col.html();
            Document document = org.jsoup.Jsoup.parse(col_html);
            Elements elements = document.select("div.subject");

            for (Element e : elements) {

                String startTime = topToStartClock(getTop(e));
                String runningTime = heightToRunningTime(getHeight(e));
                List<String> contents = getContents(e);


                LessonDetails tmp = LessonDetails.builder()
                        .title(contents.get(0))
                        .name(contents.get(1))
                        .location(contents.get(2))
                        .startTime(startTime)
                        .runningTime(runningTime)
                        .date(now)
                        .lesson(lesson)
                        .build();
                lessonDetailsList.add(tmp);
                lessonDetailsRepository.save(tmp);
            }

            days_idx++;
        }
        return lessonDetailsList;
    }

}
