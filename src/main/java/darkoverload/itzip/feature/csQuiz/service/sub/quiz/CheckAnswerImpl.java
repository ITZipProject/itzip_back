package darkoverload.itzip.feature.csQuiz.service.sub.quiz;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizAnswerRequest;
import darkoverload.itzip.feature.csQuiz.entity.QuizDocument;
import darkoverload.itzip.feature.csQuiz.entity.QuizUserSolvedMapping;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import darkoverload.itzip.feature.csQuiz.repository.quiz.QuizRepository;
import darkoverload.itzip.feature.csQuiz.repository.quizusersolvedmapping.QuizUserSolvedMappingRepository;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.feature.user.service.UserService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckAnswerImpl implements CheckAnswer {
    //퀴즈 정답 체크에 필요한 리파지토리
    private final QuizRepository quizRepository;
    private final QuizUserSolvedMappingRepository quizUserSolvedMappingRepository;

    //User객체를 받아올 userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public UserQuizStatus checkAnswer(QuizAnswerRequest quizAnswerRequest, CustomUserDetails customUserDetails) {
        // 사용자가 존재하는지 확인
        UserEntity userEntity = userService.findByEmail(customUserDetails.getEmail())
                .map(User::convertToEntity)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        // 제출된 퀴즈가 존재하는지 확인
        ObjectId objectQuizId = new ObjectId(quizAnswerRequest.getQuizId());
        QuizDocument quizDocument = quizRepository.findById(objectQuizId).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_QUIZ)
        );

        // 정답 여부 확인
        boolean isCorrect = quizDocument.getAnswer().equals(quizAnswerRequest.getAnswer());
        UserQuizStatus quizStatus = isCorrect ? UserQuizStatus.CORRECT : UserQuizStatus.INCORRECT;

        // 기존에 푼 문제인지 확인
        QuizUserSolvedMapping quizUserSolvedMapping = quizUserSolvedMappingRepository.findByUserAndProblemId(userEntity, quizAnswerRequest.getQuizId())
                .orElseGet(() -> QuizUserSolvedMapping.builder()
                        .user(userEntity)
                        .problemId(quizAnswerRequest.getQuizId())
                        .timeStamp(LocalDateTime.now())
                        .givenPoints(0) // 기본 점수 설정
                        .userQuizStatus(UserQuizStatus.UNSOLVED) // 기본 상태 설정
                        .build());

        //이미 문제를 맞췄을 경우 또 못 풀게 함
        if (quizUserSolvedMapping.getUserQuizStatus().equals(UserQuizStatus.CORRECT)) {
            throw new RestApiException(CommonExceptionCode.ALREADY_CORRECT);
        }

        quizUserSolvedMapping = quizUserSolvedMapping.updateTimeStampAndIsCorrect(LocalDateTime.now(), quizStatus);

        if (quizUserSolvedMapping.getId() != null)  {
            // 새로운 기록이라면 시도 횟수와 맞춘 사람 수 업데이트
            quizRepository.save(QuizDocument.builder()
                    .id(quizDocument.getId())
                    .questionText(quizDocument.getQuestionText())
                    .difficulty(quizDocument.getDifficulty())
                    .categoryId(quizDocument.getCategoryId())
                    .category(quizDocument.getCategory())
                    .answer(quizDocument.getAnswer())  // 기존 정답 유지
                    .acceptedUserCount(quizDocument.getAcceptedUserCount() + (isCorrect ? 1 : 0))
                    .triedUserCount(quizDocument.getTriedUserCount() + 1)
                    .points(quizDocument.getPoints())
                    .createUserId(quizDocument.getCreateUserId())
                    .choices(quizDocument.getChoices())
                    .build());
        }

        quizUserSolvedMappingRepository.save(quizUserSolvedMapping);

        return quizStatus;
    }
}