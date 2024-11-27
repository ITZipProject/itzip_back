package darkoverload.itzip.feature.resume.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetResumeDetailsResponse(Resume resume, Achievements achievements, Activities activities, Careers careers, Educations educations, Languages languages, MySkills mySkills, Qualifications qualifications) {

    @Builder
    public GetResumeDetailsResponse {

    }

    public static GetResumeDetailsResponse of(Resume resume, Achievements achievements, Activities activities, Careers careers, Educations educations, Languages languages, MySkills mySkills, Qualifications qualifications) {
        return GetResumeDetailsResponse.builder()
                .resume(resume)
                .achievements(achievements)
                .activities(activities)
                .careers(careers)
                .educations(educations)
                .languages(languages)
                .mySkills(mySkills)
                .qualifications(qualifications)
                .build();
    }

}
