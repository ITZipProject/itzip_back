package darkoverload.itzip.feature.resume.dto.qualification;

import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "발급 기관", example = "Oracle")
    private String organization;

    // 취득일
    @Schema(description = "발급 날짜", example = "2020-08-15T00:00:00.000Z")
    private LocalDateTime qualificationDate;

    // 자격증명
    @Schema(description = "자격증 이름", example = "Oracle Certified Professional")
    private String name;

    // 아이디
    @Schema(description = "자격증 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
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
