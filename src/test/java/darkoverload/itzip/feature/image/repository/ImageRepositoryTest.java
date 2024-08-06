package darkoverload.itzip.feature.image.repository;

import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.feature.image.repository.ImageRepository;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
@SqlGroup({
        @Sql(value = "/sql/image/delete-image-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/image/image-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class ImageRepositoryTest {
    @Autowired
    private ImageRepository imageRepository;

    @Test
    void 레포지토리_update구문을_테스트한다(){
        String imagePath = "https://dy1vg9emkijkn.cloudfront.net/temporary/e44444qee-f337-487a-8c9e-100ba6d046cf.png";

        imageRepository.imagePathUpdate(imagePath, 1L);

        Image image = imageRepository.findById(1L).get().convertToDomain();
        assertThat(image.getImagePath()).isEqualTo(imagePath);
    }

}