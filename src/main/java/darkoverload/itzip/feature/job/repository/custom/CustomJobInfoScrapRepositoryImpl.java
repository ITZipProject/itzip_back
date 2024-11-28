package darkoverload.itzip.feature.job.repository.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.job.domain.JobInfoScrap;
import darkoverload.itzip.feature.job.domain.QJobInfoScrap;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomJobInfoScrapRepositoryImpl implements CustomJobInfoScrapRepository {

    private final JPAQueryFactory queryFactory;
    private final QJobInfoScrap jobInfoScrapEntity = QJobInfoScrap.jobInfoScrap;


    @Override
    public void bulkDeleteByPositionIds(List<Long> positionIds) {
        queryFactory.delete(jobInfoScrapEntity)
                .where(jobInfoScrapEntity.jobInfo.positionId.in(positionIds))
                .execute();
    }

    @Override
    public Optional<JobInfoScrap> findByJobInfoId(Long id, String email) {
        JobInfoScrap scrap = queryFactory.selectFrom(jobInfoScrapEntity)
                .where(
                        (jobInfoScrapEntity.jobInfo.id.eq(id))
                                .and(jobInfoScrapEntity.user.email.eq(email))
                )
                .fetchOne();

        return Optional.ofNullable(scrap);
    }

}
