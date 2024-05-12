/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.controller;

import dz.elit.pfe.commun.reporting.engine.Reporting;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author hu
 */
@Named
@ViewScoped
public class Imprimer implements Serializable {
    private String methodeImp;

    private static InputStream rapport;
    private static Map parametres;
    private static JRBeanCollectionDataSource data;

    public Imprimer() {
    }

    @PostConstruct
    public void initParam() {
        methodeImp = "1";
    }

    public InputStream getRapport() {
        return rapport;
    }

    public void setRapport(InputStream rapport) {

        System.out.println("rapport -----s-------->= " + rapport);

        this.rapport = rapport;
    }

    public Map getParametres() {
        return parametres;
    }

    public void setParametres(Map parametres) {

        System.out.println("parametres -----s--------------->= " + parametres);

        this.parametres = parametres;
    }

    public JRBeanCollectionDataSource getData() {
        return data;
    }

    public void setData(JRBeanCollectionDataSource data) {

        System.out.println("data ------s--------------->= " + data);
        this.data = data;
    }

    public void downloadEtat() throws JRException, FileNotFoundException {

        System.out.println("rapport ------------->= " + rapport);

        System.out.println("parametres -------------------->= " + parametres);
        System.out.println("data --------------------->= " + data);

        switch (methodeImp) {
            case "1":
                Reporting.downloadReportPdf(rapport, data, parametres);
                ;
                break;
            case "2":
                Reporting.downloadReportExel(rapport, data, parametres);
                ;
                break;
            case "3":
                Reporting.downloadReportExcelx(rapport, data, parametres);
                ;
                break;
            case "4":
                Reporting.downloadReportCsv(rapport, data, parametres);
                ;
                break;

        }

    }

    public String getMethodeImp() {
        return methodeImp;
    }

    public void setMethodeImp(String methodeImp) {
        this.methodeImp = methodeImp;
    }

}
