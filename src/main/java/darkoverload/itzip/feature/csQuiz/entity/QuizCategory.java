package darkoverload.itzip.feature.csQuiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//퀴즈 카테고리를 받아오는 엔티티
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_categories")
public class QuizCategory {
    @Id
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    public QuizCategory update(String categoryName) {
        return QuizCategory.builder()
                .id(this.id)
                .categoryName(categoryName)
                .build();
    }
}