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
public class CreateResumeRequest {

    @Schema(description = "수상 리스트", example = """
            {
              "achievements": [
                {
                  "name": "Hackathon Winner",
                  "organization": "Global Hackathon 2024",
                  "achievementDate": "2024-11-27T05:51:54.050Z",
                  "content": "Developed an award-winning AI-powered application."
                }
              ]
            }
            """)
    @ResumeConditional
    private List<AchievementDto> achievements = new ArrayList<>();

    @Schema(description = "자격증 리스트", example = """
            {
              "qualifications": [
                {
                  "organization": "Oracle",
                  "qualificationDate": "2020-08-15T00:00:00.000Z",
                  "name": "Oracle Certified Professional",
                  "score": 95
                }
              ]
            }
            """)
    @ResumeConditional
    private List<QualificationDto> qualifications = new ArrayList<>();

    @Schema(description = "기술 스택 리스트", example = """
            {
              "mySkills": [
                {
                  "name": "Java"
                }
              ]
            }
            """)
    @ResumeConditional
    private List<MySkillsDto> mySkills = new ArrayList<>();

    @Schema(description = "대외 활동 리스트", example = """
            {
              "activities": [
                {
                  "name": "Open Source Contribution",
                  "content": "Contributed to Apache Kafka project.",
                  "startDate": "2023-01-01T00:00:00.000Z",
                  "endDate": "2023-12-31T23:59:59.999Z"
                }
              ]
            }
            """)
    @ResumeConditional
    private List<ActivityDto> activities = new ArrayList<>(); // activity ==> save

    @Schema(description = "경력 정보 리스트", example = """
            {
              "careers": [
                {
                  "companyName": "TechCorp",
                  "careerPosition": "Senior Software Engineer",
                  "department": "Backend Development",
                  "startDate": "2020-01-01T00:00:00.000Z",
                  "endDate": "2023-11-27T05:51:54.050Z"
                }
              ]
            }
            """)
    @ResumeConditional
    private List<CareerDto> careers = new ArrayList<>(); // ==> save

    @Schema(description = "학력 정보 리스트", example = """
            {
              "educations": [
                {
                  "schoolName": "Itzip University",
                  "major": "Software Engineering",
                  "startDate": "2016-03-01T00:00:00.000Z",
                  "endDate": "2020-02-29T23:59:59.999Z"
                }
              ]
            }
            """)
    @ResumeConditional
    private List<EducationDto> educations = new ArrayList<>(); //

    @Schema(description = "언어 자격증 리스트", example = """
            {
              "languages": [
               {
                  "name": "English",
                  "score": "TOEIC 900",
                  "acquisitionDate": "2022-10-01T00:00:00.000Z"
                }
              ]
            }
            """)
    @ResumeConditional
    private List<LanguageDto> languages = new ArrayList<>();

    @Schema(description = "이력서 정보", example = """
            {
              "email": "user@example.com",
              "phone": "+821012345678",
              "subject": "Software Engineer Resume",
              "introduction": "Experienced software engineer specializing in backend development.",
              "publicOnOff": "YES",
              "links": [
                "https://github.com/user",
                "https://linkedin.com/in/user"
              ],
              "imageUrl": "https://example.com/profile.jpg",
              "userId": 123
            }
            """)
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
