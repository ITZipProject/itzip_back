package darkoverload.itzip.feature.resume.mock;

import darkoverload.itzip.feature.resume.service.resume.ResumeService;
import darkoverload.itzip.feature.resume.service.resume.ResumeServiceImpl;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.activity.ActivityRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.career.CareerRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.education.EducationRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.language.LanguageRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.myskill.MySkillRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationReadRepository;
import darkoverload.itzip.feature.resume.service.resume.port.qualification.QualificationRepository;
import darkoverload.itzip.feature.resume.service.resume.port.resume.ResumeRepository;
import lombok.Builder;

public class TestResumeContainer {
    public final ResumeRepository resumeRepository;
    public final QualificationRepository qualificationRepository;
    public final QualificationReadRepository qualificationReadRepository;
    public final AchievementRepository achievementRepository;
    public final AchievementReadRepository achievementReadRepository;
    public final ActivityRepository activityRepository;
    public final ActivityReadRepository activityReadRepository;
    public final EducationRepository educationRepository;
    public final EducationReadRepository educationReadRepository;
    public final CareerRepository careerRepository;
    public final CareerReadRepository careerReadRepository;
    public final MySkillRepository mySkillRepository;
    public final MySkillReadRepository mySkillReadRepository;
    public final LanguageRepository languageRepository;
    public final LanguageReadRepository languageReadRepository;
    public final ResumeService resumeService;

    @Builder
    public TestResumeContainer() {
        this.resumeRepository = new FakeResumeRepository();
        this.qualificationRepository = new FakeQualificationRepository();
        this.qualificationReadRepository = new FakeQualificationRepository();
        this.achievementRepository = new FakeAchievementRepository();
        this.achievementReadRepository = new FakeAchievementRepository();
        this.activityRepository = new FakeActivityRepository();
        this.activityReadRepository = new FakeActivityRepository();
        this.educationRepository = new FakeEducationRepository();
        this.educationReadRepository = new FakeEducationRepository();
        this.careerRepository = new FakeCareerRepository();
        this.careerReadRepository = new FakeCareerRepository();
        this.mySkillRepository = new FakeMySkillRepository();
        this.mySkillReadRepository = new FakeMySkillRepository();
        this.languageReadRepository = new FakeLanguageRepository();
        this.languageRepository = new FakeLanguageRepository();

        this.resumeService = ResumeServiceImpl.builder()
                .resumeRepository(resumeRepository)
                .achievementReadRepository(achievementReadRepository)
                .achievementRepository(achievementRepository)
                .activityReadRepository(activityReadRepository)
                .activityRepository(activityRepository)
                .careerReadRepository(careerReadRepository)
                .careerRepository(careerRepository)
                .educationReadRepository(educationReadRepository)
                .educationRepository(educationRepository)
                .languageReadRepository(languageReadRepository)
                .languageRepository(languageRepository)
                .mySkillReadRepository(mySkillReadRepository)
                .mySkillRepository(mySkillRepository)
                .qualificationReadRepository(qualificationReadRepository)
                .qualificationRepository(qualificationRepository)
                .build();
    }


}
