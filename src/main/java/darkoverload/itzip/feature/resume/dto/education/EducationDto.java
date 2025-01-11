package darkoverload.itzip.feature.resume.dto.education;

import darkoverload.itzip.feature.resume.domain.education.Education;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class EducationDto {

    // 학교명
    @Schema(description = "학교 이름", example = "Itzip University")
    private String schoolName;

    // 전공
    @Schema(description = "전공", example = "Software Engineering")
    private String major;

    // 입학일
    @Schema(description = "시작 날짜", example = "2016-03-01T00:00:00.000Z")
    private LocalDateTime startDate;

    // 졸업일
    @Schema(description = "종료 날짜", example = "2020-02-29T23:59:59.999Z")
    private LocalDateTime endDate;

    // 아이디
    @Schema(description = "학교 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
    private Long educationId;

    public EducationDto(String schoolName, String major, LocalDateTime startDate, LocalDateTime endDate) {
        this.schoolName = schoolName;
        this.major = major;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EducationDto(String schoolName, String major, LocalDateTime startDate, LocalDateTime endDate, Long educationId) {
        this.schoolName = schoolName;
        this.major = major;
        this.startDate = startDate;
        this.endDate = endDate;
        this.educationId = educationId;
    }

    public Education toModel(){
        return Education.builder()
                .educationId(this.educationId)
                .schoolName(this.schoolName)
                .major(this.major)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
