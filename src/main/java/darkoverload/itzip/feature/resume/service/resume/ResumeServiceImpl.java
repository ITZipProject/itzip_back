package darkoverload.itzip.feature.resume.service.resume;

import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.domain.achievement.Achievement;
import darkoverload.itzip.feature.resume.domain.activity.Activity;
import darkoverload.itzip.feature.resume.domain.career.Career;
import darkoverload.itzip.feature.resume.domain.education.CreateEducation;
import darkoverload.itzip.feature.resume.domain.language.CreateLanguage;
import darkoverload.itzip.feature.resume.domain.myskill.CreateMySkill;
import darkoverload.itzip.feature.resume.domain.qualification.CreateQualification;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.*;
import darkoverload.itzip.feature.resume.repository.Activity.ActivityRepository;
import darkoverload.itzip.feature.resume.repository.achievement.AchievementRepository;
import darkoverload.itzip.feature.resume.repository.career.CareerRepository;
import darkoverload.itzip.feature.resume.repository.education.EducationRepository;
import darkoverload.itzip.feature.resume.repository.language.LanguageRepository;
import darkoverload.itzip.feature.resume.repository.myskill.MySkillRepository;
import darkoverload.itzip.feature.resume.repository.qualification.QualificationRepository;
import darkoverload.itzip.feature.resume.repository.resume.ResumeRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
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
        Resume resume = Resume.create(request.getResume(), request.getUserId());

        // 이력서 저장
        ResumeEntity resumeEntity = resumeRepository.save(resume.toEntity());

        // 이력서와 관련된 내용들 저장
        create(request, resumeEntity);

    }

    @Transactional
    @Override
    public void update(UpdateResumeRequest request) {

        Resume updateResume = Resume.update(request.getResume(), request.getResumeId(), request.getUserId());

        Long resumeUpdate = resumeRepository.update(updateResume);

        if(resumeUpdate < 0) throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_RESUME);

        ResumeEntity resume = resumeRepository.getReferenceById(request.getResumeId());

        update(request, resume);
    }


    private void update(UpdateResumeRequest request, ResumeEntity resume) {

        if(!request.getCareers().isEmpty()){
            List<CareerEntity> careerEntities = request.getCareers().stream().map(careerDto -> {
                    Career update = Career.update(careerDto, resume);
                    return update.toEntity();
            }).toList();

            careerRepository.saveAll(careerEntities);
        }

        if(!request.getAchievements().isEmpty()) {
            List<AchievementEntity> achievementEntities = request.getAchievements().stream().map(achievementDto -> {
                Achievement update = Achievement.update(achievementDto, resume);
                return  update.toEntity();
            }).toList();

            achievementRepository.saveAll(achievementEntities);
        }

        if(!request.getActivities().isEmpty()) {
            List<ActivityEntity> activityEntities = request.getActivities().stream().map(activityDto -> {

                Activity update = Activity.update(activityDto, resume);
                return update.toEntity();
            }).toList();

            activityRepository.saveAll(activityEntities);
        }

        
    }


    /**
     *
     * @param request
     * @param resumeEntity
     */
    private void create(CreateResumeRequest request, ResumeEntity resumeEntity) {

        if (!request.getCareers().isEmpty()) {
            List<CareerEntity> careerEntities = request.getCareers().stream().map(createCareerDto -> {
                Career career = createCareerDto.create();
                career.setResume(resumeEntity);
                return career.toEntity();
            }).toList();

            careerRepository.saveAll(careerEntities);
        }

        if (!request.getAchievements().isEmpty()) {
            List<AchievementEntity> achievementEntities = request.getAchievements().stream().map(createAchievementDto -> {
                Achievement achievement = createAchievementDto.create();
                achievement.setResume(resumeEntity);
                return achievement.toEntity();
            }).toList();

            achievementRepository.saveAll(achievementEntities);
        }

        if (!request.getActivities().isEmpty()) {
            List<ActivityEntity> activityEntities = request.getActivities().stream().map(createActivityDto -> {
                Activity activity = createActivityDto.create();
                activity.setResume(resumeEntity);
                return activity.toEntity();
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
