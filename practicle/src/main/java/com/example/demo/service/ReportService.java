package com.example.demo.service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;


@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public byte[] generateReport(String reportName, Map<String, Object> parameters) throws Exception {
        // Load JRXML
        InputStream reportStream = getClass().getResourceAsStream("/reports/" + "EmployeeDepartmentwise" + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Connect to database
        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

            // Export to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}

