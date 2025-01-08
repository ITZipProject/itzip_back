package darkoverload.itzip.feature.user.service;

import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.feature.image.service.CloudStorageService;
import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.user.controller.request.ChangeNicknameRequest;
import darkoverload.itzip.feature.user.controller.request.ChangePasswordRequest;
import darkoverload.itzip.feature.user.domain.User;
import darkoverload.itzip.feature.user.repository.UserRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService {
    UserService userService;
    UserRepository userRepository;
    private final CloudStorageService storageService;

    /**
     * 닉네임 중복 체크
     */
    @Override
    public String checkDuplicateNickname(String nickname) {
        // 사용 중인 닉네임일 경우
        if (userService.findByNickname(nickname).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
        }

        return "사용 가능한 닉네임입니다.";
    }

    /**
     * 닉네임 변경
     */
    @Override
    public String changeNickname(CustomUserDetails userDetails, ChangeNicknameRequest changeNicknameRequest) {
        // 요청 닉네임
        String nickname = changeNicknameRequest.getNickname();

        // 사용 중인 닉네임일 경우
        if (userService.findByNickname(nickname).isPresent()) {
            throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
        }

        // 유저 데이터를 가져와 닉네임 변경
        User user = userService.findByEmail(userDetails.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        user.setNickname(nickname);
        userRepository.save(user.convertToEntity());

        return "닉네임이 변경되었습니다.";
    }

    /**
     * 비밀번호 변경
     */
    @Override
    public String changePassword(CustomUserDetails userDetails, ChangePasswordRequest changePasswordRequest) {
        // 요청 비밀번호
        String password = changePasswordRequest.getPassword();
        // 암호화한 비밀번호
        String encryptPassword = userService.encryptPassword(password);

        // 로그인 유저 데이터를 가져와 비밀번호 변경
        User user = userService.findByEmail(userDetails.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        user.setPassword(encryptPassword);
        userRepository.save(user.convertToEntity());

        return "비밀번호가 변경되었습니다.";
    }

    /**
     * 프로필 이미지 변경
     */
    @Override
    public String changeImageUrl(CustomUserDetails userDetails, MultipartFile file) {
        // 파일 빈값 체크
        if (file.isEmpty()) {
            throw new RestApiException(CommonExceptionCode.FILED_ERROR);
        }

        // s3 프로필 이미지 디렉토리
        String profileDir = "profile";

        // 이미지 업로드
        Image image = storageService.imageUpload(file, profileDir);

        User user = userService.findByEmail(userDetails.getEmail()).orElseThrow(() -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER));

        // 기존 프로필 이미지 삭제
        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            storageService.imageDelete(user.getImageUrl(), profileDir);
        }

        user.setImageUrl(image.getImagePath());
        userRepository.save(user.convertToEntity());

        return "프로필 이미지가 변경되었습니다.";
    }
}
