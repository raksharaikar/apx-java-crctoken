package com.crc.demo;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/firmware")
public class FirmwareController {

    private final Crc32Service crc32Service;

    public FirmwareController(Crc32Service crc32Service) {
        this.crc32Service = crc32Service;
    }

    // Endpoint to download the latest firmware
 
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> downloadLatestFirmware() {
        try {
            // Get the latest firmware file
            File latestFirmware = crc32Service.getLatestFirmware();

            if (latestFirmware == null || !latestFirmware.exists()) {
                System.err.println("Error: Latest firmware file is not found or is inaccessible.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            // Log the file that is being served
            System.out.println("Serving firmware file: " + latestFirmware.getAbsolutePath());

             Path path = Paths.get(latestFirmware.getAbsolutePath());
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
    HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
    header.add("Pragma", "no-cache");
    header.add("Expires", "0");
    return ResponseEntity.ok()
            .headers(header)
            .contentLength(latestFirmware.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
        } catch (IOException e) {
            System.err.println("Error reading the firmware file: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Endpoint to list available firmware versions (file names)
    @GetMapping("/versions")
    public List<String> getFirmwareVersions() {
        return crc32Service.getAvailableFirmwareVersions();
    }
}

