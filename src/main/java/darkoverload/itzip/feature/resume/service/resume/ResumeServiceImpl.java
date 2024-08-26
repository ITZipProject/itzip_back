package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.domain.achievement.CreateAchievement;
import darkoverload.itzip.feature.resume.domain.activity.CreateActivity;
import darkoverload.itzip.feature.resume.domain.career.CreateCareer;
import darkoverload.itzip.feature.resume.domain.education.CreateEducation;
import darkoverload.itzip.feature.resume.domain.language.CreateLanguage;
import darkoverload.itzip.feature.resume.domain.myskill.CreateMySkill;
import darkoverload.itzip.feature.resume.domain.qualification.CreateQualification;
import darkoverload.itzip.feature.resume.domain.resume.CreateResume;
import darkoverload.itzip.feature.resume.entity.*;
import darkoverload.itzip.feature.resume.repository.Activity.ActivityRepository;
import darkoverload.itzip.feature.resume.repository.achievement.AchievementRepository;
import darkoverload.itzip.feature.resume.repository.career.CareerRepository;
import darkoverload.itzip.feature.resume.repository.education.EducationRepository;
import darkoverload.itzip.feature.resume.repository.language.LanguageRepository;
import darkoverload.itzip.feature.resume.repository.myskill.MySkillRepository;
import darkoverload.itzip.feature.resume.repository.qualification.QualificationRepository;
import darkoverload.itzip.feature.resume.repository.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{

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
        CreateResume resume = request.getResume().create(request.getUserId());

        // 이력서 저장
        ResumeEntity resumeEntity = resumeRepository.save(resume.toEntity());

        // 이력서와 관련된 내용들 저장
        create(request, resumeEntity);

    }


    private void create(CreateResumeRequest request, ResumeEntity resumeEntity) {

        if (!request.getCareers().isEmpty()) {
            List<CareerEntity> careerEntities = request.getCareers().stream().map(createCareerDto -> {
                CreateCareer createCareer = createCareerDto.create();
                createCareer.setResume(resumeEntity);
                return createCareer.toEntity();
            }).toList();

            careerRepository.saveAll(careerEntities);
        }

        if (!request.getAchievements().isEmpty()) {
            List<AchievementEntity> achievementEntities = request.getAchievements().stream().map(createAchievementDto -> {
                CreateAchievement createAchievement = createAchievementDto.create();
                createAchievement.setResume(resumeEntity);
                return createAchievement.toEntity();
            }).toList();

            achievementRepository.saveAll(achievementEntities);
        }

        if (!request.getActivities().isEmpty()) {
            List<ActivityEntity> activityEntities = request.getActivities().stream().map(createActivityDto -> {
                CreateActivity createActivity = createActivityDto.create();
                createActivity.setResume(resumeEntity);
                return createActivity.toEntity();
            }).toList();

            activityRepository.saveAll(activityEntities);
        }

        if (!request.getLanguages().isEmpty()) {

            List<LanguageEntity> languageEntities = request.getLanguages().stream().map(createLanguageDto -> {
                CreateLanguage createLanguage = createLanguageDto.create();
                createLanguage.setResume(resumeEntity);
                return createLanguage.toEntity();
            }).toList();
            languageRepository.saveAll(languageEntities);
        }

        if (!request.getEducations().isEmpty()) {
            List<EducationEntity> educationEntities = request.getEducations().stream().map(createEducationDto -> {
                CreateEducation createEducation = createEducationDto.create();
                createEducation.setResume(resumeEntity);
                return createEducation.toEntity();
            }).toList();

            educationRepository.saveAll(educationEntities);
        }

        if(!request.getMySkills().isEmpty()) {
            List<MySkillEntity> mySkillEntities = request.getMySkills().stream().map(createMySkillsDto -> {
                CreateMySkill createMySkill = createMySkillsDto.create();
                createMySkill.setResume(resumeEntity);
                return createMySkill.toEntity();
            }).toList();


            mySkillRepository.saveAll(mySkillEntities);
        }


        if(!request.getQualifications().isEmpty()) {
            List<QualificationEntity> qualificationEntities = request.getQualifications().stream().map(createQualificationDto -> {
                CreateQualification createQualification = createQualificationDto.create();
                createQualification.setResume(resumeEntity);
                return createQualification.toEntity();
            }).toList();

            qualificationRepository.saveAll(qualificationEntities);
        }

    }

}
