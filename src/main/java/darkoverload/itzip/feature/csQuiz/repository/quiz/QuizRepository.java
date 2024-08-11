package darkoverload.itzip.feature.csQuiz.repository.quiz;

import darkoverload.itzip.feature.csQuiz.entity.QuizDocument;
import darkoverload.itzip.feature.csQuiz.repository.quiz.custom.CustomQuizRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.springframework.data.mongodb.repository.MongoRepository;

//퀴즈를 service계층에서 찾을 대 사용할 리파지토리
//JPA의 Bean 생성에서 제외시켰다.
@ExcludeFromJpaRepositories
public interface QuizRepository extends MongoRepository<QuizDocument, String>, CustomQuizRepository {
}