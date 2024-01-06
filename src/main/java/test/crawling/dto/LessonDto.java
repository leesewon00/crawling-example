package test.crawling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LessonDto {
    private Long id;
    private String userName;
    private List<LessonDetailsDto> lessonDetailsList = new ArrayList<>();
}
