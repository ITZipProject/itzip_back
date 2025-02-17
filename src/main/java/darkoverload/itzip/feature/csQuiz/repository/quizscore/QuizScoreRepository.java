package darkoverload.itzip.feature.csQuiz.repository.quizscore;

import darkoverload.itzip.feature.csQuiz.entity.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizScoreRepository extends JpaRepository<QuizScore, Long> {

    Optional<List<QuizScore>> findTop6ByOrderByScoreDesc();

}
