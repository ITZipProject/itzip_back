package darkoverload.itzip.csQuiz.entity;

import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Builder
@Document(collection = "quizzes")
public class Quiz extends AuditingFields{
    @Id
    private String id;
    private String questionText;
    private Integer difficulty;
    private Long category;
    private Integer answer;
    private Integer acceptedUserCount;
    private Integer triedUserCount;
    private Integer points;
    private String createUserId;
    private List<QuizChoice> choices;
}