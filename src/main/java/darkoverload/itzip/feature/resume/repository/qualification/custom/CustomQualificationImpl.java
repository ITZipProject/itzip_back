package darkoverload.itzip.feature.resume.repository.qualification.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.QQualificationEntity;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomQualificationImpl implements CustomQualification {

    private final JPAQueryFactory queryFactory;

    private final QQualificationEntity qualification = QQualificationEntity.qualificationEntity;


    @Override
    public List<QualificationEntity> findByAllResumeId(Long resumeId) {
        return queryFactory.selectFrom(qualification)
                .where(qualification.resume.id.eq(resumeId)).fetch();
    }
}
