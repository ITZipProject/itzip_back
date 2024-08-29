package darkoverload.itzip.feature.resume.code;

public enum PublicOnOff {
    YES(true), NO(false);

    private final boolean isPublic;

    PublicOnOff(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
