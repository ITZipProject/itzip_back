package darkoverload.itzip.feature.csQuiz.util;

import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    /**
     * QuizCategory 엔티티를 QuizCategoryDetailResponse DTO로 변환하는 메서드
     *
     * @param quizCategory 변환할 QuizCategory 엔티티
     * @return 변환된 QuizCategoryDetailResponse 객체
     */
    public static QuizCategoryDetailResponse entityToResponse(QuizCategory quizCategory) {
        if (quizCategory == null) {
            return null;
        }

        return QuizCategoryDetailResponse.builder()
                .id(quizCategory.getId().intValue()) // Long을 int로 변환
                .categoryname(quizCategory.getCategoryName())
                .build();
    }

    /**
     * QuizCategory 엔티티 리스트를 QuizCategoryDetailResponse DTO 리스트로 변환하는 메서드
     *
     * @param quizCategories 변환할 QuizCategory 엔티티 리스트
     * @return 변환된 QuizCategoryDetailResponse 리스트
     */
    public static List<QuizCategoryDetailResponse> entitiesToResponseList(List<QuizCategory> quizCategories) {
        return quizCategories.stream()
                .map(CategoryMapper::entityToResponse)
                .toList();
    }
}