package darkoverload.itzip.feature.job.domain.scrap;

public enum JobInfoScrapType {

    SCRAP("scrap"),
    UN_SCRAP("unscrap");

    private final String status;

    JobInfoScrapType(String status) {
        this.status = status;
    }

    public static boolean isUnScrapEqual(String status) {
        return status.equals(UN_SCRAP.name());
    }

}
