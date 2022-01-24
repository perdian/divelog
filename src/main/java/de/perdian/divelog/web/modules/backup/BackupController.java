package de.perdian.divelog.web.modules.backup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUser;

@Controller
@RequestMapping("/backup")
public class BackupController {

    private DiveLogUser currentUser = null;
    private DiveRepository diveRepository = null;
    private ObjectMapper objectMapper = null;

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<?> doBackup() throws IOException {

        Specification<Dive> diveSpecification = this.getCurrentUser().specification(Dive.class);
        Sort diveSort = Sort.by(Order.desc("start.date"), Order.desc("start.time"));
        List<Dive> diveList = this.getDiveRepository().findAll(diveSpecification, diveSort);

        BackupMetadata backupMetadata = new BackupMetadata();
        backupMetadata.setCreatedAt(Instant.now());
        backupMetadata.setUsername(this.getCurrentUser().getUserEntity().getName());
        Backup backup = new Backup();
        backup.setDives(diveList);
        backup.setMetadata(backupMetadata);

        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipStream = new GZIPOutputStream(resultStream);
        Writer jsonWriter = new OutputStreamWriter(gzipStream, StandardCharsets.UTF_8);

        ObjectWriter objectWriter = this.getObjectMapper().writer().withDefaultPrettyPrinter();
        objectWriter.writeValue(jsonWriter, backup);
        byte[] resultBytes = resultStream.toByteArray();

        return ResponseEntity
            .ok()
            .header("Cache-Control", "no-cache")
            .header("Content-Disposition", "attachment; filename=divelog-backup-" + DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss").format(Instant.now().atZone(ZoneId.of("UTC"))) + ".json.gz")
            .contentLength(resultBytes.length)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .eTag(DigestUtils.md5DigestAsHex(resultBytes))
            .body(resultBytes);

    }

    @GetMapping("/import")
    public String doImportGet() {
        return "backup/import";
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
