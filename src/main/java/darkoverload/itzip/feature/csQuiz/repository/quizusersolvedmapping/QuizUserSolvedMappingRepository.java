package darkoverload.itzip.feature.csQuiz.repository.quizusersolvedmapping;

import darkoverload.itzip.feature.csQuiz.entity.QuizUserSolvedMapping;
import darkoverload.itzip.feature.csQuiz.repository.quizusersolvedmapping.Custom.CustomQuizUserSolvedMappingRepository;
import darkoverload.itzip.feature.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//사용자가 푼 퀴즈를를 service계층에서 찾을 때 사용할 리파지토리
@Repository
public interface QuizUserSolvedMappingRepository extends JpaRepository<QuizUserSolvedMapping, Integer>, CustomQuizUserSolvedMappingRepository {
    List<QuizUserSolvedMapping> findAllByUser(UserEntity userEntity);
}