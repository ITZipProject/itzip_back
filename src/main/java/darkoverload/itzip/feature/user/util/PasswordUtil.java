package darkoverload.itzip.feature.user.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PasswordUtil {

    // 사용 가능한 문자 집합
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*?_";

    private static final SecureRandom random = new SecureRandom();

    /**
     * 주어진 정규식 조건을 만족하는 랜덤 비밀번호를 생성
     * 정규식: ^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$
     *
     * @return 조건에 맞는 랜덤 비밀번호
     */
    public static String generatePassword() {
        // 비밀번호 길이를 8~16 사이로 랜덤 선택
        int passwordLength = 8 + random.nextInt(9); // 8 ~ 16

        List<Character> passwordChars = new ArrayList<>();

        // 각 그룹에서 최소 1개씩 추가하여 정규식 조건 만족
        passwordChars.add(LETTERS.charAt(random.nextInt(LETTERS.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIALS.charAt(random.nextInt(SPECIALS.length())));

        // 남은 길이만큼 임의의 문자 추가
        String allAllowed = LETTERS + DIGITS + SPECIALS;
        for (int i = 3; i < passwordLength; i++) {
            passwordChars.add(allAllowed.charAt(random.nextInt(allAllowed.length())));
        }

        // 문자 순서를 섞어서 예측 불가하게 만듦
        Collections.shuffle(passwordChars, random);

        // List<Character>를 String으로 변환
        StringBuilder password = new StringBuilder();
        for (Character ch : passwordChars) {
            password.append(ch);
        }

        return password.toString();
    }
}
