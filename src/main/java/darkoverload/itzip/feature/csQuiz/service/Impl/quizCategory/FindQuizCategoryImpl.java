package darkoverload.itzip.feature.csQuiz.service.Impl.quizCategory;

import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.repository.quizcategory.QuizCategoryRepository;
import darkoverload.itzip.feature.csQuiz.util.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//퀴즈 카테고리 관련 로직을 구현할 Impl
@Service
@RequiredArgsConstructor
public class FindQuizCategoryImpl implements FindQuizCategory {
    private final QuizCategoryRepository quizCategoryRepository;

    /**
     * 주어진 ID에 해당하는 카테고리를 조회하는 메서드
     *
     * @param id 카테고리 ID
     * @return 카테고리 정보, 해당 ID에 대한 카테고리가 존재하지 않는 경우 null 반환
     */
    public QuizCategory CategoryById(Long id) {
        return quizCategoryRepository.findById(id).orElse(null);
    }

    /**
     * 모든 카테고리 조회하는 메서드
     *
     * @return 모든 카테고리 리스트
     */
    public List<QuizCategoryDetailResponse> AllCategory() {
        List<QuizCategory> categories = quizCategoryRepository.findAll();
        return CategoryMapper.entitiesToResponseList(categories);
    }
}