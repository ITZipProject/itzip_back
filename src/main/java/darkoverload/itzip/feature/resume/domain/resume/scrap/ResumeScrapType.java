package darkoverload.itzip.feature.resume.domain.resume.scrap;

public enum ResumeScrapType {

    SCRAP("scrap"), UN_SCRAP("unscrap");

    private final String status;

    ResumeScrapType(String status) {
        this.status = status;
    }

    public static boolean isUnScrapEqual(String status) {
        return status.equals(UN_SCRAP.name());
    }

}
