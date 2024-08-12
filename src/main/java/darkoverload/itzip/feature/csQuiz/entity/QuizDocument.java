package darkoverload.itzip.feature.csQuiz.entity;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

//퀴즈의 모든 정보를 담아두는 엔티티
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "quizzes")
public class QuizDocument {
    //문제의 id값
    @Id
    @Field("_id")
    private ObjectId id;

    //문제
    @Field("questionText")
    private String questionText;

    //문제 난이도
    @Field("difficulty")
    private Integer difficulty;

    //문제 카테고리 id
    @Field("categoryId")
    private Long categoryId;

    //문제 유형 번호
    @Field("category")
    private String category;

    //문제 정답 번호
    @Field("answer")
    private Integer answer;

    //문제를 맞춘 사람들 수
    @Field("acceptedUserCount")
    private Integer acceptedUserCount;

    //문제를 시도한 사람들 수
    @Field("triedUserCount")
    private Integer triedUserCount;

    //문제가 받은 평가 점수
    @Field("points")
    private Integer points;

    //문제를 만든 사용자의 Id
    @Field("createUserId")
    private Long createUserId;

    //문제 선택지 리스트
    private List<QuizChoice> choices;
}