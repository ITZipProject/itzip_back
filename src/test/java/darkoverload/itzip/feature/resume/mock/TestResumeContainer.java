package darkoverload.itzip.feature.resume.mock;

import darkoverload.itzip.feature.resume.service.resume.ResumeService;
import darkoverload.itzip.feature.resume.service.resume.ResumeServiceImpl;
import darkoverload.itzip.feature.resume.service.resume.port.*;
import lombok.Builder;

public class TestResumeContainer {
    public final ResumeRepository resumeRepository;
    public final QualificationRepository qualificationRepository;
    public final AchievementRepository achievementRepository;
    public final ActivityRepository activityRepository;
    public final EducationRepository educationRepository;
    public final CareerRepository careerRepository;
    public final MySkillRepository mySkillRepository;
    public final LanguageRepository languageRepository;
    public final ResumeService resumeService;

    @Builder
    public TestResumeContainer() {
        this.resumeRepository = new FakeResumeRepository();
        this.qualificationRepository = new FakeQualificationRepository();
        this.achievementRepository = new FakeAchievementRepository();
        this.activityRepository = new FakeActivityRepository();
        this.educationRepository = new FakeEducationRepository();
        this.careerRepository = new FakeCareerRepository();
        this.mySkillRepository = new FakeMySkillRepository();
        this.languageRepository = new FakeLanguageRepository();

        this.resumeService = ResumeServiceImpl.builder()
                .resumeRepository(resumeRepository)
                .achievementRepository(achievementRepository)
                .activityRepository(activityRepository)
                .careerRepository(careerRepository)
                .educationRepository(educationRepository)
                .languageRepository(languageRepository)
                .mySkillRepository(mySkillRepository)
                .qualificationRepository(qualificationRepository)
                .build();
    }


}
