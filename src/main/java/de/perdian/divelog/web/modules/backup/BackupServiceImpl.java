package de.perdian.divelog.web.modules.backup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.modules.backup.BackupImportItem.ConsolidationResult;
import de.perdian.divelog.web.support.authentication.DiveLogUser;

@Service
class BackupServiceImpl implements BackupService {

    private DiveLogUser currentUser = null;
    private DiveRepository diveRepository = null;
    private ObjectMapper objectMapper = null;

    @Override
    @Transactional
    public List<BackupImportItem> executeImport(MultipartFile inputFile) throws IOException {
        Backup backup = this.extractBackupFromFile(inputFile);
        List<Dive> allDives = this.getDiveRepository().findAll(this.getCurrentUser().specification(Dive.class));
        List<BackupImportItem> importedItems = new ArrayList<>(backup.getDives().size());
        for (Dive importedDive : backup.getDives()) {

            Dive databaseDive = allDives.stream()
                .filter(dive -> Objects.equals(dive.getStart().getDate(), importedDive.getStart().getDate()))
                .filter(dive -> Objects.equals(dive.getStart().getTime(), importedDive.getStart().getTime()))
                .findFirst()
                .orElse(null);

            BackupImportItem importItem = new BackupImportItem(importedDive);
            if (databaseDive == null) {
                databaseDive = new Dive();
                databaseDive.setUser(this.getCurrentUser().getUserEntity());
                importItem.setConsolidationResult(ConsolidationResult.INSERT);
            } else {
                importItem.setConsolidationResult(ConsolidationResult.UPDATE);
            }
            this.synchronizeDives(importedDive, databaseDive);
            importItem.setDatabaseDive(this.getDiveRepository().save(databaseDive));
            importedItems.add(importItem);

        }
        return importedItems;
    }

    private void synchronizeDives(Dive sourceDive, Dive targetDive) {
        targetDive.ensureLogbookImage().setJpegBytes(sourceDive.ensureLogbookImage().getJpegBytes());
        targetDive.setAir(sourceDive.getAir());
        targetDive.setBottomTimeMinutes(sourceDive.getBottomTimeMinutes());
        targetDive.setBuddy(sourceDive.getBuddy());
        targetDive.setComments(sourceDive.getComments());
        targetDive.setEnd(sourceDive.getEnd());
        targetDive.setEnvironment(sourceDive.getEnvironment());
        targetDive.setEquipment(sourceDive.getEquipment());
        targetDive.setMaxDepth(sourceDive.getMaxDepth());
        targetDive.setOrganizer(sourceDive.getOrganizer());
        targetDive.setPadiStatistics(sourceDive.getPadiStatistics());
        targetDive.setSpot(sourceDive.getSpot());
        targetDive.setStart(sourceDive.getStart());
        targetDive.setTotalTimeMinutes(sourceDive.getTotalTimeMinutes());
    }

    private Backup extractBackupFromFile(MultipartFile inputFile) throws IOException {
        try (InputStream inputStream = inputFile.getInputStream()) {
            InputStream internalStream = inputFile.getOriginalFilename().endsWith(".gz") ? new GZIPInputStream(inputStream) : inputStream;
            Reader internalReader = new InputStreamReader(internalStream, StandardCharsets.UTF_8);
            return this.getObjectMapper().readValue(internalReader, Backup.class);
        }
    }

    @Override
    @Transactional
    public Backup createBackup() {

        Specification<Dive> diveSpecification = this.getCurrentUser().specification(Dive.class);
        Sort diveSort = Sort.by(Order.desc("start.date"), Order.desc("start.time"));
        List<Dive> diveList = this.getDiveRepository().findAll(diveSpecification, diveSort);

        BackupMetadata backupMetadata = new BackupMetadata();
        backupMetadata.setCreatedAt(Instant.now());
        backupMetadata.setUsername(this.getCurrentUser().getUserEntity().getName());
        Backup backup = new Backup();
        backup.setDives(diveList);
        backup.setMetadata(backupMetadata);
        return backup;

    }

    DiveLogUser getCurrentUser() {
        return this.currentUser;
    }
    @Autowired
    void setCurrentUser(DiveLogUser currentUser) {
        this.currentUser = currentUser;
    }

    DiveRepository getDiveRepository() {
        return this.diveRepository;
    }
    @Autowired
    void setDiveRepository(DiveRepository diveRepository) {
        this.diveRepository = diveRepository;
    }

    ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
