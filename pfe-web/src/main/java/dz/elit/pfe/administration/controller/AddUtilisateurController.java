package dz.elit.pfe.administration.controller;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.administration.service.AdminUtilisateurFacade;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.exception.MyException;
import dz.elit.pfe.commun.util.MyUtil;
import dz.elit.pfe.commun.util.StaticUtil;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author leghettas.rabah
 */
@Named
@ViewScoped
public class AddUtilisateurController  implements Serializable {

    @Inject
    private AdminUtilisateurFacade utilisateurFacade;

    private @Getter @Setter AdminUtilisateur utilisateur; 
    private @Getter @Setter List<AdminUniteOrganisationnelle> listUniteOrganisationnelle;
    private @Getter @Setter AdminUniteOrganisationnelle uniteOrganisationnelleSelected;
    private @Getter @Setter int etatCompte;
    private @Getter @Setter Date dateDuJour;
    private @Getter @Setter boolean disabled = false;
    
    @Inject
    private @Getter @Setter MySessionController mySessionController;

    public AddUtilisateurController() {
    }

    @PostConstruct
    protected void initController() {
        dateDuJour = new Date();
        initAddUtilisateur();
    }

    private boolean isVerifier() {
        if (utilisateur.getDateActivation() != null && utilisateur.getDateExpiration() != null) {
            if (utilisateur.getDateActivation().after(utilisateur.getDateExpiration())) {
                MyUtil.addWarnMessage(MyUtil.getBundleAdmin("msg_date_activation_doit_superieure_date_expiration"));
                return false;
            }
        }
        if (uniteOrganisationnelleSelected == null || uniteOrganisationnelleSelected.getId() == null) {
            MyUtil.addWarnMessage("Il est impossible de créer un utilisateur sans unité organisationnel");
            return false;
        }
        return true;
    }

    public void create() {
        if (isVerifier()) {
            try {
                switch (etatCompte) {
                    case 1:
                        utilisateur.setActive(true);
                        utilisateur.setDateExpiration(null);
                        break;
                    case 2:
                        utilisateur.setActive(true);
                        break;
                    case 3:
                        utilisateur.setActive(false);
                        utilisateur.setDateExpiration(null);
                        break;
                }
                utilisateur.setPwd(StaticUtil.getDefaultEncryptPassword());
                utilisateur.setLogin(utilisateur.getNom().toLowerCase() + "." + utilisateur.getPrenom().toLowerCase());
                utilisateur.setAdminUniteOrganisationnelle(uniteOrganisationnelleSelected);

                utilisateurFacade.createUtilisateur(utilisateur);
                setDisabled(true);
                
                MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Utilisateur modifié avec succè");
            } catch (MyException ex) {
                MyUtil.addErrorMessage(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
            }
        }
    }

    public void newUtilisateur() {
        initAddUtilisateur();
        setDisabled(false);
    }

    private void initAddUtilisateur() {
        utilisateur = new AdminUtilisateur();
        utilisateur.setDateActivation(new Date());
        etatCompte = 1;
        uniteOrganisationnelleSelected = new AdminUniteOrganisationnelle();
    }
}
