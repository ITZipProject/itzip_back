package darkoverload.itzip.csQuiz.repository.quizcategory;

import darkoverload.itzip.csQuiz.entity.QuizCategory;
import darkoverload.itzip.csQuiz.repository.quizcategory.custom.CustomQuizCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//퀴즈 카테고리를 service계층에서 찾을 대 사용할 리파지토리
@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Long>, CustomQuizCategoryRepository {
}