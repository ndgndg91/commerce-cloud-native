package mongo;

import org.joda.time.DateTime;

public class BaseEntity {

    private DateTime createdAt;

    private DateTime lastModifiedAt;

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(DateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
