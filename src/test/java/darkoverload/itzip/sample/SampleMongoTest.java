package darkoverload.itzip.sample;


import darkoverload.itzip.mongo.sample.repository.SampleMongoRepository;
import darkoverload.itzip.mongo.sample.domain.SampleMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SampleMongoTest {

    private SampleMongo sampleMongo;

    @Autowired
    private SampleMongoRepository repository;

    @BeforeEach
    void setUp() {
        sampleMongo = new SampleMongo("SAMPLE00001", "샘플 테스트");
    }
    @AfterEach
    void afterEach(){
        repository.deleteAll(); //모두 롤백
    }

    @Test
    void mongo_save() {
        repository.save(sampleMongo);
        SampleMongo persistSample = repository.findById(sampleMongo.getId()).orElseThrow(RuntimeException::new);

        assertThat(persistSample.getId()).isEqualTo(sampleMongo.getId());
        assertThat(persistSample.getName()).isEqualTo(sampleMongo.getName());
    }

    @Test
    void mongo_update() {
        repository.save(sampleMongo);
        SampleMongo persistSample = repository.findById(sampleMongo.getId()).orElseThrow(RuntimeException::new);

        persistSample.changeName("샘플 테스트 수정");
        repository.save(persistSample);

        SampleMongo findById = repository.findById(persistSample.getId()).orElseThrow(RuntimeException::new);
        assertThat(findById.getName()).isEqualTo("샘플 테스트 수정");
    }

}
