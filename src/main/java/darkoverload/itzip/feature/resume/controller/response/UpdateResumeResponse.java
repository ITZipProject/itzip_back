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
import darkoverload.itzip.feature.resume.domain.resume.ResumeDetails;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateResumeResponse(Achievements achievements, Activities activities, Careers careers,
                                   Educations educations, Languages languages, MySkills mySkills,
                                   Qualifications qualifications, Resume resume) {
    @Builder
    public UpdateResumeResponse {

    }

    public static UpdateResumeResponse from(ResumeDetails details) {
        return UpdateResumeResponse.builder()
                .achievements(details.achievements())
                .activities(details.activities())
                .careers(details.careers())
                .educations(details.educations())
                .languages(details.languages())
                .mySkills(details.mySkills())
                .qualifications(details.qualifications())
                .resume(details.resume())
                .build();
    }

}


