package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizCreatedRequest;
import darkoverload.itzip.feature.csQuiz.entity.QuizCategory;
import darkoverload.itzip.feature.csQuiz.repository.quiz.QuizRepository;
import darkoverload.itzip.feature.csQuiz.repository.quizcategory.QuizCategoryRepository;
import darkoverload.itzip.feature.csQuiz.util.QuizMapper;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateQuizImpl implements CreateQuiz {
    //퀴즈를 저장할때 필요한 리파지토리
    private final QuizRepository quizRepository;
    //퀴즈 카테고리를 받아올 리파지토리
    private final QuizCategoryRepository quizCategoryRepository;

    //request -> document 변환을 할 util
    private final QuizMapper quizMapper;

    //User객체를 받아올 userRepository
    private final UserRepository userRepository;

    /**
     * 퀴즈를 생성할 때 사용하는 메서드
     * @param quizCreatedRequest 사용자한테서 받아오는 퀴즈 생성 코드 퀴즈 생성자 아이디도 들어있다.
     */
    @Override
    @Transactional
    public void createQuiz(QuizCreatedRequest quizCreatedRequest) {
        //사용자가 존재하는지 확인
        userRepository.findById(quizCreatedRequest.getUserId()).ifPresentOrElse(
                user -> {},
                () -> { throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER); }
        );

        //카테고리가 존재하는지 확인
        QuizCategory quizCategory = quizCategoryRepository.findById(quizCreatedRequest.getCategoryId()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_CATEGORY)
        );

        //퀴즈 저장
        try{
            quizRepository.save(quizMapper.requestToDocument(quizCreatedRequest, quizCategory.getCategoryName()));
        } catch (MongoTransactionException e){
            throw new RestApiException(CommonExceptionCode.MONGO_DB_EXCEPTION);
        }
    }
}
