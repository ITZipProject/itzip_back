package darkoverload.itzip.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static darkoverload.itzip.image.entity.QImageEntity.imageEntity;
import darkoverload.itzip.image.entity.ImageEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class CustomImageRepositoryImpl implements CustomImageRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ImageEntity> findByImagePath(String imagePath) {
        return Optional.ofNullable(queryFactory
                .selectFrom(imageEntity)
                .where(imageEntity.imagePath.eq(imagePath))
                .fetchOne());
    }

    @Override
    public void imagePathUpdate(String imagePath, Long imageSeq) {
        queryFactory.update(imageEntity)
                .set(imageEntity.imagePath, imagePath)
                .where(imageEntity.imageSeq.eq(imageSeq))
                .execute();
    }

}
