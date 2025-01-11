package darkoverload.itzip.feature.resume.dto.myskill;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MySkillsDto {

    // 이름
    @Schema(description = "기술 이름", example = "Java")
    private String name;

    // 아이디
    @Schema(description = "기술 아이디 값", example = "이력서 생성 시 제외 업데이트 시 추가해서 사용")
    private Long mySkillId;

    public MySkill toModel() {
        return MySkill.builder()
                .name(this.name)
                .mySkillId(this.mySkillId)
                .build();
    }
}
