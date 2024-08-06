package darkoverload.itzip.feature.csQuiz.repository.quiz.custom;

import darkoverload.itzip.feature.csQuiz.entity.QuizEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
//cs퀴즈 관련 커스텀 코드를 구현하는 Impl
@RequiredArgsConstructor
public class CustomQuizRepositoryImpl implements CustomQuizRepository {
    private final MongoTemplate mongoTemplate;

    /**
     * 주어진 난이도, 카테고리 ID, 사용자가 푼 퀴즈 목록을 기준으로 퀴즈를 조회하는 메서드
     *
     * @param difficulty 퀴즈 난이도
     * @param categoryId 카테고리 ID
     * @param userSolved 사용자가 푼 퀴즈 ID 목록
     * @param pageable 페이지 정보
     * @return 페이징 처리된 퀴즈 목록
     */
    @Override
    public Page<QuizEntity> findByDifficultyAndCategoryAndUserSolved(Integer difficulty, Long categoryId, List<String> userSolved, Pageable pageable) {
        Query query = new Query()
                // 난이도에 맞는 기준 추가
                .addCriteria(Criteria.where("difficulty").is(difficulty))
                // 카테고리 ID에 맞는 기준 추가
                .addCriteria(Criteria.where("categoryId").is(categoryId))
                // 사용자가 푼 퀴즈를 제외하는 기준 추가
                .addCriteria(Criteria.where("id").nin(userSolved))
                .with(pageable);

        // 쿼리를 실행하여 퀴즈 목록을 가져옴
        List<QuizEntity> quizzes = mongoTemplate.find(query, QuizEntity.class);
        // 쿼리에 맞는 총 퀴즈 개수 계산
        long count = mongoTemplate.count(query, QuizEntity.class);

        // 결과를 Page 객체로 반환
        return new PageImpl<>(quizzes, pageable, count);
    }

    /**
     * 주어진 난이도와 카테고리 ID를 기준으로 퀴즈를 조회하는 메서드
     *
     * @param difficulty 퀴즈 난이도
     * @param categoryId 카테고리 ID
     * @param pageable 페이지 정보
     * @return 페이징 처리된 퀴즈 목록
     */
    @Override
    public Page<QuizEntity> findByDifficultyAndCategory(Integer difficulty, Long categoryId, Pageable pageable) {
        Query query = new Query()
                // 난이도에 맞는 기준 추가
                .addCriteria(Criteria.where("difficulty").is(difficulty))
                // 카테고리 ID에 맞는 기준 추가
                .addCriteria(Criteria.where("categoryId").is(categoryId))
                .with(pageable);

        // 쿼리를 실행하여 퀴즈 목록을 가져옴
        List<QuizEntity> quizzes = mongoTemplate.find(query, QuizEntity.class);
        // 쿼리에 맞는 총 퀴즈 개수 계산
        long count = mongoTemplate.count(query, QuizEntity.class);

        // 결과를 Page 객체로 반환
        return new PageImpl<>(quizzes, pageable, count);
    }
}
