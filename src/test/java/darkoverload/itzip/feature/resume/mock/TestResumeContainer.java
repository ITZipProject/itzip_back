/*
package darkoverload.itzip.feature.resume.mock;

import darkoverload.itzip.feature.resume.service.resume.ResumeService;
import darkoverload.itzip.feature.resume.service.resume.ResumeServiceImpl;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationCommandRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeRepository;
import lombok.Builder;

public class TestResumeContainer {
    public final ResumeRepository resumeRepository;
    public final QualificationCommandRepository qualificationCommandRepository;
    public final QualificationReadRepository qualificationReadRepository;
    public final AchievementCommandRepository achievementCommandRepository;
    public final AchievementReadRepository achievementReadRepository;
    public final ActivityCommandRepository activityCommandRepository;
    public final ActivityReadRepository activityReadRepository;
    public final EducationCommandRepository educationCommandRepository;
    public final EducationReadRepository educationReadRepository;
    public final CareerCommandRepository careerCommandRepository;
    public final CareerReadRepository careerReadRepository;
    public final MySkillCommandRepository mySkillCommandRepository;
    public final MySkillReadRepository mySkillReadRepository;
    public final LanguageCommandRepository languageCommandRepository;
    public final LanguageReadRepository languageReadRepository;
    public final ResumeService resumeService;

    @Builder
    public TestResumeContainer() {
        this.resumeRepository = new FakeResumeRepository();
        this.qualificationCommandRepository = new FakeQualificationCommandRepository();
        this.qualificationReadRepository = new FakeQualificationCommandRepository();
        this.achievementCommandRepository = new FakeAchievementCommandRepository();
        this.achievementReadRepository = new FakeAchievementCommandRepository();
        this.activityCommandRepository = new FakeActivityCommandRepository();
        this.activityReadRepository = new FakeActivityCommandRepository();
        this.educationCommandRepository = new FakeEducationCommandRepository();
        this.educationReadRepository = new FakeEducationCommandRepository();
        this.careerCommandRepository = new FakeCareerCommandRepository();
        this.careerReadRepository = new FakeCareerCommandRepository();
        this.mySkillCommandRepository = new FakeMySkillCommandRepository();
        this.mySkillReadRepository = new FakeMySkillCommandRepository();
        this.languageReadRepository = new FakeLanguageCommandRepository();
        this.languageCommandRepository = new FakeLanguageCommandRepository();

        this.resumeService = ResumeServiceImpl.builder()
                .resumeRepository(resumeRepository)
                .achievementReadRepository(achievementReadRepository)
                .achievementCommandRepository(achievementCommandRepository)
                .activityReadRepository(activityReadRepository)
                .activityCommandRepository(activityCommandRepository)
                .careerReadRepository(careerReadRepository)
                .careerCommandRepository(careerCommandRepository)
                .educationReadRepository(educationReadRepository)
                .educationCommandRepository(educationCommandRepository)
                .languageReadRepository(languageReadRepository)
                .languageCommandRepository(languageCommandRepository)
                .mySkillReadRepository(mySkillReadRepository)
                .mySkillCommandRepository(mySkillCommandRepository)
                .qualificationReadRepository(qualificationReadRepository)
                .qualificationCommandRepository(qualificationCommandRepository)
                .build();
    }


}
*/
