package darkoverload.itzip.feature.job.repository.custom;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.job.entity.QJobInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomJobInfoRepositoryImpl implements CustomJobInfoRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public long bulkDeleteByPositionIds(List<Long> positionIds) {
        QJobInfoEntity jobInfoEntity = QJobInfoEntity.jobInfoEntity;

        return queryFactory.delete(jobInfoEntity)
                .where(jobInfoEntity.positionId.in(positionIds))
                .execute();
    }
}
