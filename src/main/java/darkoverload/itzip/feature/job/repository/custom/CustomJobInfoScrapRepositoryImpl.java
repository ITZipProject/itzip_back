package darkoverload.itzip.feature.job.repository.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.job.entity.JobInfoScrapEntity;
import darkoverload.itzip.feature.job.entity.QJobInfoEntity;
import darkoverload.itzip.feature.job.entity.QJobInfoScrapEntity;
import darkoverload.itzip.feature.user.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomJobInfoScrapRepositoryImpl implements CustomJobInfoScrapRepository{

    private final JPAQueryFactory queryFactory;
    private final QJobInfoScrapEntity jobInfoScrapEntity = QJobInfoScrapEntity.jobInfoScrapEntity;


    @Override
    public Optional<JobInfoScrapEntity> findByJobInfoId(Long id, String email) {
         JobInfoScrapEntity scrap = queryFactory.selectFrom(jobInfoScrapEntity)
                 .where(
                         (jobInfoScrapEntity.jobInfo.id.eq(id))
                                 .and(jobInfoScrapEntity.user.email.eq(email))
                ).fetchOne();

        return Optional.ofNullable(scrap);
    }


}
