package com.crc.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/crc32")
public class Crc32Controller {

    @Autowired
    private Crc32Service crc32Service;
    @PostMapping("/calculate")
    public String calculateCRC32(@RequestParam("file") MultipartFile file) {
        System.out.println("Inside calculateCRC32 method");  // Debugging output
        try {
            System.out.println("Received file: " + file.getOriginalFilename());  // Debugging output
            byte[] fileBytes = file.getBytes();

            System.out.println("Received filePath: " + fileBytes);  // Debugging output
        int crc = crc32Service.calculateCRC32(fileBytes);
        return Integer.toHexString(crc);
        } catch (IOException e) {
            System.out.println("Error calculating CRC32: " + e.getMessage());  // Debugging output
            return "Error calculating CRC32: " + e.getMessage();
        }
    }


}

