package darkoverload.itzip.feature.csQuiz.repository.quiz.custom;

import darkoverload.itzip.feature.csQuiz.entity.QuizDocument;
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
    public Page<QuizDocument> findByDifficultyAndCategoryAndUserSolved(Integer difficulty, Long categoryId, List<String> userSolved, Pageable pageable, boolean inUserSolved, String keyword) {
        Query query = new Query();

        // 필터 조건이 존재할 경우에만 해당 기준 추가
        //난이도 필터가 있을경우 추가
        if (difficulty != null) {
            query.addCriteria(Criteria.where("difficulty").is(difficulty));
        }

        //카테고리 필터가 있을 경우 추가
        if (categoryId != null) {
            query.addCriteria(Criteria.where("categoryId").is(categoryId));
        }

        //사용자 문제를 제외하냐 조건이 있으면 추가 (사용자가 푼문제가 있고, 사용자 푼문제를 포함하지 않으면 제외함)
        if (userSolved != null && !userSolved.isEmpty() && !inUserSolved) {
            query.addCriteria(Criteria.where("_id").nin(userSolved));
        }

        // 키워드 검색 로직 추가 (문제와 카테고리 이름에서 검색)
        if (keyword != null && !keyword.isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("questionText").regex(keyword, "i"),    // 문제에서 검색
                    Criteria.where("category").regex(keyword, "i")         // 카테고리 이름에서 검색
            ));
        }
        // 쿼리에 맞는 총 퀴즈 개수 계산 (페이지네이션 적용 전)
        long count = mongoTemplate.count(query, QuizDocument.class);

        //쿼리에 page적용
        query.with(pageable);

        // 쿼리를 실행하여 퀴즈 목록을 가져옴
        List<QuizDocument> quizzes = mongoTemplate.find(query, QuizDocument.class);

        // 결과를 Page 객체로 반환
        return new PageImpl<>(quizzes, pageable, count);
    }
}
