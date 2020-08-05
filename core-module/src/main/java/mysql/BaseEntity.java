package mysql;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    private long createdAt;

    @LastModifiedDate
    private long lastModifiedAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public long getLastModifiedAt() {
        return lastModifiedAt;
    }
}
