package dz.elit.pfe.administration.controller;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.administration.service.AdminUtilisateurFacade;
import dz.elit.pfe.commun.controller.Imprimer;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.reporting.engine.Reporting;
import dz.elit.pfe.commun.util.MyUtil;
import dz.elit.pfe.commun.util.StaticUtil;
import jakarta.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;


/**
 *
 * @author leghettas.rabah
 */
@Named
@ViewScoped
public class ListUtilisateurController implements Serializable {
    @Inject
    private AdminUtilisateurFacade utilisateurFacade;    

    @Inject
    private @Getter @Setter MySessionController mySessionController;
    
    @ManagedProperty(value = "#{imprimer}")
    private @Getter @Setter Imprimer ctrImprimer;
    private @Getter @Setter AdminUtilisateur adminUtilisateur;
    private @Getter @Setter List<AdminUtilisateur> listUtilisateurs;

    //Les variables de recherche
    private @Getter @Setter String nom;
    private @Getter @Setter String prenom;
    private @Getter @Setter String login;
    private @Getter @Setter List<AdminUniteOrganisationnelle> listUniteOrganisationnelle;
    private @Getter @Setter AdminUniteOrganisationnelle uniteOrganisationnelleSelected = new AdminUniteOrganisationnelle();

    public ListUtilisateurController() {

    }

    @PostConstruct
    protected void initController() {
        findList();
    }

    public void remove(AdminUtilisateur utilisateur) {
        String loginCurrent = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        if (loginCurrent.equals(utilisateur.getLogin())) {
            MyUtil.addWarnMessage(MyUtil.getBundleAdmin("msg_vous_pouvez_pas_supprimer_votre_compte"));
        } else {
            try {
                utilisateurFacade.remove(utilisateur);
                MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Utilisateur supprimé");
                findList();
            } catch (Exception ex) {
                ex.printStackTrace();
                MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
            }
        }
    }

    public void initialisePwdUtilisateur(AdminUtilisateur utilisateur) {
        try {
            utilisateur.setPwd(StaticUtil.getDefaultEncryptPassword());
            utilisateur.setIsPwdChanged(false);
            utilisateurFacade.edit(utilisateur);
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Mot de passe réinitialisé avec succés");
        } catch (Exception ex) {
            ex.printStackTrace();
            MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
        }
    }

    public void rechercher() {
        listUtilisateurs = utilisateurFacade.findByNomPrenomLogin(nom, prenom, login, uniteOrganisationnelleSelected);
        // supprimer systeme de la liste des user partout sauf dans le systeme lui meme
        AdminUtilisateur user = utilisateurFacade.findByLogin("systeme.systeme");
//       if(!mySessionController.getUtilisateurCourant().equals(user)){
//        listUtilisateurs.remove(user);
//       } 
       if (listUtilisateurs.isEmpty() || listUtilisateurs.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }

    private void findList() {
        rechercher();
    }

    public String addUtilisateur() {
        adminUtilisateur = new AdminUtilisateur();
        return "addUtilisateur";
    }

    public void download() throws SQLException, IOException {
        String rapportLien = "/dz/elit/harmo/commun/reporting/source/listUtilisateur.jasper";
        InputStream rapport = getClass().getResourceAsStream(rapportLien);
        String SUBREPORT_DIR = getClass().getResource("/dz/elit/harmo/commun/reporting/source/Entete/").getFile();
        String rapportNom = "rapportNom";
        String entreprisFr = "entreprisFr";
        String entreprisAr = "entreprisAr";
        String iSoRapport = "iSoRapport";
        InputStream urlLogo = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/images-login/logo.png");
        Map parametres = new HashMap();
        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listUtilisateurs);
        parametres.put("rapportNom", rapportNom);
        parametres.put("entreprisFr", entreprisFr);
        parametres.put("entreprisAr", entreprisAr);
        parametres.put("iSoRapport", iSoRapport);
        parametres.put("iMgDir", urlLogo);
        parametres.put("SUBREPORT_DIR", SUBREPORT_DIR);
        ctrImprimer.setData(data);
        ctrImprimer.setParametres(parametres);
        ctrImprimer.setRapport(rapport);
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("width", 350);
        options.put("contentHeight", 90);
        options.put("contentWidth", 310);
        PrimeFaces.current().dialog().openDynamic("/pages/commun/download.xhtml", options, null);
    }

    public void telecharger() throws JRException, FileNotFoundException {

        String rapportLien = "/dz/elit/harmo/commun/reporting/source/listUtilisateur.jasper";
        InputStream rapport = getClass().getResourceAsStream(rapportLien);
        String rapportNom = "Test";
        String entreprisFr = "Elit";
        String entreprisAr = "شركة";
        String iSoRapport = "iSoRapport";
        String SUBREPORT_DIR = getClass().getResource("/dz/elit/harmo/commun/reporting/source/Entete/").getFile();
        InputStream urlLogo = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/images-login/logo.png");
        Map parametres = new HashMap();
        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listUtilisateurs);
        parametres.put("rapportNom", rapportNom);
        parametres.put("entreprisFr", entreprisFr);
        parametres.put("entreprisAr", entreprisAr);
        parametres.put("iSoRapport", iSoRapport);
        parametres.put("iMgDir", urlLogo);
        parametres.put("SUBREPORT_DIR", SUBREPORT_DIR);
        Reporting.downloadReportPdf(rapport, data, parametres);
    }
}
