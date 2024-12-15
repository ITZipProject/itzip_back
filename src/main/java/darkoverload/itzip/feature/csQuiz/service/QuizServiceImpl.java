package darkoverload.itzip.feature.csQuiz.service;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizCreatedRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.csQuiz.controller.request.QuizQueryRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizCategoryDetailResponse;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.repository.quiz.QuizRepository;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.CheckAnswer;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.CreateQuiz;
import darkoverload.itzip.feature.csQuiz.service.sub.quiz.GivenPointToQuiz;
import darkoverload.itzip.feature.csQuiz.service.sub.quizCategory.FindQuizCategory;
import darkoverload.itzip.feature.csQuiz.service.sub.quizzes.FindQiuzQuery;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
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
    private final QuizRepository quizRepository;

    /**
     * 주어진 필터와 정렬 기준, 사용자 정보를 기반으로 퀴즈 목록을 조회하는 메서드
     *
     * @param quizQueryRequest 사용자가 보내는 퀴즈 쿼리를 담는 객체
     * @return 필터링되고 정렬된 퀴즈 목록
     */
    @Override
    public PagedModel<EntityModel<QuizDetailResponse>> findQuizzesByQuery(QuizQueryRequest quizQueryRequest) {
        return getFilteredAndSortedQuizzes.findQuizzesByQuery(quizQueryRequest);
    }

    /**
     * 주어진 ID에 해당하는 카테고리를 조회하는 메서드
     *
     * @param id 카테고리 ID
     * @return 카테고리 정보, 해당 ID에 대한 카테고리가 존재하지 않는 경우 null 반환
     */
    @Override
    public QuizCategory findCategoryById(Long id) {
        return getQuizCategory.findCategoryById(id);
    }

    /**
     * 모든 카테고리 조회하는 메서드
     *
     * @return 모든 카테고리 리스트
     */
    @Override
    public List<QuizCategoryDetailResponse> findAllCategory() {
        return getQuizCategory.findAllCategory();
    }

    /**
     * 퀴즈가 정답인지 알아보는 메서드
     * @param quizAnswerRequest 퀴즈 정답을 맞출때 사용하는 객체
     * @return 맞췄는짐 못맞췄는지 알려준다.
     */
    @Override
    public UserQuizStatus checkAnswer(QuizAnswerRequest quizAnswerRequest, CustomUserDetails customUserDetails){
        return checkAnswer.checkAnswer(quizAnswerRequest, customUserDetails);
    }

    /**
     * 사용자가 문제에게 점수를 줄때 사용하는 메서드
     * @param quizPointRequest 사용자한테서 받아오는 응답 코드
     * @return 문제를 주는 것을 성공했을 경우 변경된 문제의 점수를 알려준다.
     */
    @Override
    public Integer givenPointToQuiz(QuizPointRequest quizPointRequest, CustomUserDetails customUserDetails){
        return givenPointToQuiz.givenPointToQuiz(quizPointRequest, customUserDetails);
    }

    /**
     * 퀴즈를 생성할 때 사용하는 메서드
     * @param quizCreatedRequest 사용자한테서 받아오는 퀴즈 생성 코드 퀴즈 생성자 아이디도 들어있다.
     */
    @Override
    public void createQuiz(QuizCreatedRequest quizCreatedRequest, CustomUserDetails customUserDetails){
        createQuiz.createQuiz(quizCreatedRequest, customUserDetails);
    }
}