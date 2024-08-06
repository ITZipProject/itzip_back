package darkoverload.itzip.feature.csQuiz.service;

import darkoverload.itzip.feature.csQuiz.dto.SortBy;
import darkoverload.itzip.feature.csQuiz.dto.quiz.QuizDetailDto;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.service.Impl.quizCategory.FindQuizCategory;
import darkoverload.itzip.feature.csQuiz.service.Impl.quizzes.FindQiuzQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

//컨트롤러가 사용할 service계층 진입점 구현
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    //Impl지정
    @Qualifier("findQuizQuerytImpl")
    private final FindQiuzQuery getFilteredAndSortedQuizzes;

    //Impl지정
    @Qualifier("findQuizCategoryImpl")
    private final FindQuizCategory getQuizCategory;

    /**
     * 주어진 필터와 정렬 기준, 사용자 정보를 기반으로 퀴즈 목록을 조회하는 메서드
     *
     * @param difficulty 퀴즈 난이도 (선택 사항)
     * @param categoryId 카테고리 ID (선택 사항)
     * @param sortBy 정렬 기준 (OLDEST 또는 NEWEST)
     * @param userId 사용자 ID (0이면 모든 사용자)
     * @param solved 사용자가 푼 문제만 조회할지 여부
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @Override
    public Page<QuizDetailDto> QuizzesByDifficultyAndCategoryIdAndUserId(
            Integer difficulty, Long categoryId,
            SortBy sortBy, Long userId, boolean solved, int page, int size) {
        return getFilteredAndSortedQuizzes.QuizzesByDifficultyAndCategoryIdAndUserId(
                difficulty, categoryId, sortBy, userId, solved, page, size);
    }

    /**
     * 주어진 ID에 해당하는 카테고리를 조회하는 메서드
     *
     * @param id 카테고리 ID
     * @return 카테고리 정보, 해당 ID에 대한 카테고리가 존재하지 않는 경우 null 반환
     */
    @Override
    public QuizCategory CategoryById(Long id) {
        return getQuizCategory.CategoryById(id);
    }
}