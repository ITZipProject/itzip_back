package darkoverload.itzip.feature.resume.repository.language.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.entity.QLanguageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CustomLanguageRepositoryImpl implements CustomLanguageRepository{

    private final JPAQueryFactory queryFactory;

    private final QLanguageEntity qLanguage = QLanguageEntity.languageEntity;

    @Override
    public List<LanguageEntity> findAllByResumeId(Long resumeId) {

        return queryFactory.selectFrom(qLanguage)
                .where(qLanguage.resume.id.eq(resumeId)).fetch();
    }
}
