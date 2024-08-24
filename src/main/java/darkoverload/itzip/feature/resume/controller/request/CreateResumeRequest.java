package darkoverload.itzip.feature.resume.controller.request;

import darkoverload.itzip.feature.resume.dto.achievement.CreateAchievementDto;
import darkoverload.itzip.feature.resume.dto.activity.CreateActivityDto;
import darkoverload.itzip.feature.resume.dto.career.CreateCareerDto;
import darkoverload.itzip.feature.resume.dto.education.CreateEducationDto;
import darkoverload.itzip.feature.resume.dto.language.CreateLanguageDto;
import darkoverload.itzip.feature.resume.dto.myskill.CreateMySkillsDto;
import darkoverload.itzip.feature.resume.dto.qualification.CreateQualificationDto;
import darkoverload.itzip.feature.resume.dto.resume.CreateResumeDto;
import darkoverload.itzip.feature.resume.util.ResumeConditional;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class CreateResumeRequest {

    @ResumeConditional
    private List<CreateAchievementDto> achievements = new ArrayList<>();//=> null  // 애 없을 수도 있음.

    @ResumeConditional
    private final List<CreateQualificationDto> qualifications = new ArrayList<>();

    @ResumeConditional
    private final List<CreateMySkillsDto> mySkills= new ArrayList<>();

    @ResumeConditional
    private final List<CreateActivityDto> activities = new ArrayList<>(); // activity ==> save

    @ResumeConditional
    private final List<CreateCareerDto> careers = new ArrayList<>(); // ==> save

    @ResumeConditional
    private final List<CreateEducationDto> educations = new ArrayList<>(); //

    @ResumeConditional
    private final List<CreateLanguageDto> languages = new ArrayList<>();

    private CreateResumeDto resume;

    private Long userId;
}
