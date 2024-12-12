package darkoverload.itzip.feature.resume.entity;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.ProfileInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class ProfileInfoEntity {
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name="public_on_off", nullable = false)
    private PublicOnOff publicOnOff;

    @Column(length=50)
    private String subject;

    @Column(length=50)
    private String phone;

    @Column(length=5000)
    private String introduction;

    @Builder
    public ProfileInfoEntity(String email, PublicOnOff publicOnOff, String subject, String phone, String introduction) {
        this.email = email;
        this.publicOnOff = publicOnOff;
        this.subject = subject;
        this.phone = phone;
        this.introduction = introduction;
    }



    public ProfileInfo convertToDomain() {
        return ProfileInfo.builder()
                .email(this.email)
                .introduction(this.introduction)
                .publicOnOff(this.publicOnOff)
                .subject(this.subject)
                .phone(this.phone)
                .build();
    }
}
