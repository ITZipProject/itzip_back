package darkoverload.itzip.feature.resume.domain.myskill;

import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.myskill.MySkillsDto;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import lombok.*;

@Setter
@Getter
@ToString
public class MySkill {
    // 이력서
    private Resume resume;

    // 이름
    private String name;

    // 아이디
    private Long mySkillId;

    @Builder
    public MySkill(Resume resume, String name, Long mySkillId) {
        this.resume = resume;
        this.name = name;
        this.mySkillId = mySkillId;
    }

    public static MySkill update(MySkillsDto mySkill, Resume resume){
        return MySkill.builder()
                .name(mySkill.getName())
                .mySkillId(mySkill.getMySkillId())
                .resume(resume)
                .build();
    }

    public MySkillEntity toEntity() {
        return MySkillEntity.builder()
                .resume(this.resume.toEntity())
                .name(this.name)
                .id(this.mySkillId)
                .build();
    }

}
