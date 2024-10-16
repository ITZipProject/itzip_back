package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
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
import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.service.resume.port.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    private final EducationRepository educationRepository;

    private final LanguageRepository languageRepository;

    private final QualificationRepository qualificationRepository;

    private final MySkillRepository mySkillRepository;

    private final CareerRepository careerRepository;

    private final AchievementRepository achievementRepository;

    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public void create(CreateResumeRequest request) {

        //"career", "skill", "language", "qualification", "education", "activity", "achievement"
        Resume resume = Resume.create(request.getResume(), request.getUserId());

        // 이력서 저장
        // 이력서와 관련된 내용들 저장
        create(request, resumeRepository.save(resume));

    }

    @Transactional
    @Override
    public void update(UpdateResumeRequest request) {
        Resume updateResume = Resume.update(request.getResume(), request.getResumeId(), request.getUserId());

        update(request, resumeRepository.update(updateResume));
    }

    /**
     * 제공된 요청 객체의 데이터를 사용하여 주어진 이력서를 업데이트합니다.
     * 이 메서드는 경력, 성과, 활동, 언어, 학력, 기술, 자격증과 같은 이력서의 여러 섹션을 처리하고,
     * 각 섹션에 해당하는 저장소에 업데이트를 수행합니다.
     *
     * @param request 업데이트할 이력서의 각 섹션(경력, 성과, 활동, 언어, 학력, 기술, 자격증 등)에 대한 데이터를 포함한
     *                UpdateResumeRequest 객체입니다.
     * @param resume  새로운 정보로 업데이트할 Resume 객체입니다.
     */
    private void update(UpdateResumeRequest request, Resume resume) {

        // 요청에 경력 정보가 포함된 경우 경력 섹션을 업데이트
        if (!request.getCareers().isEmpty()) {
            List<Career> careers = request.getCareers().stream().map(careerDto -> Career.update(careerDto, resume)).toList();
            careerRepository.update(careers, resume);
        }

        // 요청에 성과 정보가 포함된 경우 성과 섹션을 업데이트
        if (!request.getAchievements().isEmpty()) {
            List<Achievement> achievements = request.getAchievements().stream().map(achievementDto -> Achievement.update(achievementDto, resume)).toList();
            achievementRepository.update(achievements, resume);
        }

        // 요청에 활동 정보가 포함된 경우 활동 섹션을 업데이트
        if (!request.getActivities().isEmpty()) {
            List<Activity> activities = request.getActivities().stream().map(activityDto -> Activity.update(activityDto, resume)).toList();

            activityRepository.update(activities, resume);
        }

        // 요청에 언어 정보가 포함된 경우 언어 섹션을 업데이트
        if (!request.getLanguages().isEmpty()) {
            List<Language> languages = request.getLanguages().stream().map(languageDto -> Language.update(languageDto, resume)).toList();
            languageRepository.update(languages, resume);
        }

        // 요청에 학력 정보가 포함된 경우 학력 섹션을 업데이트
        if (!request.getEducations().isEmpty()) {
            List<Education> educations = request.getEducations().stream().map(educationDto -> Education.update(educationDto, resume)).toList();
            educationRepository.update(educations, resume);
        }

        // 요청에 기술 정보가 포함된 경우 기술 섹션을 업데이트
        if (!request.getMySkills().isEmpty()) {
            List<MySkill> mySkills = request.getMySkills().stream().map(mySkillsDto -> MySkill.update(mySkillsDto, resume)).toList();
            mySkillRepository.update(mySkills, resume);
        }

        // 요청에 자격증 정보가 포함된 경우 자격증 섹션을 업데이트
        if (!request.getQualifications().isEmpty()) {
            List<Qualification> qualifications = request.getQualifications().stream().map(qualificationDto -> Qualification.update(qualificationDto, resume)).toList();
            qualificationRepository.update(qualifications, resume);
        }
    }

    /**
     * 제공된 요청 데이터를 기반으로 새로운 이력서의 각 섹션(경력, 성과, 활동, 언어, 학력, 기술, 자격증 등)을 생성하고 저장합니다.
     * 각 섹션은 DTO를 도메인 객체로 변환한 후, 해당 이력서와의 관계를 설정하고,
     * 이를 엔티티로 변환한 후 해당 저장소에 저장합니다.
     *
     * @param request 새로운 이력서 데이터를 포함한 CreateResumeRequest 객체입니다.
     * @param resume  새로운 정보가 저장될 Resume 객체입니다.
     */
    private void create(CreateResumeRequest request, Resume resume) {

        // 요청에 경력 정보가 포함된 경우 경력 섹션을 생성하고 저장
        Optional<Careers> careers = Careers.of(request.getCareers(), resume);
        careers.ifPresent(value -> careerRepository.saveAll(value.getCareers()));

        // 요청에 성과 정보가 포함된 경우 성과 섹션을 생성하고 저장
        Optional<Achievements> achievements = Achievements.of(request.getAchievements(), resume);
        achievements.ifPresent(value -> achievementRepository.saveAll(value.getAchievements()));

        // 요청에 활동 정보가 포함된 경우 활동 섹션을 생성하고 저장
        Optional<Activities> activities = Activities.of(request.getActivities(), resume);
        activities.ifPresent(value -> activityRepository.saveAll(value.getActivities()));

        // 요청에 언어 정보가 포함된 경우 언어 섹션을 생성하고 저장
        Optional<Languages> languages = Languages.of(request.getLanguages(), resume);
        languages.ifPresent(value-> languageRepository.saveAll(value.getLanguages()));


        // 요청에 학력 정보가 포함된 경우 학력 섹션을 생성하고 저장
        Optional<Educations> educations = Educations.of(request.getEducations(), resume);
        educations.ifPresent(value -> educationRepository.saveAll(value.getEducations()));

        // 요청에 기술 정보가 포함된 경우 기술 섹션을 생성하고 저장
        Optional<MySkills> mySkills = MySkills.of(request.getMySkills(), resume);
        mySkills.ifPresent(value -> mySkillRepository.saveAll(value.getMySkills()));


        // 요청에 자격증 정보가 포함된 경우 자격증 섹션을 생성하고 저장
        Optional<Qualifications> qualifications = Qualifications.of(request.getQualifications(), resume);
        qualifications.ifPresent(value -> qualificationRepository.saveAll(value.getQualifications()));

    }


}
