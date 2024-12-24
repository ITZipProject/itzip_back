package darkoverload.itzip.feature.resume.domain.resume.scrap;

public class ResumeScrap {
    public static final String MAP_RESUME_SCRAP_KEY = "RESUME_SCRAP:";
    private static final String DELIMITER = ":";

    public static String makeRedisKey(Long jobInfoId, String userEmail) {
        StringBuilder sb = new StringBuilder();
        return sb.append(MAP_RESUME_SCRAP_KEY)
                .append(jobInfoId)
                .append(DELIMITER)
                .append(userEmail).toString();
    }

    public static String[] getRedisKeyParts(String redisKey) {
        return redisKey.split(DELIMITER);
    }

}
