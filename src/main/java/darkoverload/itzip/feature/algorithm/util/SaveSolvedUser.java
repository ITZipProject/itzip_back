package darkoverload.itzip.feature.algorithm.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import darkoverload.itzip.feature.algorithm.repository.user.SolvedacUserRepository;
import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.feature.image.service.CloudStorageService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.entity.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

//사용자 저장 공통 로직 리펙토링
@Component
@RequiredArgsConstructor
public class SaveSolvedUser {
    private final SolvedacUserRepository solvedacUserRepository;
    private final SolvedAcAPI solvedAcAPI;
    private final CloudStorageService cloudStorageService;

    /**
     * 사용자 정보 업데이트 혹은 저장 메서드
     * @param userId 사용자 id
     * @param username 사용자 이름
     */
    public SolvedacUser saveSolvedUser(Long userId, String username) {
        //solvedac 프로필 사진이 저장되는 디렉토리
        final String solvedAcProfileDir = "solvedacProfile";

        try{
            HttpResponse<String> response = solvedAcAPI.solvedacAPIRequest(solvedAcAPI.getUserByName(username));
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (response.statusCode() == 400) {
                throw new RestApiException(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USERNAME);
            }
            Image image = new Image();

            //사용자 사진이 있을경우에만 저장
            if (jsonObject.get("profileImageUrl") != null && !jsonObject.get("profileImageUrl").isJsonNull()){
                MultipartFile multipartFile = downloadImageAsMultipartFile(jsonObject.get("profileImageUrl").getAsString(), username);

                image = cloudStorageService.imageUpload(multipartFile, solvedAcProfileDir);
            }

            SolvedacUserEntity solvedacUserEntity = SolvedacUserEntity.builder()
                    .userId(userId)
                    .username(username)
                    .rating(jsonObject.get("rating").getAsInt())
                    .rank(jsonObject.get("rank").getAsInt())
                    .updateTime(LocalDateTime.now())
                    .profileImageUrl(image.getImagePath())
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

    /**
     * 이미지 url를 사진을 다운로드해 multifile 변환
     * @param imageUrl 이미지 주소
     * @return MultipartFile 형 이미지
     * @throws IOException url에 에러가 있을시 예외 출력
     */
    public MultipartFile downloadImageAsMultipartFile(String imageUrl, String username) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            byte[] imageBytes = response.body();

            client.close();
            return new CustomMultipartFile(imageBytes, username + ".jpg", "image/jpeg");

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestApiException(CommonExceptionCode.SOLVEDAC_PROFILEIMAGE_URL_ERROR);
        }
    }
}