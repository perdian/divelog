package de.perdian.divelog.web.modules.backup;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
@RequestMapping("/backup")
public class BackupController {

    private BackupService backupService = null;
    private ObjectMapper objectMapper = null;

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<?> doBackup() throws IOException {

        Backup backup = this.getBackupService().createBackup();
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

    @PostMapping("/import")
    public String doImportPost(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (file == null || file.getSize() <= 0) {
                throw new FileNotFoundException("No data file was uploaded!");
            } else {
                List<BackupImportItem> importItems = this.getBackupService().executeImport(file);
                redirectAttributes.addFlashAttribute("importItems", importItems);
                return "redirect:/backup/import/completed";
            }
        } catch (Exception e) {
            model.addAttribute("importException", e);
            return this.doImportGet();
        }
    }

    @GetMapping("/import/completed")
    public String doImportCompleted() {
        return "backup/import-completed";
    }

    BackupService getBackupService() {
        return this.backupService;
    }
    @Autowired
    void setBackupService(BackupService backupService) {
        this.backupService = backupService;
    }

    ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
