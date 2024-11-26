package com.crc.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/crc32")
public class Crc32Controller {

    @Autowired
    private Crc32Service crc32Service;
@GetMapping("/calculate")
public String calculateCRC32(@RequestParam String filePath) {
    System.out.println("Inside calculateCRC32 method");  // Debugging output
    try {
        System.out.println("Received filePath: " + filePath);  // Debugging output
        int crc = crc32Service.calculateCRC32(filePath);
        return Integer.toHexString(crc);
    } catch (IOException e) {
        System.out.println("Error calculating CRC32: " + e.getMessage());  // Debugging output
        return "Error calculating CRC32: " + e.getMessage();
    }
}

}

