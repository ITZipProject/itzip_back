package darkoverload.itzip.feature.job.util;


import lombok.extern.slf4j.Slf4j;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class TimeStampUtil {

    /**
     * 주어진 유닉스 타임스탬프 문자열을 Asia/Seoul 시간대의 LocalDateTime 객체로 변환합니다.
     * @param timeStampString 유닉스 타임스탬프를 나타내는 문자열 (초 단위)
     * @return Asia/Seoul 시간대의 LocalDateTime 객체
     */
    public static LocalDateTime convertToLocalDateTime(String timeStampString){
        // 타임스탬프 문자열을 long 타입 값으로 변환
        long timestamp = Long.parseLong(timeStampString);

        // 변환된 long 타입 타임스탬프를 Instant로 변환한 후, 이를 Asia/Seoul 시간대의 LocalDateTime으로 변환
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("Asia/Seoul"));

        log.info("Convert Time :: {} ", dateTime);

        return dateTime;
    }
}
