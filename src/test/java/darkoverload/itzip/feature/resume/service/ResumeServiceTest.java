package darkoverload.itzip.feature.resume.service;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.controller.response.CreateResumeResponse;
import darkoverload.itzip.feature.resume.controller.response.UpdateResumeResponse;
import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.domain.achievement.Achievements;
import darkoverload.itzip.feature.resume.domain.activity.Activities;
import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.career.Careers;
import darkoverload.itzip.feature.resume.domain.education.Education;
import darkoverload.itzip.feature.resume.domain.education.Educations;
import darkoverload.itzip.feature.resume.domain.language.Language;
import darkoverload.itzip.feature.resume.domain.language.Languages;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeDetails;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import darkoverload.itzip.feature.resume.dto.resume.ResumeDto;
import darkoverload.itzip.feature.resume.mock.TestResumeContainer;
import darkoverload.itzip.feature.user.entity.Authority;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class ResumeServiceTest {

    private TestResumeContainer testResumeContainer = new TestResumeContainer();
    private CustomUserDetails customUserDetails;
    @BeforeEach
    void init() {
        CreateResumeRequest createResumeRequest = CreateResumeRequest.builder()
                .qualifications(List.of(new QualificationDto("잇집기관", LocalDateTime.of(2024, 10, 1, 10, 30), "Java Programming")))
                .achievements(List.of(new AchievementDto("잇집자바상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.")))
                .activities(List.of(new ActivityDto("해커톤상", "무박 3일동안 해커톤 통해서 우수상 수상하였습니다.", LocalDateTime.of(2024, 10, 11, 9, 30), LocalDateTime.of(2024, 10, 14, 9, 30))))
                .educations(List.of(new EducationDto("잇집대", "소프트웨어학과", LocalDateTime.of(2018, 3, 10, 0, 0), LocalDateTime.of(2022, 3, 10, 0, 0))))
                .careers(List.of(new CareerDto("잇집회사", "사원", "IT팀", LocalDateTime.of(2022, 3, 10, 0, 0), LocalDateTime.of(2023, 3, 20, 0, 0))))
                .languages(List.of(new LanguageDto("토익", "850", LocalDateTime.of(2023, 10, 20, 0, 0))))
                .resume(new ResumeDto("itzip@gmail.com", "010-2987-8765", "잇집이력서", "안녕하세요 잇집 이력서입니다.", PublicOnOff.YES, List.of("https://naver.com"), null))
                .userId(1L)
                .build();



        customUserDetails = new CustomUserDetails("itzip@gmail.com", "aaaa1234@@", "itzip", Collections.singletonList(Authority.USER));

        testResumeContainer.resumeService.create(createResumeRequest, customUserDetails);
    }

    @Test
    void 저장_로직_테스트() {
        CreateResumeRequest createResumeRequest = CreateResumeRequest.builder()
                .qualifications(List.of(new QualificationDto("잇집", LocalDateTime.of(2023, 10, 1, 9, 30), "Python Programming")))
                .achievements(List.of(new AchievementDto("잇집파이썬상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.")))
                .activities(List.of(new ActivityDto("잇집프로젝트상", "잇집을 정말 열심해서 상을 드립니다.", LocalDateTime.of(2022, 9, 23, 9, 30), LocalDateTime.of(2022, 9, 23, 9, 30))))
                .educations(List.of(new EducationDto("잇집대", "소프트웨어학과", LocalDateTime.of(2019, 4, 20, 0, 0), LocalDateTime.of(2023, 3, 10, 0, 0))))
                .careers(List.of(new CareerDto("잇집회사", "팀장", "IT팀", LocalDateTime.of(2024, 3, 10, 0, 0), LocalDateTime.of(2024, 6, 20, 0, 0))))
                .resume(new ResumeDto("itzip@gmail.com", "010-2987-8765", "잇집이력서", "안녕하세요 잇집 이력서입니다.", PublicOnOff.YES, List.of("https://naver.com"), null))
                .build();

        CreateResumeResponse createResumeResponse = testResumeContainer.resumeService.create(createResumeRequest, customUserDetails);

        ResumeDetails expectedResumeDetails = saveExpectedResumeDetails();

        assertThat(createResumeResponse).isEqualTo(CreateResumeResponse.from(expectedResumeDetails));
    }

    private static ResumeDetails saveExpectedResumeDetails() {
        Resume expectedResume = new Resume("itzip@gmail.com", "010-2987-8765", "잇집이력서", "안녕하세요 잇집 이력서입니다.", PublicOnOff.YES, List.of("https://naver.com"), null, null, 2L, null);

        ResumeDetails expectedResumeDetails = new ResumeDetails(new Achievements(
                List.of(new Achievement(expectedResume, "잇집파이썬상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.", 2L)
                )),
                new Activities(
                        List.of(new Activity(expectedResume, "잇집프로젝트상", "잇집을 정말 열심해서 상을 드립니다.", LocalDateTime.of(2022, 9, 23, 9, 30), LocalDateTime.of(2022, 9, 23, 9, 30), 2L))
                ),
                new Careers(
                        List.of(new Career(expectedResume, "잇집회사", "팀장", "IT팀", LocalDateTime.of(2024, 3, 10, 0, 0), LocalDateTime.of(2024, 6, 20, 0, 0), 2L))
                ),
                new Educations(
                        List.of(new Education(expectedResume, "잇집대", "소프트웨어학과", LocalDateTime.of(2019, 4, 20, 0, 0), LocalDateTime.of(2023, 3, 10, 0, 0), 2L))
                ),
                new Languages(new ArrayList<>()),
                new MySkills(new ArrayList<>()),
                new Qualifications(
                        List.of(new Qualification(expectedResume, "잇집", LocalDateTime.of(2023, 10, 1, 9, 30), "Python Programming", 2L))
                ),
                expectedResume
        );
        return expectedResumeDetails;
    }

    @Test
    void 이력서_업데이트_테스트() {
        UpdateResumeRequest updateResumeRequest = UpdateResumeRequest.builder()
                .qualifications(List.of(new QualificationDto("잇집기관", LocalDateTime.of(2024, 10, 1, 10, 30), "Java Programming", 1L), new QualificationDto("원티드", LocalDateTime.of(2024, 11, 1, 10, 30), "TDD SOLID TEST")))
                .achievements(List.of(new AchievementDto("잇집자바상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.", 1L), new AchievementDto("잇집완주상", "잇집", LocalDateTime.of(2024, 11, 20, 11, 20), "완료해서 수여함.")))
                .activities(List.of(new ActivityDto("해커톤상", "무박 3일동안 해커톤 통해서 우수상 수상하였습니다.", LocalDateTime.of(2024, 10, 11, 9, 30), LocalDateTime.of(2024, 10, 14, 9, 30), 1L)))
                .educations(List.of(new EducationDto("잇집대", "소프트웨어학과", LocalDateTime.of(2018, 3, 10, 0, 0), LocalDateTime.of(2022, 3, 10, 0, 0), 1L)))
                .careers(List.of(new CareerDto("잇집회사", "사원", "IT팀", LocalDateTime.of(2022, 3, 10, 0, 0), LocalDateTime.of(2023, 3, 20, 0, 0), 1L)))
                .languages(List.of(new LanguageDto("토익", "850", LocalDateTime.of(2023, 10, 20, 0, 0), 1L)))
                .mySkills(new ArrayList<>())
                .resume(new ResumeDto("itzip@gmail.com", "010-2987-8765", "잇집이력서", "안녕하세요 잇집 이력서입니다.", PublicOnOff.YES, List.of("https://naver.com"), null))
                .resumeId(1L)
                .userId(1L)
                .build();

        ResumeDetails expectedResumeDetails = updateExcepectedResumeDetails();

        assertThat(testResumeContainer.resumeService.update(updateResumeRequest, customUserDetails)).isEqualTo(UpdateResumeResponse.from(expectedResumeDetails));
    }

    private static ResumeDetails updateExcepectedResumeDetails() {
        Resume expectedResume = new Resume("itzip@gmail.com", "010-2987-8765", "잇집이력서", "안녕하세요 잇집 이력서입니다.", PublicOnOff.YES, List.of("https://naver.com"), null, 1L, 1L, null);

        ResumeDetails expectedResumeDetails = new ResumeDetails(new Achievements(
                List.of(new Achievement(expectedResume, "잇집자바상", "잇집", LocalDateTime.of(2024, 7, 30, 11, 20), "자바 잘해서줍니다.", 1L), new Achievement(expectedResume, "잇집완주상", "잇집", LocalDateTime.of(2024, 11, 20, 11, 20), "완료해서 수여함.", 2L)
                )),
                new Activities(
                        List.of(new Activity(expectedResume, "해커톤상", "무박 3일동안 해커톤 통해서 우수상 수상하였습니다.", LocalDateTime.of(2024, 10, 11, 9, 30), LocalDateTime.of(2024, 10, 14, 9, 30), 1L))
                ),
                new Careers(
                        List.of(new Career(expectedResume, "잇집회사", "사원", "IT팀", LocalDateTime.of(2022, 3, 10, 0, 0), LocalDateTime.of(2023, 3, 20, 0, 0), 1L))
                ),
                new Educations(
                        List.of(new Education(expectedResume, "잇집대", "소프트웨어학과", LocalDateTime.of(2018, 3, 10, 0, 0), LocalDateTime.of(2022, 3, 10, 0, 0), 1L))
                ),
                new Languages(
                        List.of(new Language(expectedResume, "토익", "850", LocalDateTime.of(2023, 10, 20, 0, 0), 1L))
                ),
                new MySkills(new ArrayList<>()),
                new Qualifications(
                        List.of(new Qualification(expectedResume, "잇집기관", LocalDateTime.of(2024, 10, 1, 10, 30), "Java Programming", 1L), new Qualification(expectedResume, "원티드", LocalDateTime.of(2024, 11, 1, 10, 30), "TDD SOLID TEST", 2L))
                ),
                expectedResume
        );
        return expectedResumeDetails;
    }

}
