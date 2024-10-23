package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResumeDetails {

    private final Achievements achievements;
    private final Activities activities;
    private final Careers careers;
    private final Educations educations;
    private final Languages languages;
    private final MySkills mySkills;
    private final Qualifications qualifications;
    private final Resume resume;

    @Builder
    public ResumeDetails(Achievements achievements, Activities activities, Careers careers, Educations educations, Languages languages, MySkills mySkills, Qualifications qualifications, Resume resume) {
        this.achievements = achievements;
        this.activities = activities;
        this.careers = careers;
        this.educations = educations;
        this.languages = languages;
        this.mySkills = mySkills;
        this.qualifications = qualifications;
        this.resume = resume;
    }


    public static ResumeDetails of(Achievements achievements, Activities activities, Careers careers, Educations educations, Languages languages, MySkills mySkills, Qualifications qualifications, Resume resume) {
        return ResumeDetails.builder()
                .achievements(achievements != null ? achievements : new Achievements())
                .activities(activities != null ? activities : new Activities())
                .careers(careers != null? careers : new Careers())
                .educations(educations != null ? educations : new Educations())
                .languages(languages != null ? languages : new Languages())
                .mySkills(mySkills != null ? mySkills : new MySkills())
                .qualifications(qualifications != null ? qualifications : new Qualifications())
                .resume(resume != null ? resume : new Resume())
                .build();
    }
}
