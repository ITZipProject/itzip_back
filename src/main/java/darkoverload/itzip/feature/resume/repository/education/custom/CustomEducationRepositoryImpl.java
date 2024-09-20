package darkoverload.itzip.feature.resume.repository.education.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.entity.QEducationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomEducationRepositoryImpl implements CustomEducationRepository{

    private final JPAQueryFactory queryFactory;

    private final QEducationEntity qEducation = QEducationEntity.educationEntity;

    @Override
    public List<EducationEntity> findAllByResumeId(Long resumeId) {
        return queryFactory.selectFrom(qEducation)
                .where(qEducation.resume.id.eq(resumeId)).fetch();
    }
}
