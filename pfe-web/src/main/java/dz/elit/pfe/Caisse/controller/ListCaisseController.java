package dz.elit.pfe.Caisse.controller;

import dz.elit.pfe.Caisse.entity.Caisse;
import dz.elit.pfe.Caisse.service.CaisseFacade;
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
public class ListCaisseController implements Serializable {
    @Inject
    private CaisseFacade caisseFacade;
    
    @Inject
    private @Getter @Setter MySessionController mySessionController;
    
    @Inject
    private @Getter @Setter Imprimer ctrImprimer;
    
    private @Getter @Setter List<Caisse> listCaisses;
    private @Getter @Setter List<Caisse> selectedCaisses;
    private @Getter @Setter Caisse selectedCaisse;
    
    public ListCaisseController() {
    }
    
    @PostConstruct
    protected void initController() {
        findList();
        this.selectedCaisses = new ArrayList<>();
    }
    
    private void findList() {
        rechercher();
    }
    
    public void rechercher() {
        listCaisses = caisseFacade.findAll();
        if (listCaisses.isEmpty()) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }
    
    public void addCaisse() {
        this.selectedCaisse = new Caisse();
    }
    
    public void saveCaisse() {
        try {
            if (selectedCaisse.getId() == null) {
                caisseFacade.createCaisse(selectedCaisse);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caisse ajoutée"));
            } else {
                caisseFacade.editCaisse(selectedCaisse);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caisse mise à jour"));
            }
            PrimeFaces.current().executeScript("PF('manageCaisseDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-Caisse");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
        }
    }
    
    public void deleteCaisse() {
        if (selectedCaisse != null) {
            try {
                caisseFacade.remove(selectedCaisse);
                listCaisses.remove(selectedCaisse);
                selectedCaisses.remove(selectedCaisse);
                selectedCaisse = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caisse supprimée"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-Caisse");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucune caisse sélectionnée", null));
        }
    }
    
    public String getDeleteButtonMessage() {
        if (hasSelectedCaisses()) {
            int size = this.selectedCaisses.size();
            return size > 1 ? size + " Caisses sélectionnées" : "1 Caisse sélectionnée";
        }
        return "Delete";
    }
    
    public boolean hasSelectedCaisses() {
        return this.selectedCaisses != null && !this.selectedCaisses.isEmpty();
    }
    
    public void deleteSelectedCaisses() {
        if (selectedCaisses != null && !selectedCaisses.isEmpty()) {
            try {
                for (Caisse caisse : selectedCaisses) {
                    caisseFacade.remove(caisse);
                }
                listCaisses.removeAll(selectedCaisses);
                selectedCaisses = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Caisses supprimées"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-Caisse");
                PrimeFaces.current().executeScript("PF('dtCaisse').clearFilters()");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucune caisse sélectionnée", null));
        }
    }
}

