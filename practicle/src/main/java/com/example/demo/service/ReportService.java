package com.example.demo.service;

import java.util.Map;


public interface ReportService {   
    public byte[] generateReport(Map<String, Object> parameters) throws Exception;
}

