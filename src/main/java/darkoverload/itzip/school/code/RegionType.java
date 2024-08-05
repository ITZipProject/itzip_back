package darkoverload.itzip.school.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum RegionType {
    SEOUL("서울특별시"),BUSAN("부산광역시"),
    INCHEON("인천광역시"), DAEJEON("대전광역시"),
    DAEGU("대구광역시"), ULSAN("울산광역시"),
    GWANGJI("광주광역시"), GYEONGGIDO("경기도"),
    GANGWONDO("강원특별자치도"), CHUNGCHEONGBUKDO("충청북도"),
    CHUNGCHEONGNAMDO("충청남도"), JEONBUK("전북특별자치도"),
    JEOLLANAMDO("전라남도"), GYEONGSANGBUKDO("경상북도"),
    GYEONGSANGNAMDO("경상남도"), JEJU("제주특별자치도"),
    SEJONG("세종특별자치시");
    private final String name;

    RegionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RegionType fromRegionName(String name) {
        for (RegionType region : values()) {
            if(region.getName().equals(name))  return region;
        }

        log.info("Unknown region name: " + name);
        return null;
    }
}
