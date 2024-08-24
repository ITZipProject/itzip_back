package darkoverload.itzip.feature.resume.dto.myskill;

import darkoverload.itzip.feature.resume.domain.myskill.CreateMySkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMySkillsDto {

    // 이름
    private String name;

    public CreateMySkill create() {
        return CreateMySkill.builder()
                .name(this.name)
                .build();
    }
}
