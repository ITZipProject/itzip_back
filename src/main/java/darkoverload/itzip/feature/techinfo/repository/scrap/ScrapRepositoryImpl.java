package darkoverload.itzip.feature.techinfo.repository.scrap;

import darkoverload.itzip.feature.techinfo.domain.scrap.Scrap;
import darkoverload.itzip.feature.techinfo.model.document.ScrapDocument;
import darkoverload.itzip.feature.techinfo.repository.scrap.mongo.MongoScrapRepository;
import darkoverload.itzip.feature.techinfo.service.scrap.port.ScrapRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;
import lombok.RequiredArgsConstructor;

/**
 * MongoDB를 사용하여 스크랩 정보를 관리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class ScrapRepositoryImpl implements ScrapRepository {

    private final MongoScrapRepository repository;

    /**
     * 새로운 스크랩 정보를 저장합니다.
     *
     * @param scrap 저장할 Scrap
     */
    @Override
    public Scrap save(Scrap scrap) {
        return repository.save(ScrapDocument.from(scrap)).toModel();
    }

    /**
     * 특정 사용자가 특정 포스트를 스크랩했는지 확인합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @return 스크랩이 존재하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean existsByUserIdAndPostId(Long userId, ObjectId postId) {
        return repository.existsByUserIdAndPostId(userId, postId);
    }

    /**
     * 특정 사용자의 특정 포스트에 대한 스크랩을 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param postId 포스트 ID
     * @throws RestApiException 스크랩 삭제에 실패했을 때 발생
     */
    @Override
    public void deleteByUserIdAndPostId(Long userId, ObjectId postId) {
        if (repository.deleteByUserIdAndPostId(userId, postId) <= 0) {
            throw new RestApiException(CommonExceptionCode.DELETE_FAIL_SCRAP_IN_POST);
        }
    }

    /**
     * 저장된 모든 스크랩 데이터를 삭제합니다.
     * 주로 테스트 환경이나 데이터 초기화 시 사용됩니다.
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
