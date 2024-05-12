package dz.elit.pfe.administration.controller;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.administration.service.AdminUniteOrganisationnelleFacade;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.util.MyUtil;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chekor.samir
 */
@Named
@ViewScoped
public class ListUniteOrgController implements Serializable {

    @Inject
    private AdminUniteOrganisationnelleFacade societeFacade;

    private @Getter
    @Setter
    AdminUniteOrganisationnelle societe;
    private @Getter
    @Setter
    AdminUtilisateur adminUtilisateur;
    @Inject
    private @Getter
    @Setter
    MySessionController mySessionController;
    private @Getter
    @Setter
    List<AdminUniteOrganisationnelle> listUniteOrgs;

    public ListUniteOrgController() {
    }

    @PostConstruct
    protected void initController() {
       listUniteOrgs = societeFacade.findAll();

    }

    public void remove(AdminUniteOrganisationnelle uniteOrganisationnelle) {
        try {
            if (!uniteOrganisationnelle.getAdminUniteOrganisationnelleList().isEmpty()) {
                MyUtil.addWarnMessage(MyUtil.getBundleCommun("msg_vous_pouvez_pas_supprimer_cette_unite"));
            } else {
                if (uniteOrganisationnelle.getUniteParent() != null) {
                    uniteOrganisationnelle.getUniteParent().getAdminUniteOrganisationnelleList().remove(uniteOrganisationnelle);
                    societeFacade.edit(uniteOrganisationnelle.getUniteParent());
                }
                societeFacade.remove(uniteOrganisationnelle);
                adminUtilisateur = new AdminUtilisateur();
                adminUtilisateur = mySessionController.getUtilisateurCourant();
                listUniteOrgs = societeFacade.findAllOrderByTrieAsc();
                MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Unité organisationnelle  supprimé");
            }

        } catch (Exception e) {
            e.printStackTrace();
            MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
        }
    }
}
