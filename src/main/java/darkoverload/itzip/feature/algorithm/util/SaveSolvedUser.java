package darkoverload.itzip.feature.algorithm.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

//사용자 저장 공통 로직 리펙토링
@Component
@RequiredArgsConstructor
public class SaveSolvedUser {
    private final SolvedacUserRepository solvedacUserRepository;
    private final SolvedAcAPI solvedAcAPI;

    /**
     * 사용자 정보 업데이트 혹은 저장 메서드
     * @param userId 사용자 id
     * @param username 사용자 이름
     */
    public SolvedacUser saveSolvedUser(Long userId, String username) {
        try{
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getUserByName(username));
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (response.statusCode() == 400) {
                throw new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USERNAME);
            }

            SolvedacUserEntity solvedacUserEntity = SolvedacUserEntity.builder()
                    .userId(userId)
                    .username(username)
                    .rating(jsonObject.get("rating").getAsInt())
                    .rank(jsonObject.get("rank").getAsInt())
                    .updateTime(LocalDateTime.now())
                    .profileImageUrl(jsonObject.get("profileImageUrl").getAsString())
                    .solvedClass(jsonObject.get("class").getAsInt())
                    .tier(jsonObject.get("tier").getAsInt())
                    .build();
            solvedacUserRepository.save(solvedacUserEntity);
            return solvedacUserEntity.convertToDomain();
        }catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.SOLVED_USER_SOLVED_ERROR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVED_USER_SOLVED_ERROR);
        }
    }
}
