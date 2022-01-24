package de.perdian.divelog.web.modules.backup;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

interface BackupService {

    List<BackupImportItem> executeImport(MultipartFile inputFile) throws IOException;

    Backup createBackup();

}
