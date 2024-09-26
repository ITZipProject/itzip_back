package darkoverload.itzip.feature.resume.repository.career.Custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
import darkoverload.itzip.feature.resume.entity.QCareerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCareerRepositoryImpl implements CustomCareerRepository{

    private final JPAQueryFactory queryFactory;

    private final QCareerEntity qCareer = QCareerEntity.careerEntity;


    @Override
    public List<CareerEntity> findAllByResumeId(Long resumeId) {

        return queryFactory.selectFrom(qCareer)
                .where(qCareer.resume.id.eq(resumeId)).fetch();
    }
}
