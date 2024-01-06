package test.crawling.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.crawling.converter.LessonConverter;
import test.crawling.service.CrawlingService;
import test.crawling.dto.CrawlingRequest;
import test.crawling.dto.LessonDetailsDto;
import test.crawling.dto.LessonDto;
import test.crawling.entity.Lesson;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CrawlingController {

    private final CrawlingService crawlingService;

    //https://everytime.kr/@zjmhATXF5czcDHm78zDZ
    //https://everytime.kr/@MVXWO0FP3qdoA0tGis4c

    @PostMapping(value = "/api/crawling")
    public ResponseEntity<LessonDto> crawling(@RequestBody CrawlingRequest crawlingRequest) {
        log.info("enter CrawlingController");

        Lesson action = crawlingService.action(crawlingRequest.getUrl());

        List<LessonDetailsDto> lessonDetailsDtoList = LessonConverter.lessonDetailsDtoList(action.getLessonDetailsList());
        LessonDto lessonDto = LessonDto
                .builder()
                .id(action.getId())
                .userName(action.getUserName())
                .lessonDetailsList(lessonDetailsDtoList)
                .build();

        return new ResponseEntity<>(lessonDto, HttpStatusCode.valueOf(200));
    }
}
