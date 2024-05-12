package dz.elit.pfe.commun.reporting.engine;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author GUEFFAF.MESBAH
 */
public class Reporting {

    /**
     * Cette méthode permet de télécharger l'état sous format pdf
     * @param report
     * @param source
     * @param parameters
     */
    public static void downloadReportPdf(InputStream report, JRDataSource source, Map parameters) throws JRException, FileNotFoundException {
        parameters.put("IgnorePagination", false);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            context.responseComplete();

            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            //response.setHeader("Content-Disposition", "inline; filename=" + (parameters.get("rapportNom")).toString().replace(" ", "_") + ".pdf");
            response.setContentType("application/pdf");
            parameters.put("iMgDir", parameters.get("iMgDir"));
            JasperRunManager.runReportToPdfStream(report, servletOutputStream, parameters, source);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reporting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Cette méthode permet de télécharger l'état sous format Excelx
     * @param report
     * @param source
     * @param parameters
     */
    public static void downloadReportExcelx(InputStream report, JRDataSource source, Map parameters)
            throws JRException, FileNotFoundException {
        parameters.put("IgnorePagination", true);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            context.responseComplete();
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "inline; filename=" + (parameters.get("rapportNom")).
                    toString().replace(" ", "_") + ".xlsx");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
            reportConfig.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
            reportConfig.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
            reportConfig.setDetectCellType(Boolean.TRUE);
            exporter.setConfiguration(reportConfig);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            exporter.exportReport();
            byte[] reportBytes = xlsReport.toByteArray();
            response.setContentLength(reportBytes.length);
            response.getOutputStream().write(reportBytes, 0, reportBytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reporting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cette méthode permet de télécharger l'état sous format Csv
     *
     * @param report
     * @param source
     * @param parameters
     */
    public static void downloadReportCsv(InputStream report, JRDataSource source, Map parameters) throws JRException, FileNotFoundException {
        parameters.put("IgnorePagination", true);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            context.responseComplete();
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "inline; filename=" + (parameters.get("rapportNom")).toString().replace(" ", "_") + ".csv");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(xlsReport));
            exporter.exportReport();
            byte[] reportBytes = xlsReport.toByteArray();
            response.setContentLength(reportBytes.length);
            response.getOutputStream().write(reportBytes, 0, reportBytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reporting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cette méthode permet de télécharger l'état sous format Exel
     *
     * @param report
     * @param source
     * @param parameters
     */
    public static void downloadReportExel(InputStream report, JRDataSource source, Map parameters) throws JRException, FileNotFoundException {
        parameters.put("IgnorePagination", true);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            context.responseComplete();
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "inline; filename=" + (parameters.get("rapportNom")).toString().replace(" ", "_") + ".xls");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            SimpleXlsReportConfiguration reportConfig = new SimpleXlsReportConfiguration();
            reportConfig.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
            reportConfig.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
            reportConfig.setDetectCellType(Boolean.TRUE);
            exporter.exportReport();
            byte[] reportBytes = xlsReport.toByteArray();
            response.setContentLength(reportBytes.length);
            response.getOutputStream().write(reportBytes, 0, reportBytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reporting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
