package darkoverload.itzip.feature.resume.dto.career;

import darkoverload.itzip.feature.resume.domain.career.Career;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto {

    // 회사명
    @Schema(description = "회사 이름", example = "TechCorp")
    private String companyName;

    // 직책
    @Schema(description = "직급", example = "Senior Software Engineer")
    private String careerPosition;

    // 부서
    @Schema(description = "부서", example = "Backend Development")
    private String department;

    // 입사일
    @Schema(description = "시작 날짜", example = "2020-01-01T00:00:00.000Z")
    private LocalDateTime startDate;

    // 퇴사일
    @Schema(description = "종료 날짜", example = "2023-11-27T05:51:54.050Z")
    private LocalDateTime endDate;

    // 아이디
    @Schema(description = "경력 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
    private Long careerId;

    public CareerDto(String companyName, String careerPosition, String department, LocalDateTime startDate, LocalDateTime endDate) {
        this.companyName = companyName;
        this.careerPosition = careerPosition;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Career toModel(){
        return Career.builder()
                .companyName(this.companyName)
                .careerPosition(this.careerPosition)
                .department(this.department)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .careerId(this.careerId)
                .build();
    }

}
