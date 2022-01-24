package de.perdian.divelog.web.modules.backup;

import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BackupMetadata implements Serializable {

    static final long serialVersionUID = 1L;

    private String username = null;
    private Instant createdAt = null;
    private int version = 1;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

}
