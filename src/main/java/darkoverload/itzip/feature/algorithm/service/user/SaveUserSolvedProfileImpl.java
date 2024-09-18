package darkoverload.itzip.feature.algorithm.service.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.algorithm.service.AlgorithmService;
import darkoverload.itzip.feature.algorithm.util.SolvedAcAPI;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

//처음 사용자가 solvedac username을 등록했을때 사용할 class
@Service
@RequiredArgsConstructor
public class SaveUserSolvedProfileImpl implements SaveUserSolvedProfile {
    private final SolvedAcAPI solvedAcAPI;

    private final SolvedacUserRepository solvedacUserRepository;
    private final AlgorithmService algorithmService;

    /**
     * 사용자 id와 이름을 받아와서 solvedAc를 등록하는 메서드
     * @param userId
     * @param username
     */
    @Override
    public void saveUserSolvedProfile(Long userId, String username) {
        try{
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getUserByName(username));
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            solvedacUserRepository.save(SolvedacUserEntity.builder()
                    .userId(userId)
                    .username(username)
                    .rating(jsonObject.get("rating").getAsInt())
                    .rank(jsonObject.get("rank").getAsInt())
                    .updateTime(LocalDateTime.now())
                    .profileImageUrl(jsonObject.get("profileImageUrl").getAsString())
                    .solvedClass(jsonObject.get("class").getAsInt())
                    .tier(jsonObject.get("tier").getAsInt())
                    .build());
            //사용자가 푼 문제들 저장
            algorithmService.saveUserSolvedProblem(userId);
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.SOLVED_USER_SOLVED_ERROR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_USER_SOLVED_ERROR);
        }
    }
}
