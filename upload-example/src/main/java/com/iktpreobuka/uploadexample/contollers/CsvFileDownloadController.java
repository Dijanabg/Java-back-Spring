package com.iktpreobuka.uploadexample.contollers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.uploadexample.services.CsvFileService;

@RestController
public class CsvFileDownloadController {
	@Autowired
    private CsvFileService csvFileService;

    @GetMapping("/download/users.csv")
    public ResponseEntity<Resource> downloadCsv() throws Exception {
        String fileName = csvFileService.createCsvFile();
        Path path = Paths.get(fileName);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
