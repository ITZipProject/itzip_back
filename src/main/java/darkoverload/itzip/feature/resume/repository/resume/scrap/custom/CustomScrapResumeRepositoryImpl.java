package darkoverload.itzip.feature.resume.repository.resume.scrap.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.resume.QResumeScrapEntity;
import darkoverload.itzip.feature.resume.entity.resume.ResumeScrapEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomScrapResumeRepositoryImpl implements CustomScrapResumeRepository{

    private final JPAQueryFactory queryFactory;
    private final QResumeScrapEntity qResumeScrap = QResumeScrapEntity.resumeScrapEntity;

    @Override
    public Optional<ResumeScrapEntity> findByResumeScrap(Long userId, Long resumeId) {
        ResumeScrapEntity resumeScrapEntity = queryFactory.selectFrom(qResumeScrap)
                .where(qResumeScrap.user.id.eq(userId).and(qResumeScrap.resume.id.eq(resumeId)))
                .fetchOne();

        return Optional.ofNullable(resumeScrapEntity);
    }

}
