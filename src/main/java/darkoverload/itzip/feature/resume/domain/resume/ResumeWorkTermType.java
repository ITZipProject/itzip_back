package darkoverload.itzip.feature.resume.domain.resume;

public enum ResumeWorkTermType {

    NEW_PERSON("신입"),JUNIOR("(1~5)주니어"), SENIOR("(6~)시니어");

    private String name;

    private ResumeWorkTermType(String name) {
        this.name = name;
    }

    public static ResumeWorkTermType from(long term) {
        if(1L <= term && 5 >= term) {
            return ResumeWorkTermType.JUNIOR;
        }

        if(6 <= term) {
            return ResumeWorkTermType.SENIOR;
        }

        return ResumeWorkTermType.NEW_PERSON;
    }

    public String getName() {
        return name;
    }
}
