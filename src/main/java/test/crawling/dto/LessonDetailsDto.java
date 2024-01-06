package test.crawling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LessonDetailsDto {
    private Long id;
    private String title; // 과목명
    private String name; // 교수명
    private String location; // 강의실
    private String startTime; // 시작시간
    private String runningTime; // 진행시간
    private String date; // 요일
    private Long lessonId;
}
