package com.sttl.employees.service;

import java.util.Map;


public interface ReportService {   
    public byte[] generateReport(Map<String, Object> parameters);
}

