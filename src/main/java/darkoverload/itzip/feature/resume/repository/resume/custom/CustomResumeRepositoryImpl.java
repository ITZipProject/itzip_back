package darkoverload.itzip.feature.resume.repository.resume.custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import darkoverload.itzip.feature.resume.controller.response.SearchResumeResponse;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.QResumeEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.util.StringUtils.*;


@Repository
@RequiredArgsConstructor
public class CustomResumeRepositoryImpl implements CustomResumeRepository {

    private final JPAQueryFactory queryFactory;

    private QResumeEntity qResume = QResumeEntity.resumeEntity;

    @Override
    public Long update(Resume resume) {

        JPAUpdateClause updateClause = queryFactory.update(qResume);

        Map<PathBuilder<Object>, Object> updates = new HashMap<>();

        updates(resume, updates);

        // 동적으로 필드 업데이트 적용
        updates.forEach((path, value) -> {
            if (value instanceof String) {
                updateClause.set(path, (String) value);
            } else if (value instanceof Enum) {
                updateClause.set(path, (Enum<?>) value);
            } else if (value instanceof List) {
                updateClause.set(path, (List<?>) value);
            } else {
                // 타입에 따라 다른 처리 추가 가능
                updateClause.set(path, value);
            }
        });

        return updateClause.where(qResume.id.eq(resume.getResumeId())).execute();
    }

    private void updates(Resume resume, Map<PathBuilder<Object>, Object> updates) {
        // email
        if (resume.getEmail() != null) {
            updates.put(new PathBuilder<>(String.class, qResume.email.getMetadata()), resume.getEmail());
        }

        // phone
        if (resume.getPhone() != null) {
            updates.put(new PathBuilder<>(String.class, qResume.phone.getMetadata()), resume.getPhone());
        }

        // subject
        if (resume.getSubject() != null) {
            updates.put(new PathBuilder<>(String.class, qResume.subject.getMetadata()), resume.getSubject());
        }

        // introduction
        if (resume.getIntroduction() != null) {
            updates.put(new PathBuilder<>(String.class, qResume.introduction.getMetadata()), resume.getIntroduction());
        }

        // PublicOnOff
        if (resume.getPublicOnOff() != null) {
            updates.put(new PathBuilder<>(Enum.class, qResume.publicOnOff.getMetadata()), resume.getPublicOnOff());
        }

        // Links
        if (resume.getLinks() != null && !resume.getLinks().isEmpty()) {
            updates.put(new PathBuilder<>(List.class, qResume.links.getMetadata()), resume.getLinks());
        }
    }

}
