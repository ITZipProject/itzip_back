package darkoverload.itzip.global.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * create, modify date 추상 클래스
 */
@ToString
@Getter
public abstract class MongoAuditingFields {

    /**
     * create_date 엔티티 생성 시 자동 생성, 수정 불가
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    protected LocalDateTime createDate;

    /**
     * modify_date 엔티티 수정 시 자동 업데이트
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    protected LocalDateTime modifyDate;
}