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
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class CreateResumeRequest {

    @Schema(description = "수상 리스트")
    @ResumeConditional
    private List<AchievementDto> achievements = new ArrayList<>();

    @Schema(description = "자격증 리스트")
    @ResumeConditional
    private List<QualificationDto> qualifications = new ArrayList<>();

    @Schema(description = "기술 스택 리스트")
    @ResumeConditional
    private List<MySkillsDto> mySkills = new ArrayList<>();

    @Schema(description = "대외 활동 리스트")
    @ResumeConditional
    private List<ActivityDto> activities = new ArrayList<>(); // activity ==> save

    @Schema(description = "경력 정보 리스트")
    @ResumeConditional
    private List<CareerDto> careers = new ArrayList<>(); // ==> save

    @Schema(description = "학력 정보 리스트")
    @ResumeConditional
    private List<EducationDto> educations = new ArrayList<>(); //

    @Schema(description = "언어 자격증 리스트")
    @ResumeConditional
    private List<LanguageDto> languages = new ArrayList<>();

    @Schema(description = "이력서 정보")
    private ResumeDto resume;

    @Schema(description = "유저 아이디", example = "1")
    private Long userId;


    @Builder
    public CreateResumeRequest(List<AchievementDto> achievements, List<QualificationDto> qualifications, List<MySkillsDto> mySkills, List<ActivityDto> activities, List<CareerDto> careers, List<EducationDto> educations, List<LanguageDto> languages, ResumeDto resume, Long userId) {
        this.achievements = achievements;
        this.qualifications = qualifications;
        this.mySkills = mySkills;
        this.activities = activities;
        this.careers = careers;
        this.educations = educations;
        this.languages = languages;
        this.resume = resume;
        this.userId = userId;
    }

}
