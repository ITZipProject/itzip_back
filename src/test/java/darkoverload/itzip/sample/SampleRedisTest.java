package darkoverload.itzip.sample;

import darkoverload.itzip.sample.Repository.SampleRedisRepository;
import darkoverload.itzip.sample.domain.SampleRedis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RedisSample CRUD Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleRedisTest {

    @Autowired
    private SampleRedisRepository repository;

    private SampleRedis sampleRedis;

    @BeforeEach
    void setUp() {
        sampleRedis = new SampleRedis("SAMPLE00001", "샘플 테스트");
    }

    @AfterEach
    void setDown() {
        repository.deleteById(sampleRedis.getId());
    }

    @Test
    @DisplayName("Redis에 데이터를 저장")
    void redis_save(){
        //given
        repository.save(sampleRedis);

        // when
        SampleRedis persistSample = repository.findById(sampleRedis.getId()).orElseThrow(RuntimeException::new);

        // then
        assertThat(persistSample.getId()).isEqualTo(sampleRedis.getId());
        assertThat(persistSample.getName()).isEqualTo(sampleRedis.getName());
    }

    @Test
    @DisplayName("Redis 데이터 수정")
    void redis_update(){
        //given
        repository.save(sampleRedis);
        SampleRedis persistSample = repository.findById(sampleRedis.getId()).orElseThrow(RuntimeException::new);

        //when
        persistSample.changeName("샘플 테스트 수정");
        repository.save(persistSample);

        //then
        assertThat(persistSample.getName()).isEqualTo("샘플 테스트 수정");
    }


    @Test
    @DisplayName("Redis 데이터 삭제")
    void redis_delete(){
        //given
        repository.save(sampleRedis);

        //when
        repository.delete(sampleRedis);
        Optional<SampleRedis> deletedSampleRedis = repository.findById(sampleRedis.getId());

        // then
        assertThat(deletedSampleRedis.isEmpty()).isTrue();
    }
}
