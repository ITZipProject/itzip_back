package darkoverload.itzip.resume.domain;

import darkoverload.itzip.resume.code.EstType;
import darkoverload.itzip.resume.code.RegionType;
import darkoverload.itzip.resume.code.SchoolType;
import darkoverload.itzip.resume.entity.SchoolEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class School {
    private String schoolName;

    private String gubun;

    private SchoolType schoolType;

    private String address;

    private String campusName;

    private EstType estType;

    private RegionType region;

    public SchoolEntity convertToEntity(){
        return SchoolEntity.builder()
                .schoolName(this.schoolName)
                .gubun(this.gubun)
                .schoolType(this.schoolType)
                .address(this.address)
                .campusName(this.campusName)
                .estType(this.estType)
                .region(this.region)
                .build();
    }
}
