package darkoverload.itzip.image.repository;

import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import darkoverload.itzip.image.domain.Image;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@SqlGroup({
        @Sql(value = "/sql/delete-image-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/image-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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