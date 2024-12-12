package darkoverload.itzip.feature.techinfo.service.scrap.port;

import darkoverload.itzip.feature.techinfo.domain.scrap.Scrap;
import org.bson.types.ObjectId;

public interface ScrapRepository {

    Scrap save(Scrap scrap);

    boolean existsByUserIdAndPostId(Long userId, ObjectId postId);

    void deleteByUserIdAndPostId(Long userId, ObjectId postId);

    void deleteAll();

}
