package dz.elit.pfe.Banque.controller;

import dz.elit.pfe.Banque.entity.Banque;
import dz.elit.pfe.Banque.service.BanqueFacade;
import dz.elit.pfe.commun.controller.Imprimer;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.util.MyUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class ListBanqueController implements Serializable {
    @Inject
    private BanqueFacade banqueFacade;
    
    @Inject
    private @Getter @Setter MySessionController mySessionController;
    
    @Inject
    private @Getter @Setter Imprimer ctrImprimer;
    
    private @Getter @Setter List<Banque> ListBanque;
    private @Getter @Setter List<Banque> selectedBanques;
    private @Getter @Setter Banque selectedBanque;
    
    public ListBanqueController() {
    }
    
    @PostConstruct
    protected void initController() {
        findList();
        this.selectedBanques = new ArrayList<>();
    }
    
    private void findList() {
        rechercher();
    }
    
    public void rechercher() {
        ListBanque = banqueFacade.findAll();
        if (ListBanque.isEmpty()) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }
    
    public void addBanque() {
        this.selectedBanque = new Banque();
    }
    
    public void saveBanque() {
        System.err.println("test save");    
        System.err.println(selectedBanque);
        if (!banqueFacade.isBanqueExists(this.selectedBanque.getId())) {
            this.ListBanque.add(this.selectedBanque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Banque ajoutée"));
        } else if (this.selectedBanque.getId() != null) {
            banqueFacade.editBanque(this.selectedBanque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Banque mise à jour"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le id existe déjà.", null));
        }
        PrimeFaces.current().executeScript("PF('manageBanqueDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Banque");
    }
    
    public void deleteBanque() {
        this.ListBanque.remove(this.selectedBanque);
        this.selectedBanques.remove(this.selectedBanque);
        this.selectedBanque = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Banque supprimée"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Banque");
    }
    
    public String getDeleteButtonMessage() {
        if (hasSelectedBanques()) {
            int size = this.selectedBanques.size();
            return size > 1 ? size + " Banques sélectionnés" : "1 Banque sélectionnée";
        }
        return "Delete";
    }
    
    public boolean hasSelectedBanques() {
        return this.selectedBanques != null && !this.selectedBanques.isEmpty();
    }
    
    public void deleteSelectedBanques() {
        this.ListBanque.removeAll(this.selectedBanques);
        this.selectedBanques = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Banques supprimées"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Banque");
        PrimeFaces.current().executeScript("PF('dtBanque').clearFilters()");
    }
}

