<<<<<<< HEAD
package darkoverload.itzip.feature.resume.service;


import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import darkoverload.itzip.feature.resume.mock.*;
import darkoverload.itzip.feature.resume.service.resume.ResumeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class ResumeServiceTest {

    private ResumeServiceImpl resumeService;


    @BeforeEach
    void init() {
        FakeActivityRepository fakeActivityRepository = new FakeActivityRepository();
        FakeAchievementRepository fakeAchievementRepository = new FakeAchievementRepository();
        FakeCareerRepository fakeCareerRepository = new FakeCareerRepository();
        FakeEducationRepository fakeEducationRepository = new FakeEducationRepository();
        FakeLanguageRepository fakeLanguageRepository = new FakeLanguageRepository();
        FakeMySkillRepository fakeMySkillRepository = new FakeMySkillRepository();
        FakeQualificationRepository fakeQualificationRepository = new FakeQualificationRepository();
        FakeResumeRepository fakeResumeRepository = new FakeResumeRepository();

        this.resumeService = ResumeServiceImpl.builder()
                .resumeRepository(fakeResumeRepository)
                .activityRepository(fakeActivityRepository)
                .achievementRepository(fakeAchievementRepository)
                .careerRepository(fakeCareerRepository)
                .educationRepository(fakeEducationRepository)
                .languageRepository(fakeLanguageRepository)
                .mySkillRepository(fakeMySkillRepository)
                .qualificationRepository(fakeQualificationRepository)
                .build();


        CreateResumeRequest createResumeRequest = CreateResumeRequest.builder()
                .qualifications(List.of(new QualificationDto("잇집기관", LocalDateTime.of(2024, 10, 1, 10, 30), "Java Programming", 95, "A")))
                .achievements(List.of(new AchievementDto("잇집자바상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.")))
                .activities(List.of(new ActivityDto("해커톤상", "무박 3일동안 해커톤 통해서 우수상 수상하였습니다.", LocalDateTime.of(2024, 10, 11, 9, 30), LocalDateTime.of(2024, 10, 14, 9, 30))))
                .educations(List.of(new EducationDto("잇집대", "소프트웨어학과", LocalDateTime.of(2018, 3, 10, 0, 0), LocalDateTime.of(2022, 3, 10, 0, 0))))
                .careers(List.of(new CareerDto("잇집회사", "사원", "IT팀", LocalDateTime.of(2022, 3, 10, 0,0), LocalDateTime.of(2023, 3, 20,0,0))))
                .languages(List.of(new LanguageDto("토익", null, 850, LocalDateTime.of(2023, 10, 20, 0,0))))
                .build();

        resumeService.create(createResumeRequest);
    }

    @Test
    void 저장_로직_테스트(){
        CreateResumeRequest createResumeRequest = CreateResumeRequest.builder()
                .qualifications(List.of(new QualificationDto("잇집", LocalDateTime.of(2023, 10, 1, 9, 30), "Python Programming", 85, "B")))
                .achievements(List.of(new AchievementDto("잇집파이썬상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.")))
                .activities(List.of(new ActivityDto("잇집프로젝트상", "잇집을 정말 열심해서 상을 드립니다.", LocalDateTime.of(2022, 9, 23, 9, 30), LocalDateTime.of(2022, 9, 23, 9, 30))))
                .educations(List.of(new EducationDto("잇집대", "소프트웨어학과", LocalDateTime.of(2019, 4, 20, 0, 0), LocalDateTime.of(2023, 3, 10, 0, 0))))
                .careers(List.of(new CareerDto("잇집회사", "팀장", "IT팀", LocalDateTime.of(2024, 3, 10, 0,0), LocalDateTime.of(2024, 6, 20,0,0))))
                .build();

        resumeService.create(createResumeRequest);
    }
}
