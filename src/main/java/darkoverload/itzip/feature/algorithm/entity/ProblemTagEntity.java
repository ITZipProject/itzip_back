package darkoverload.itzip.feature.algorithm.entity;

import darkoverload.itzip.feature.algorithm.domain.ProblemTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//문제의 Tag들을 저장하는 엔티티
@Entity
@Table(name = "problem_tags")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bojTagId; // 태그 ID

    @Column(name = "display_name")
    private String displayName; // 문제 유형 이름

    @Column(name = "display_name_sort")
    private String displayNameSort; // 문제 유형 별명

    public ProblemTag convertToDomain() {
        return ProblemTag.builder()
                .bojTagId(this.bojTagId)
                .displayName(this.displayName)
                .displayNameSort(this.displayNameSort)
                .build();
    }
}