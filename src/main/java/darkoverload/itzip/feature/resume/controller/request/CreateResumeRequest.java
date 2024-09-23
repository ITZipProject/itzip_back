package darkoverload.itzip.feature.resume.controller.request;

import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import darkoverload.itzip.feature.resume.dto.myskill.MySkillsDto;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import darkoverload.itzip.feature.resume.dto.resume.ResumeDto;
import darkoverload.itzip.feature.resume.util.ResumeConditional;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class CreateResumeRequest {

    @ResumeConditional
    private List<AchievementDto> achievements = new ArrayList<>();

    @ResumeConditional
    private final List<QualificationDto> qualifications = new ArrayList<>();

    @ResumeConditional
    private final List<MySkillsDto> mySkills= new ArrayList<>();

    @ResumeConditional
    private final List<ActivityDto> activities = new ArrayList<>(); // activity ==> save

    @ResumeConditional
    private final List<CareerDto> careers = new ArrayList<>(); // ==> save

    @ResumeConditional
    private final List<EducationDto> educations = new ArrayList<>(); //

    @ResumeConditional
    private final List<LanguageDto> languages = new ArrayList<>();

    private ResumeDto resume;

    private Long userId;
}
