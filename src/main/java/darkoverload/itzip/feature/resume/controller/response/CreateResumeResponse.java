package darkoverload.itzip.feature.resume.controller.response;

import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeDetails;
import lombok.Builder;

public record CreateResumeResponse(Achievements achievements, Activities activities, Careers careers,
                                   Educations educations, Languages languages, MySkills mySkills,
                                   Qualifications qualifications, Resume resume) {
    @Builder
    public CreateResumeResponse {
    }

    public static CreateResumeResponse from(ResumeDetails details) {
        return CreateResumeResponse.builder()
                .achievements(details.getAchievements())
                .activities(details.getActivities())
                .careers(details.getCareers())
                .educations(details.getEducations())
                .languages(details.getLanguages())
                .mySkills(details.getMySkills())
                .qualifications(details.getQualifications())
                .resume(details.getResume())
                .build();
    }
}
