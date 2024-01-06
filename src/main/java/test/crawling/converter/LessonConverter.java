package test.crawling.converter;

import test.crawling.dto.LessonDetailsDto;
import test.crawling.entity.LessonDetails;

import java.util.ArrayList;
import java.util.List;

public class LessonConverter {

    public static List<LessonDetailsDto> lessonDetailsDtoList(List<LessonDetails> lessonDetailsList) {

        List<LessonDetailsDto> lessonDetailsDtoList = new ArrayList<>();

        for (LessonDetails lessonDetails : lessonDetailsList) {
            LessonDetailsDto lessonDetailsDto = LessonDetailsDto
                    .builder()
                    .id(lessonDetails.getId())
                    .title(lessonDetails.getTitle())
                    .name(lessonDetails.getName())
                    .location(lessonDetails.getLocation())
                    .startTime(lessonDetails.getStartTime())
                    .runningTime(lessonDetails.getRunningTime())
                    .date(lessonDetails.getDate())
                    .lessonId(lessonDetails.getLesson().getId())
                    .build();
            lessonDetailsDtoList.add(lessonDetailsDto);
        }

        return lessonDetailsDtoList;
    }
}
