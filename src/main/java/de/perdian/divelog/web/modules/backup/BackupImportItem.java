package de.perdian.divelog.web.modules.backup;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.divelog.model.entities.Dive;

public class BackupImportItem implements Serializable {

    static final long serialVersionUID = 1L;

    private Dive importedDive = null;
    private Dive databaseDive = null;
    private ConsolidationResult consolidationResult = null;

    BackupImportItem(Dive importedDive) {
        this.setImportedDive(importedDive);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public static enum ConsolidationResult {

        INSERT,
        UPDATE;

    }

    public Dive getImportedDive() {
        return this.importedDive;
    }
    private void setImportedDive(Dive importedDive) {
        this.importedDive = importedDive;
    }

    public Dive getDatabaseDive() {
        return this.databaseDive;
    }
    void setDatabaseDive(Dive databaseDive) {
        this.databaseDive = databaseDive;
    }

    public ConsolidationResult getConsolidationResult() {
        return this.consolidationResult;
    }
    void setConsolidationResult(ConsolidationResult consolidationResult) {
        this.consolidationResult = consolidationResult;
    }

}
