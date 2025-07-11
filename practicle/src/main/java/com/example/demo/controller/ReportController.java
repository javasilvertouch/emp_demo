package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

  
	/**
	 * Handles GET requests to /api/report/download.
	 * Generates a report based on the provided name and parameters.
	 * 
	 * @param name the name of the report
	 * @return a ResponseEntity containing the generated report as a byte array
	 * @throws Exception if an error occurs during report generation
	 */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport(
            @RequestParam(defaultValue = "emp_info") String name
    ) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Employee Report"); 

        byte[] data = reportService.generateReport(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", name + ".pdf");

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}

