package darkoverload.itzip.school.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum EstType {
    NATIONAL_SCHOOL("국립"), PUBLIC_SCHOOL("공립"), PRIVATE_SCHOOL("사립");
    private final String type;

    EstType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static EstType fromEstTypeName(String type) {
        for(EstType estType : values()) {
            if(estType.getType().equals(type)) return  estType;
        }

        log.info("Unknown region name: " + type);
        return null;
    }
}
