package darkoverload.itzip.feature.user.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Random;

@Component
@AllArgsConstructor
public class RandomNickname {
    // 형용사 배열
    private final String[] ADJECTIVES = {
            "행복한", "똑똑한", "즐거운", "강한", "빠른", "재치있는", "충성스러운", "멋진", "훌륭한", "즐거운", "아름다운", "기쁜", "사랑스러운", "행복한", "환상적인", "놀라운", "훌륭한", "매력적인", "긍정적인", "빛나는", "희망찬", "용감한", "따뜻한", "신나는", "친절한", "든든한", "감동적인", "뛰어난", "성실한", "창의적인", "자랑스러운", "유쾌한"
    };

    // 명사 배열
    private final String[] NOUNS = {
            "사자", "호랑이", "독수리", "상어", "판다", "여우", "늑대", "용", "곰", "매", "강아지", "고양이", "토끼", "햄스터", "앵무새", "거북이", "고슴도치", "물고기", "말", "돌고래", "펭귄", "코알라", "기린", "수달", "코끼리"
    };

    private final Random RANDOM = new Random();

    /**
     * 랜덤 닉네임을 생성하는 메소드
     * @return 생성한 닉네임
     */
    public String generate() {
        // 랜덤 형용사
        String adjective = ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)];
        // 랜덤 숫자
        String randomInt = String.valueOf(RANDOM.nextInt(999) + 1);
        // 랜덤 명사
        String noun = NOUNS[RANDOM.nextInt(NOUNS.length)];

        // 생성한 닉네임 반환 (Ex. 놀라운 469번째 호랑이)
        return MessageFormat.format("{0} {1}번째 {2}", adjective, randomInt, noun);
    }
}
