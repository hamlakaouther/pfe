
package dz.elit.pfe.administration.controller;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.service.AdminUniteOrganisationnelleFacade;
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
 * 
 */
@Named
@ViewScoped
public class AddUniteOrgController  implements Serializable {

    @Inject
    private AdminUniteOrganisationnelleFacade societeFacade;

    private @Getter @Setter AdminUniteOrganisationnelle societe;
    private @Getter @Setter AdminUniteOrganisationnelle societeSelected;
    
    private @Getter @Setter List<AdminUniteOrganisationnelle> listUniteOrgs;

    public AddUniteOrgController() {
    }

    @PostConstruct
    protected void initController() {
        societe = new AdminUniteOrganisationnelle();
        societeSelected = new AdminUniteOrganisationnelle();
        listUniteOrgs = societeFacade.findAllOrderByTrieAsc();
    }

    public void create() {
        try {
            if (societeSelected != null && societeSelected.getId() != null) {
                societe.setUniteParent(societeSelected);
            }
            societe.setAdminUniteOrganisationnelleCreateur(societe);
            societe.setNiveau((societe.getUniteParent()==null || societe.getUniteParent().getId()==null) ? 1 : (societe.getUniteParent().getNiveau()+1));       
            societeFacade.create(societe);
            if (societeSelected != null && societeSelected.getId() != null) {
                societeSelected.getAdminUniteOrganisationnelleList().add(societe);
                societeFacade.edit(societeSelected);
            }
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Unité organisationnelle crée avec succè");
        } catch (Exception ex) {
            ex.printStackTrace();
            MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
        }
    }
}
