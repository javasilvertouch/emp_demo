
package com.example.demo.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.service.ReportService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReportServiceImpl implements ReportService {

	// Autowired DataSource to connect to the database
	
	@Autowired
    private DataSource dataSource;

	/**
	 * Generates a report based on the provided report name and parameters.
	 *
	 ** @param parameters a map of parameters to pass to the report
	 * @return a byte array containing the generated PDF report
	 * @throws Exception if an error occurs during report generation
	 */
    public byte[] generateReport(Map<String, Object> parameters) throws Exception {
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
