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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class UpdateResumeRequest {

    @ResumeConditional
    private List<AchievementDto> achievements = new ArrayList<>();

    @ResumeConditional
    private List<QualificationDto> qualifications = new ArrayList<>();

    @ResumeConditional
    @Schema(description = "기술 정보 값", example = "")
    private List<MySkillsDto> mySkills = new ArrayList<>();

    @ResumeConditional
    private List<ActivityDto> activities = new ArrayList<>(); // activity ==> save

    @ResumeConditional
    private List<CareerDto> careers = new ArrayList<>(); // ==> save

    @ResumeConditional
    private List<EducationDto> educations = new ArrayList<>(); //

    @ResumeConditional
    private List<LanguageDto> languages = new ArrayList<>();

    private ResumeDto resume;

    private Long userId;

    private Long resumeId;

    @Builder
    public UpdateResumeRequest(List<AchievementDto> achievements, List<QualificationDto> qualifications, List<MySkillsDto> mySkills, List<ActivityDto> activities, List<CareerDto> careers, List<EducationDto> educations, List<LanguageDto> languages, ResumeDto resume, Long userId, Long resumeId) {
        this.achievements = achievements;
        this.qualifications = qualifications;
        this.mySkills = mySkills;
        this.activities = activities;
        this.careers = careers;
        this.educations = educations;
        this.languages = languages;
        this.resume = resume;
        this.userId = userId;
        this.resumeId = resumeId;
    }

}
