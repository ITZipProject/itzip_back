package darkoverload.itzip.feature.resume.repository.resume.scrap.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.domain.resume.scrap.QResumeScrap;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomScrapResumeRepositoryImpl implements CustomScrapResumeRepository{

    private final JPAQueryFactory queryFactory;
    private final QResumeScrap qResumeScrap = QResumeScrap.resumeScrap;

    @Override
    public Optional<ResumeScrap> findByResumeScrap(Long resumeId, String userEmail) {
        ResumeScrap resumeScrap = queryFactory.selectFrom(qResumeScrap)
                .where(qResumeScrap.user.email.eq(userEmail).and(qResumeScrap.resume.id.eq(resumeId)))
                .fetchOne();

        return Optional.ofNullable(resumeScrap);
    }

}
