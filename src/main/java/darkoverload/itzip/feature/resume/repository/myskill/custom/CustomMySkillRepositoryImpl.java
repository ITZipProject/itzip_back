package darkoverload.itzip.feature.resume.repository.myskill.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
import darkoverload.itzip.feature.resume.entity.QMySkillEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomMySkillRepositoryImpl implements CustomMySkillRepository {

    private final JPAQueryFactory queryFactory;

    private final QMySkillEntity qMySkill = QMySkillEntity.mySkillEntity;

    @Override
    public List<MySkillEntity> findByAllResumeId(Long resumeId) {
        return queryFactory.selectFrom(qMySkill)
                .where(qMySkill.resume.id.eq(resumeId))
                .fetch();
    }
}
