package darkoverload.itzip.feature.csQuiz.entity;

import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//퀴즈의 모든 정보를 담아두는 엔티티
@Getter
@Builder
@Document(collection = "quizzes")
public class QuizEntity extends AuditingFields{
    @Id
    //문제의 id값
    private String id;
    //문제
    private String questionText;
    //문제 난이도
    private Integer difficulty;
    //문제 카테고리 id
    private Long categoryId;
    //문제 유형 번호
    private String category;
    //문제 정답 번호
    private Integer answer;
    //문제를 맞춘 사람들 수
    private Integer acceptedUserCount;
    //문제를 시도한 사람들 수
    private Integer triedUserCount;
    //문제가 받은 평가 점수
    private Integer points;
    //문제를 만든 사용자의 Id
    private Long createUserId;
    //문제 선택지 리스트
    private List<QuizChoice> choices;
}