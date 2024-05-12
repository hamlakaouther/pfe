package dz.elit.pfe.administration.controller;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.administration.service.AdminUtilisateurFacade;
import dz.elit.pfe.commun.controller.MySessionController;
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
public class EditUtilisateurController implements Serializable {

    @Inject
    private AdminUtilisateurFacade utilisateurFacade;

    @Inject
    private @Getter
    @Setter
    MySessionController sessionController;
    private @Getter
    @Setter
    AdminUtilisateur utilisateur;
    private @Getter
    @Setter
    int etatCompte;
    private @Getter
    @Setter
    Date dateDuJour;
    private @Getter
    @Setter
    String ancienPwd;
    private @Getter
    @Setter
    String nouveauPwd;
    private @Getter
    @Setter
    String confirmPwd;
    private @Getter
    @Setter
    List<AdminUniteOrganisationnelle> listUniteOrganisationnelle;
    private @Getter
    @Setter
    AdminUniteOrganisationnelle uniteOrganisationnelleSelected;
    private @Getter
    @Setter
    boolean disabled = false;

    private @Getter
    @Setter
    AdminUtilisateur utilisateurConnecte;

    public EditUtilisateurController() {
    }

    @PostConstruct
    protected void initController() {
        dateDuJour = new Date();
        initEditUtilisateur();

        String compte = MyUtil.getRequestParameter("compte");
        if (compte != null) {
            utilisateur = sessionController.getUtilisateurCourant();
            if (utilisateur.getActive() && utilisateur.getDateExpiration() == null) {
                etatCompte = 1;
            } else if (utilisateur.getActive()) {
                etatCompte = 2;
            } else {
                etatCompte = 3;
            }
        }

        String id = MyUtil.getRequestParameter("id");
        if (id != null) {
            utilisateur = utilisateurFacade.find(Integer.parseInt(id));
            if (utilisateur.getActive() && utilisateur.getDateExpiration() == null) {
                etatCompte = 1;
            } else if (utilisateur.getActive()) {
                etatCompte = 2;
            } else {
                etatCompte = 3;
            }
            uniteOrganisationnelleSelected = utilisateur.getAdminUniteOrganisationnelle();
        }

        utilisateurConnecte = sessionController.getUtilisateurCourant();

    }

    private boolean isVerifier() {
        if (utilisateur.getDateActivation() != null && utilisateur.getDateExpiration() != null) {
            if (utilisateur.getDateActivation().after(utilisateur.getDateExpiration())) {
                MyUtil.addWarnMessage(MyUtil.getBundleAdmin("msg_date_activation_doit_superieure_date_expiration"));//"La date d'activation doit être superieure a la date d'expiration ");
                return false;
            }
        }
        return true;
    }

    public void editForPrint() {
        try {

            utilisateurFacade.edit(utilisateurConnecte);
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));

        } catch (Exception ex) {
            ex.printStackTrace();
            MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu 
        }
    }

    public void edit() {
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

                AdminUniteOrganisationnelle userUniteBeforEdit = utilisateur.getAdminUniteOrganisationnelle();

                if (uniteOrganisationnelleSelected != null && uniteOrganisationnelleSelected.getId() != null) {
                    utilisateur.setAdminUniteOrganisationnelle(uniteOrganisationnelleSelected);
                    utilisateur.setAdminUniteOrganisationnelleCreateur(uniteOrganisationnelleSelected);
                }
                utilisateur.setLogin(utilisateur.getNom().toLowerCase() + "." + utilisateur.getPrenom().toLowerCase());
                utilisateurFacade.editUtilisateur(utilisateur);
                setDisabled(true);
                MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Utilisateur modifié avec succè");
            } catch (Exception ex) {
                ex.printStackTrace();
                MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
            }
        }
    }

    public boolean modifierMotPasse(String ancienPass, String nouvPass, String confirmPass) {
        boolean retour = false;
        if (nouveauPwd != null && !nouveauPwd.equals("") && confirmPwd.equals(nouveauPwd)) {
            if (ancienPwd != null && !ancienPwd.equals("") && utilisateur.getPwd().equals(StaticUtil.getEncryptPassword(ancienPwd))) {
                if (!nouveauPwd.equals(ancienPass)) {
                    if (!StaticUtil.getEncryptPassword(nouveauPwd).equals(StaticUtil.getDefaultEncryptPassword())) {
                        if (isMotPasseConforme(nouveauPwd)) {
                            utilisateur.setPwd(StaticUtil.getEncryptPassword(nouveauPwd));
                            retour = true;
                        }
                    } else {
                        MyUtil.addErrorMessage("Le nouveau mot de passe doit être différent à le mot de passe par défaut");

                    }
                } else {
                    MyUtil.addErrorMessage("Le nouveau mot de passe doit être différent à l'ancien mot de passe");
                }
            } else {
                MyUtil.addErrorMessage("Ancien mot de passe érroné");
            }
        } else {
            MyUtil.addErrorMessage("Les deux nouveaux mots de passe saisis doivent être identique");
        }
        return retour;
    }

    public String editPwdUtilisateur() {
        utilisateur = sessionController.getUtilisateurCourant();
        if (!nouveauPwd.equals(confirmPwd)) {
            MyUtil.addErrorMessage(MyUtil.getBundleAdmin("msg_confirmer_votre_pwd"));//"Veuillez confirmer votre mot de passe ");
            return null;
        } else if (!StaticUtil.getEncryptPassword(ancienPwd).equals(utilisateur.getPwd())) {
            MyUtil.addErrorMessage(MyUtil.getBundleAdmin("msg_ancien_pwd_incorrect  "));
            return null;
        } else if (ancienPwd.equals(nouveauPwd)) {
            MyUtil.addErrorMessage(MyUtil.getBundleAdmin("msg_nouveau_pwd_doit_différent_ancien"));
            return null;
        } else if (isMotPasseConforme(nouveauPwd)) {
            utilisateur.setPwd(StaticUtil.getEncryptPassword(nouveauPwd));
            utilisateur.setIsPwdChanged(true);
            try {
                utilisateurFacade.edit(utilisateur);
                return sessionController.logout();
            } catch (Exception ex) {
                ex.printStackTrace();
                String dst = null;
                MyUtil.addErrorMessage(dst == null ? MyUtil.getBundleCommun("msg_erreur_inconu") : dst);//Erreur inconu
                return null;
            }
        }
        return null;
    }

    private void initEditUtilisateur() {
        utilisateurConnecte = new AdminUtilisateur();
        utilisateur = new AdminUtilisateur();
        utilisateur.setDateActivation(new Date());
        etatCompte = 1;
    }

    private boolean isMotPasseConforme(String motPasse) {
        if (!motPasse.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{8,}$")) {
            MyUtil.addErrorMessage("Le mot de passe ne respecte pas la politique de sécurité, qui est :"
                    + "<br/> - Une longueur minimum  de 08 caractères."
                    + "<br/> - Contient au moins un caractère Majuscule."
                    + "<br/> - Contient au moins un caractère Minuscule."
                    + "<br/> -Contient au moins un caractère chiffre."
                    + "<br/> -Contient au moins un caractère spécial. "
                    + "<br/>*** Exemple: Elit.2016");
            return false;
        }
        return true;

    }
}
