package de.perdian.divelog.web.modules.backup;

import java.io.Serializable;
import java.util.List;

import de.perdian.divelog.model.entities.Dive;

public class Backup implements Serializable {

    static final long serialVersionUID = 1L;

    private BackupMetadata metadata = null;
    private List<Dive> dives = null;

    public BackupMetadata getMetadata() {
        return this.metadata;
    }
    public void setMetadata(BackupMetadata metadata) {
        this.metadata = metadata;
    }

    public List<Dive> getDives() {
        return this.dives;
    }
    public void setDives(List<Dive> dives) {
        this.dives = dives;
    }

}
