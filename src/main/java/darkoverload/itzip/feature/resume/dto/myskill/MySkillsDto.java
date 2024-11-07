package darkoverload.itzip.feature.resume.dto.myskill;

import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
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
    private String name;

    // 아이디
    private Long mySkillId;

    public MySkill toModel() {
        return MySkill.builder()
                .name(this.name)
                .mySkillId(this.mySkillId)
                .build();
    }
}
