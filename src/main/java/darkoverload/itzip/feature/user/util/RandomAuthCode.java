package darkoverload.itzip.feature.user.util;

import java.security.SecureRandom;

/**
 * 랜덤 인증번호 생성 클래스
 */
public class RandomAuthCode {
    // 인증 코드에 사용될 문자들
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    // 인증 코드의 길이 (6자리)
    private static final int CODE_LENGTH = 6;
    // 보안 강화를 위한 SecureRandom 사용
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 인증 코드를 생성하는 메소드
     *
     * @return 생성된 인증 코드
     */
    public static String generate() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        // 지정된 길이만큼 랜덤 문자를 선택하여 인증 코드 생성
        for (int i = 0; i < CODE_LENGTH; i++) {
            // CHARACTERS에서 랜덤하게 문자를 선택하여 코드에 추가
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString(); // 생성된 인증 코드를 문자열로 반환
    }
}
