package darkoverload.itzip.feature.csQuiz.repository.quiz.custom;

import darkoverload.itzip.feature.csQuiz.entity.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//cs퀴즈 관련 커스텀 코드를 담는 인터페이스
public interface CustomQuizRepository {
    Page<QuizEntity> findByDifficultyAndCategoryAndUserSolved(Integer difficulty, Long categoryId, List<String> userSolved, Pageable pageable);
    Page<QuizEntity> findByDifficultyAndCategory(Integer difficulty, Long categoryId, Pageable pageable);
}