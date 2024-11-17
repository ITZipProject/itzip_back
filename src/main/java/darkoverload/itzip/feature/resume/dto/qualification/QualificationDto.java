package darkoverload.itzip.feature.resume.dto.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDto {

    // 발급기관
    private String organization;

    // 취득일
    private LocalDateTime qualificationDate;

    // 자격증명
    private String name;

    // 아이디
    private Long qualificationId;

    public QualificationDto(String organization, LocalDateTime qualificationDate, String name) {
        this.organization = organization;
        this.qualificationDate = qualificationDate;
        this.name = name;
    }

    public Qualification toModel() {
        return Qualification.builder()
                .qualificationId(this.qualificationId)
                .organization(this.organization)
                .qualificationDate(this.qualificationDate)
                .name(this.name)
                .build();
    }
}
