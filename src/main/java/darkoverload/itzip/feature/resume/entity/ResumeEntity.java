package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.util.StringListConverter;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="resumes")
public class ResumeEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, updatable = false)
    private Long userId;

    private String email;

    private String imageUrl;

    @Column(length=50)
    private String subject;

    @Column(length=50)
    private String phone;

    @Column(length=5000)
    private String introduction;

    private String address;

    @Convert(converter = StringListConverter.class)
    private List<String> links;

    private String combination;

    @Enumerated(EnumType.STRING)
    @Column(name="public_on_off", nullable = false)
    private PublicOnOff publicOnOff;


}
