package darkoverload.itzip.global.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * create, modify date 추상 클래스
 */
@Getter
public abstract class MongoAuditingFields {

    /**
     * 다큐먼트 생성 시 create_date 자동 생성 및 수정 불가
     */
    @CreatedDate
    @Field("create_date")
    protected LocalDateTime createDate;

    /**
     * 다큐먼트 수정 시 modify_date 자동 업데이트
     */
    @LastModifiedDate
    @Field("modify_date")
    protected LocalDateTime modifyDate;

}
