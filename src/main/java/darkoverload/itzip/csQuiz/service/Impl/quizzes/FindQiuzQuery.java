package darkoverload.itzip.csQuiz.service.Impl.quizzes;

import darkoverload.itzip.csQuiz.dto.SortBy;
import darkoverload.itzip.csQuiz.dto.quiz.QuizDetailDto;
import org.springframework.data.domain.Page;

//퀴즈 관련 로직을 담아둘 인터페이스
public interface FindQiuzQuery {
    Page<QuizDetailDto> QuizzesByDifficultyAndCategoryIdAndUserId(
            Integer difficulty, Long categoryId,
            SortBy sortBy, Long userId, boolean solved, int page, int size);
}