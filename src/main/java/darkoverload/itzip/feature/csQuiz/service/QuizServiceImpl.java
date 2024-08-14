package darkoverload.itzip.feature.csQuiz.service;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizCreatedRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.SortBy;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.CheckAnswer;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.CreateQuiz;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.GivenPointToQuiz;
import darkoverload.itzip.feature.csQuiz.service.sub.quizCategory.FindQuizCategory;
import darkoverload.itzip.feature.csQuiz.service.sub.quizzes.FindQiuzQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

//컨트롤러가 사용할 service계층 진입점 구현
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    //Impl지정
    @Qualifier("findQuizQuerytImpl")
    private final FindQiuzQuery getFilteredAndSortedQuizzes;

    @Qualifier("findQuizCategoryImpl")
    private final FindQuizCategory getQuizCategory;

    @Qualifier("checkAnswerImpl")
    private final CheckAnswer checkAnswer;

    @Qualifier("givenPointToQuizImpl")
    private final GivenPointToQuiz givenPointToQuiz;

    @Qualifier("createQuizImpl")
    private final CreateQuiz createQuiz;

    /**
     * 주어진 필터와 정렬 기준, 사용자 정보를 기반으로 퀴즈 목록을 조회하는 메서드
     *
     * @param difficulty 퀴즈 난이도 (선택 사항)
     * @param categoryId 카테고리 ID (선택 사항)
     * @param sortBy     정렬 기준 (OLDEST 또는 NEWEST)
     * @param userId     사용자 ID (0이면 모든 사용자)
     * @param solved     사용자가 푼 문제만 조회할지 여부
     * @param page       페이지 번호 (기본값: 0)
     * @param size       페이지 크기 (기본값: 10)
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @Override
    public PagedModel<EntityModel<QuizDetailResponse>> QuizzesByDifficultyAndCategoryIdAndUserId(
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

    /**
     * 모든 카테고리 조회하는 메서드
     *
     * @return 모든 카테고리 리스트
     */
    @Override
    public List<QuizCategoryDetailResponse> AllCategory() {
        return getQuizCategory.AllCategory();
    }

    /**
     * 퀴즈가 정답인지 알아보는 메서드
     * @param quizAnswerRequest 퀴즈 정답을 맞출때 사용하는 객체
     * @return 맞췄는짐 못맞췄는지 알려준다.
     */
    @Override
    public UserQuizStatus checkAnswer(QuizAnswerRequest quizAnswerRequest){
        return checkAnswer.checkAnswer(quizAnswerRequest);
    }

    /**
     * 사용자가 문제에게 점수를 줄때 사용하는 메서드
     * @param quizPointRequest 사용자한테서 받아오는 응답 코드
     * @return 문제를 주는 것을 성공했을 경우 변경된 문제의 점수를 알려준다.
     */
    @Override
    public Integer givenPointToQuiz(QuizPointRequest quizPointRequest){
        return givenPointToQuiz.givenPointToQuiz(quizPointRequest);
    }

    /**
     * 퀴즈를 생성할 때 사용하는 메서드
     * @param quizCreatedRequest 사용자한테서 받아오는 퀴즈 생성 코드 퀴즈 생성자 아이디도 들어있다.
     */
    @Override
    public void createQuiz(QuizCreatedRequest quizCreatedRequest){
        createQuiz.createQuiz(quizCreatedRequest);
    }
}