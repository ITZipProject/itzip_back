package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import lombok.Builder;

public record ResumeDetails(Achievements achievements, Activities activities, Careers careers, Educations educations,
                            Languages languages, MySkills mySkills, Qualifications qualifications, Resume resume) {

    @Builder
    public ResumeDetails {
    }


    public static ResumeDetails of(Achievements achievements, Activities activities, Careers careers, Educations educations, Languages languages, MySkills mySkills, Qualifications qualifications, Resume resume) {
        return ResumeDetails.builder()
                .achievements(achievements != null ? achievements : new Achievements())
                .activities(activities != null ? activities : new Activities())
                .careers(careers != null ? careers : new Careers())
                .educations(educations != null ? educations : new Educations())
                .languages(languages != null ? languages : new Languages())
                .mySkills(mySkills != null ? mySkills : new MySkills())
                .qualifications(qualifications != null ? qualifications : new Qualifications())
                .resume(resume != null ? resume : new Resume())
                .build();
    }
}
