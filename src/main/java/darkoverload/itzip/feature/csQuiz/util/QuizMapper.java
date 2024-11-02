package darkoverload.itzip.feature.csQuiz.util;

import darkoverload.itzip.feature.csQuiz.controller.request.QuizCreatedRequest;
import darkoverload.itzip.feature.csQuiz.controller.response.QuizDetailResponse;
import darkoverload.itzip.feature.csQuiz.entity.QuizDocument;
import darkoverload.itzip.feature.csQuiz.entity.QuizUserSolvedMapping;
import darkoverload.itzip.feature.csQuiz.entity.UserQuizStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

//퀴즈 Dto와 Entity사이의 변환로직을 가지고 있는 class
@Component
public class QuizMapper {
    /**
     * quizEntity 한개를 quizDto로 변환하는 메서드
     * @param quizDocument 변환할 퀴즈 엔티티들 받아온다.
     * @param userSolved 변환할 퀴즈 엔티티를 찾고있는 사용자가 풀었는지 확인하는 flag값
     * @return 사용자가 찾는 quizDetailDto를 반환한다.
     */
    public QuizDetailResponse documentoResponse(QuizDocument quizDocument, Boolean userSolved) {
        if (quizDocument == null) {
            return null;
        }
        //문제의 정답률을 계산한다. 100분율로 정한다.
        double correctRate = ((double) quizDocument.getAcceptedUserCount() / quizDocument.getTriedUserCount()) * 100;

        return QuizDetailResponse.builder()
                .id(quizDocument.getId().toString())
                .questionText(quizDocument.getQuestionText())
                .difficulty(quizDocument.getDifficulty())
                .categoryId(quizDocument.getCategoryId())
                .category(quizDocument.getCategory())
                .acceptedUserCount(quizDocument.getAcceptedUserCount())
                .triedUserCount(quizDocument.getTriedUserCount())
                .correctRate(correctRate)
                .points(quizDocument.getPoints())
                .choices(quizDocument.getChoices())
                .createDate(quizDocument.getCreateDate())
                .modifyDate(quizDocument.getModifyDate())
                .build();
    }

    /**
     * quizEntity들을 quizDto들로 변환할때 사용하는 메서드
     * @param quizDocument 변환할 퀴즈 엔티티들 받아온다.
     * @param solvedProblemsSet 사용자가 푼 문제 목록이 담겨있는 hash 객체
     * @return 사용자가 찾는 quizDetailDto를 반환한다.
     */
    public QuizDetailResponse documentsToResponse(QuizDocument quizDocument, Set<QuizUserSolvedMapping> solvedProblemsSet) {
        if (quizDocument == null) {
            return null;
        }
        //문제의 정답률을 계산한다. 100분율로 정한다.
        double correctRate = ((double) quizDocument.getAcceptedUserCount() / quizDocument.getTriedUserCount()) * 100;

        // 사용자가 이 문제를 풀었는지, 그리고 맞췄는지 여부를 확인하여 UserQuizStatus를 설정한다.
        UserQuizStatus userQuizStatus = solvedProblemsSet.stream()
                .filter(solvedProblem -> solvedProblem.getProblemId().equals(quizDocument.getId().toString()))
                .map(QuizUserSolvedMapping::getUserQuizStatus)
                .findFirst()
                .orElse(UserQuizStatus.UNSOLVED);

        return QuizDetailResponse.builder()
                .id(quizDocument.getId().toString())
                .questionText(quizDocument.getQuestionText())
                .difficulty(quizDocument.getDifficulty())
                .categoryId(quizDocument.getCategoryId())
                .category(quizDocument.getCategory())
                .acceptedUserCount(quizDocument.getAcceptedUserCount())
                .triedUserCount(quizDocument.getTriedUserCount())
                .correctRate(correctRate)
                .points(quizDocument.getPoints())
                .userQuizStatus(userQuizStatus)
                .choices(quizDocument.getChoices())
                .createDate(quizDocument.getCreateDate())
                .modifyDate(quizDocument.getModifyDate())
                .build();
    }

    /**
     * quizEntity들을 quizDto들로 변환할때 사용하는 메서드 사용자가 풀지 않은 문제만 변환할때 사용한다.
     * @param quizDocument 변환할 퀴즈 엔티티들 받아온다.
     * @return 사용자가 찾는 quizDetailDto를 반환한다.
     */
    public QuizDetailResponse documentsToResponse(QuizDocument quizDocument) {
        if (quizDocument == null) {
            return null;
        }
        //문제의 정답률을 계산한다. 100분율로 정한다.
        double correctRate = ((double) quizDocument.getAcceptedUserCount() / quizDocument.getTriedUserCount()) * 100;

        return QuizDetailResponse.builder()
                .id(quizDocument.getId().toString())
                .questionText(quizDocument.getQuestionText())
                .difficulty(quizDocument.getDifficulty())
                .categoryId(quizDocument.getCategoryId())
                .category(quizDocument.getCategory())
                .acceptedUserCount(quizDocument.getAcceptedUserCount())
                .triedUserCount(quizDocument.getTriedUserCount())
                .correctRate(correctRate)
                .points(quizDocument.getPoints())
                .userQuizStatus(UserQuizStatus.UNSOLVED) //사용자가 풀지 않은 문제만 반환함으로 false로 모두 반환
                .choices(quizDocument.getChoices())
                .createDate(quizDocument.getCreateDate())
                .modifyDate(quizDocument.getModifyDate())
                .build();
    }

    /**
     * 사용자가 생성한 문제 DTO를 엔티티로 반환해줄 메서드
     * @param request 사용자가 생성한 문제 DTO
     * @param categoryName 사용자가 생성한 문제 카테고리 이름
     * @return QuizEntity를 반환한다.
     */
    public QuizDocument requestToDocument(QuizCreatedRequest request, String categoryName, Long userId) {
        if (request == null) {
            return null;
        }

        return QuizDocument.builder()
                .questionText(request.getQuestionText())
                .difficulty(request.getDifficulty())
                .categoryId(request.getCategoryId())
                .category(categoryName)
                .answer(request.getAnswer())
                .acceptedUserCount(0)
                .triedUserCount(0)
                .points(0)
                .createUserId(userId)
                .choices(request.getChoices())
                .build();
    }
}
