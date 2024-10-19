package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizPointRequest;
import darkoverload.itzip.feature.csQuiz.entity.QuizDocument;
import darkoverload.itzip.feature.csQuiz.entity.QuizUserSolvedMapping;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.repository.quiz.QuizRepository;
import darkoverload.itzip.feature.csQuiz.repository.quizusersolvedmapping.QuizUserSolvedMappingRepository;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GivenPointToQuizImpl implements GivenPointToQuiz {
    //퀴즈 점수 추가에 필요한 리파지토리
    private final QuizRepository quizRepository;
    private final QuizUserSolvedMappingRepository quizUserSolvedMappingRepository;

    //User 객체를 받아올 userRepository;
    private final UserService userService;

    /**
     * 사용자가 문제에게 점수를 줄때 사용하는 메서드
     * @param quizPointRequest 사용자한테서 받아오는 응답 코드
     * @return 문제를 주는 것을 성공했을 경우 변경된 문제의 점수를 알려준다.
     */
    @Transactional
    public Integer givenPointToQuiz(QuizPointRequest quizPointRequest, CustomUserDetails customUserDetails) {
        // 사용자가 존재하는지 확인
        UserEntity userEntity = userService.findByEmail(customUserDetails.getEmail())
                .map(User::convertToEntity)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        // 퀴즈가 존재하는지 확인
        ObjectId objectQuizId = new ObjectId(quizPointRequest.getQuizId());
        QuizDocument quizDocument = quizRepository.findById(objectQuizId).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_QUIZ)
        );

        //문제를 풀었는지 확인
        QuizUserSolvedMapping quizUserSolvedMapping = quizUserSolvedMappingRepository.findByUserAndProblemId(
                userEntity , quizPointRequest.getQuizId()).orElseThrow(
                        ()-> new RestApiException(CommonExceptionCode.NOT_FOUND_QUIZ)
        );

        //문제 정답을 맞췄는지 확인
        if (!quizUserSolvedMapping.getIsCorrect().equals(UserQuizStatus.CORRECT)) {
            throw new RestApiException(CommonExceptionCode.ANSWER_NOT_CORRECT);
        }

        //이미 정답에 점수를 줬는지 확인
        if (!quizUserSolvedMapping.getGivenPoints().equals(0)) {
            throw new RestApiException(CommonExceptionCode.POINT_ALREADY_GIVEN);
        }

        //포인트가 업데이트 된 퀴즈 저장
        quizRepository.save(quizDocument.sumPoints(quizPointRequest.getPoints()));

        //매핑 테이블의 포인트 업데이트
        quizUserSolvedMappingRepository.save(quizUserSolvedMapping.updateGivenPoints(quizPointRequest.getPoints()));

        //새로 변경된 문제의 점수 return
        return quizDocument.getPoints() + quizUserSolvedMapping.getGivenPoints();
    }
}