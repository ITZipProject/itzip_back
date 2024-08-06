package darkoverload.itzip.sample;

import com.querydsl.jpa.impl.JPAQueryFactory;
import darkoverload.itzip.postgresql.sample.HelloEntity;
import darkoverload.itzip.postgresql.sample.QHelloEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class SampleQueryDslTest {

    @Autowired
    EntityManager em;

    @Test
    void queryDslTest(){
        HelloEntity helloEntity = new HelloEntity();
        em.persist(helloEntity);

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QHelloEntity qHello = QHelloEntity.helloEntity; //Querydsl Q타입 동작 확인

        HelloEntity result = queryFactory
                .selectFrom(qHello)
                .fetchOne();

        assertThat(result).isEqualTo(helloEntity);
        assertThat(result.getId()).isEqualTo(helloEntity.getId());
    }

}