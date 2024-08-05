package darkoverload.itzip.csQuiz.util;

import darkoverload.itzip.csQuiz.dto.quiz.QuizCreatedDto;
import darkoverload.itzip.csQuiz.dto.quiz.QuizDetailDto;
import darkoverload.itzip.csQuiz.entity.QuizEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

//퀴즈 Dto와 Entity사이의 변환로직을 가지고 있는 class
@Component
public class QuizMapper {
    /**
     * quizEntity 한개를 quizDto로 변환하는 메서드
     * @param quizEntity 변환할 퀴즈 엔티티들 받아온다.
     * @param userSolved 변환할 퀴즈 엔티티를 찾고있는 사용자가 풀었는지 확인하는 flag값
     * @return 사용자가 찾는 quizDetailDto를 반환한다.
     */
    public QuizDetailDto entitytoDto(QuizEntity quizEntity, Boolean userSolved) {
        if (quizEntity == null) {
            return null;
        }
        //문제의 정답률을 계산한다. 100분율로 정한다.
        double correctRate = ((double) quizEntity.getAcceptedUserCount() / quizEntity.getTriedUserCount()) * 100;

        return QuizDetailDto.builder()
                .id(quizEntity.getId())
                .questionText(quizEntity.getQuestionText())
                .difficulty(quizEntity.getDifficulty())
                .categoryId(quizEntity.getCategoryId())
                .category(quizEntity.getCategory())
                .acceptedUserCount(quizEntity.getAcceptedUserCount())
                .triedUserCount(quizEntity.getTriedUserCount())
                .correctRate(correctRate)
                .points(quizEntity.getPoints())
                .userSolved(userSolved)
                .choices(quizEntity.getChoices())
                .build();
    }

    /**
     * quizEntity들을 quizDto들로 변환할때 사용하는 메서드
     * @param quizEntity 변환할 퀴즈 엔티티들 받아온다.
     * @param userSolvedIds 사용자가 푼 문제 목록이 담겨있는 메서드
     * @return 사용자가 찾는 quizDetailDto를 반환한다.
     */
    public QuizDetailDto entitiestoDto(QuizEntity quizEntity, Set<String> userSolvedIds) {
        if (quizEntity == null) {
            return null;
        }
        //문제의 정답률을 계산한다. 100분율로 정한다.
        double correctRate = ((double) quizEntity.getAcceptedUserCount() / quizEntity.getTriedUserCount()) * 100;

        return QuizDetailDto.builder()
                .id(quizEntity.getId())
                .questionText(quizEntity.getQuestionText())
                .difficulty(quizEntity.getDifficulty())
                .categoryId(quizEntity.getCategoryId())
                .category(quizEntity.getCategory())
                .acceptedUserCount(quizEntity.getAcceptedUserCount())
                .triedUserCount(quizEntity.getTriedUserCount())
                .correctRate(correctRate)
                .points(quizEntity.getPoints())
                .userSolved(userSolvedIds.contains(quizEntity.getId()))
                .choices(quizEntity.getChoices())
                .build();
    }

    /**
     * quizEntity들을 quizDto들로 변환할때 사용하는 메서드 사용자가 풀지 않은 문제만 변환할때 사용한다.
     * @param quizEntity 변환할 퀴즈 엔티티들 받아온다.
     * @return 사용자가 찾는 quizDetailDto를 반환한다.
     */
    public QuizDetailDto entitiestoDto(QuizEntity quizEntity) {
        if (quizEntity == null) {
            return null;
        }
        //문제의 정답률을 계산한다. 100분율로 정한다.
        double correctRate = ((double) quizEntity.getAcceptedUserCount() / quizEntity.getTriedUserCount()) * 100;

        return QuizDetailDto.builder()
                .id(quizEntity.getId())
                .questionText(quizEntity.getQuestionText())
                .difficulty(quizEntity.getDifficulty())
                .categoryId(quizEntity.getCategoryId())
                .category(quizEntity.getCategory())
                .acceptedUserCount(quizEntity.getAcceptedUserCount())
                .triedUserCount(quizEntity.getTriedUserCount())
                .correctRate(correctRate)
                .points(quizEntity.getPoints())
                .choices(quizEntity.getChoices())
                .build();
    }

    /**
     * 사용자가 생성한 문제 DTO를 엔티티로 반환해줄 메서드
     * @param dto 사용자가 생성한 문제 DTO
     * @param categoryName 사용자가 생성한 문제 카테고리 이름
     * @param createdUserId 문제를 생성한 사용자 id
     * @return QuizEntity를 반환한다.
     */
    public QuizEntity createdtoEntity(QuizCreatedDto dto, String categoryName, Long createdUserId) {
        if (dto == null) {
            return null;
        }

        return QuizEntity.builder()
                .questionText(dto.getQuestionText())
                .difficulty(dto.getDifficulty())
                .categoryId(dto.getCategoryId())
                .category(categoryName)
                .answer(dto.getAnswer())
                .acceptedUserCount(0)
                .triedUserCount(0)
                .points(0)
                .createUserId(createdUserId)
                .choices(dto.getChoices())
                .build();
    }
}
