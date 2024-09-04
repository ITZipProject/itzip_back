package darkoverload.itzip.feature.job.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JobInfoStringUtil {


    /**
     * 주어진 문자열을 쉼표(,) 또는 공백을 기준으로 분리하여, 각 부분 문자열을 리스트로 변환합니다.
     *
     * @param targetStr 쉼표 또는 공백으로 구분된 문자열.
     *                 이 문자열은 쉼표나 공백을 기준으로 분리됩니다.
     * @return 분리된 문자열을 포함하는 리스트.
     *         문자열이 null이거나 비어 있는 경우 빈 리스트를 반환합니다.
     */
    public static List<String> convertList(String targetStr) {

        // 문자열이 null이거나 빈 문자열인 경우 빈 리스트를 반환
        if (targetStr == null || targetStr.trim().isEmpty()) {
            log.info("targetStr is null or empty, returning an empty list");
            return Collections.emptyList();
        }

        // 주어진 문자열을 쉼표(,) 또는 공백을 기준으로 분리하여 리스트로 변환
        // "[,\\s]+" 정규 표현식은 쉼표나 공백이 하나 이상 연속되는 부분을 분리점으로 인식
        return Arrays.asList(targetStr.split("[,\\s]+"));
    }


}
