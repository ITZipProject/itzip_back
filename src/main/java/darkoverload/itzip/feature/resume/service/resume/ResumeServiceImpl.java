package darkoverload.itzip.feature.resume.service.resume;

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
import darkoverload.itzip.feature.resume.domain.myskill.MySkill;
import darkoverload.itzip.feature.resume.domain.myskill.MySkills;
import darkoverload.itzip.feature.resume.domain.qualification.Qualification;
import darkoverload.itzip.feature.resume.domain.qualification.Qualifications;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeDetails;
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
    public CreateResumeResponse create(CreateResumeRequest request) {

        //"career", "skill", "language", "qualification", "education", "activity", "achievement"
        Resume resume = Resume.create(request.getResume(), request.getUserId());

        // 이력서 저장
        // 이력서와 관련된 내용들 저장
        return CreateResumeResponse.from(create(request, resumeRepository.save(resume)));
    }

    /**
     * 제공된 요청 데이터를 기반으로 새로운 이력서의 각 섹션(경력, 성과, 활동, 언어, 학력, 기술, 자격증 등)을 생성하고 저장합니다.
     * 각 섹션은 DTO를 도메인 객체로 변환한 후, 해당 이력서와의 관계를 설정하고,
     * 이를 엔티티로 변환한 후 해당 저장소에 저장합니다.
     *
     * @param request 새로운 이력서 데이터를 포함한 CreateResumeRequest 객체입니다.
     * @param resume  새로운 정보가 저장될 Resume 객체입니다.
     */
    private ResumeDetails create(CreateResumeRequest request, Resume resume) {

        // 요청에 경력 정보가 포함된 경우 경력 섹션을 생성하고 저장
        Optional<Careers> careers = Careers.of(request.getCareers(), resume);
        Optional<Careers> dataCareers = Optional.empty();
        if (careers.isPresent()) {
            dataCareers = Careers.of(careerRepository.saveAll(careers.get().getCareers()));
        }

        // 요청에 성과 정보가 포함된 경우 성과 섹션을 생성하고 저장
        Optional<Achievements> achievements = Achievements.of(request.getAchievements(), resume);
        Optional<Achievements> dataAchievements = Optional.empty();
        if (achievements.isPresent()) {
            dataAchievements = Achievements.of(achievementRepository.saveAll(achievements.get().getAchievements()));
        }

        // 요청에 활동 정보가 포함된 경우 활동 섹션을 생성하고 저장
        Optional<Activities> activities = Activities.of(request.getActivities(), resume);
        Optional<Activities> dataActivities = Optional.empty();
        if (activities.isPresent()) {
            dataActivities = Activities.of(activityRepository.saveAll(activities.get().getActivities()));
        }

        // 요청에 언어 정보가 포함된 경우 언어 섹션을 생성하고 저장
        Optional<Languages> languages = Languages.of(request.getLanguages(), resume);
        Optional<Languages> dataLanguages = Optional.empty();
        if (languages.isPresent()) {
            dataLanguages = Languages.of(languageRepository.saveAll(languages.get().getLanguages()));
        }

        // 요청에 학력 정보가 포함된 경우 학력 섹션을 생성하고 저장
        Optional<Educations> educations = Educations.of(request.getEducations(), resume);
        Optional<Educations> dataEducations = Optional.empty();
        if (educations.isPresent()) {
            dataEducations = Educations.of(educationRepository.saveAll(educations.get().getEducations()));
        }

        // 요청에 기술 정보가 포함된 경우 기술 섹션을 생성하고 저장
        Optional<MySkills> mySkills = MySkills.of(request.getMySkills(), resume);
        Optional<MySkills> dataMySkills = Optional.empty();
        if (mySkills.isPresent()) {
            dataMySkills = MySkills.of(mySkillRepository.saveAll(mySkills.get().getMySkills()));
        }

        // 요청에 자격증 정보가 포함된 경우 자격증 섹션을 생성하고 저장
        Optional<Qualifications> qualifications = Qualifications.of(request.getQualifications(), resume);
        Optional<Qualifications> dataQualifications = Optional.empty();
        if (qualifications.isPresent()) {
            dataQualifications = Qualifications.of(qualificationRepository.saveAll(qualifications.get().getQualifications()));
        }

        return ResumeDetails.of(dataAchievements.orElse(null), dataActivities.orElse(null), dataCareers.orElse(null), dataEducations.orElse(null), dataLanguages.orElse(null), dataMySkills.orElse(null), dataQualifications.orElse(null), resume);
    }


    @Override
    public UpdateResumeResponse update(UpdateResumeRequest request) {
        Resume updateResume = Resume.update(request.getResume(), request.getResumeId(), request.getUserId());

        return UpdateResumeResponse.from(update(request, resumeRepository.update(updateResume)));
    }

    /**
     *
     * @param request
     * @param resume
     * @return
     */
    private ResumeDetails update(UpdateResumeRequest request, Resume resume) {
        Optional<Careers> careers = Careers.of(request.getCareers(), resume);
        Optional<Careers> dataCareers = Optional.empty();
        if (careers.isPresent()) {
            List<Career> allCareers = careerRepository.findAllByResumeId(resume.getResumeId());
            careerRepository.deleteAllCareers(careers.get().deleteCareers(allCareers));
            dataCareers = Careers.of(careerRepository.update(careers.get().getCareers()));
        }

        Optional<Achievements> achievements = Achievements.of(request.getAchievements(), resume);
        Optional<Achievements> dataAchievements = Optional.empty();
        if (achievements.isPresent()) {
            List<Achievement> allAchievements = achievementRepository.findAllByResumeId(resume.getResumeId());
            achievementRepository.deleteAllAchievements(achievements.get().deleteAchievements(allAchievements));
            dataAchievements = Achievements.of(achievementRepository.update(achievements.get().getAchievements()));
        }

        Optional<Activities> activities = Activities.of(request.getActivities(), resume);
        Optional<Activities> dataActivities = Optional.empty();
        if (activities.isPresent()) {
            List<Activity> allActivities = activityRepository.findAllByResumeId(resume.getResumeId());
            activityRepository.deleteAllActivities(activities.get().deleteActivities(allActivities));
            dataActivities = Activities.of(activityRepository.update(activities.get().getActivities()));
        }

        Optional<Languages> languages = Languages.of(request.getLanguages(), resume);
        Optional<Languages> dataLanguages = Optional.empty();
        if (languages.isPresent()) {
            List<Language> allLanguages = languageRepository.findAllByResumeId(resume.getResumeId());
            languageRepository.deleteAllLanguages(languages.get().deleteLanguages(allLanguages));
            dataLanguages = Languages.of(languageRepository.update(languages.get().getLanguages()));
        }

        Optional<Educations> educations = Educations.of(request.getEducations(), resume);
        Optional<Educations> dataEducations = Optional.empty();
        if (educations.isPresent()) {
            List<Education> allEducations = educationRepository.findAllByResumeId(resume.getResumeId());
            educationRepository.deleteAllEducations(educations.get().deleteEducations(allEducations));
            dataEducations = Educations.of(educationRepository.update(educations.get().getEducations()));
        }

        Optional<MySkills> mySkills = MySkills.of(request.getMySkills(), resume);
        Optional<MySkills> dataMySkills = Optional.empty();
        if (mySkills.isPresent()) {
            List<MySkill> allMySkills = mySkillRepository.findByAllResumeId(resume.getResumeId());
            mySkillRepository.deleteAllMySkills(mySkills.get().deleteMySkills(allMySkills));
            dataMySkills = MySkills.of(mySkillRepository.update(mySkills.get().getMySkills()));
        }

        Optional<Qualifications> qualifications = Qualifications.of(request.getQualifications(), resume);
        Optional<Qualifications> dataQualifications = Optional.empty();
        if (qualifications.isPresent()) {
            List<Qualification> allQualifications = qualificationRepository.findByAllResumeId(resume.getResumeId());
            qualificationRepository.deleteAllQualifications(allQualifications);
            dataQualifications = Qualifications.of(qualificationRepository.update(qualifications.get().getQualifications()));
        }

        return ResumeDetails.of(dataAchievements.orElse(null), dataActivities.orElse(null), dataCareers.orElse(null), dataEducations.orElse(null), dataLanguages.orElse(null), dataMySkills.orElse(null), dataQualifications.orElse(null), resume);
    }

}
