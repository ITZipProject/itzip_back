package darkoverload.itzip.feature.resume.domain.myskill;

import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMySkill {
    // 이력서
    private ResumeEntity resume;

    // 이름
    private String name;

    public MySkillEntity toEntity() {
        return MySkillEntity.builder()
                .resume(resume)
                .name(name)
                .build();
    }
}
