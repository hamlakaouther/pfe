/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Bordeuraux.controller;

import dz.elit.pfe.Bordeuraux.service.BordereauAgenceFacade;
import dz.elit.pfe.Bordeuraux.entity.BordereauAgence;
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
public class ListBordereauAgenceController implements Serializable {
    @Inject
    private BordereauAgenceFacade bordereauAgenceFacade;
    
    @Inject
    private @Getter @Setter MySessionController mySessionController;
    
    @Inject
    private @Getter @Setter Imprimer ctrImprimer;
    
    private @Getter @Setter List<BordereauAgence> listBordereauxAgence;
    private @Getter @Setter List<BordereauAgence> selectedBordereauxAgence;
    private @Getter @Setter BordereauAgence selectedBordereauAgence;
    
    public ListBordereauAgenceController() {
    }
    
    @PostConstruct
    protected void initController() {
        findList();
        this.selectedBordereauxAgence = new ArrayList<>();
    }
    
    private void findList() {
        rechercher();
    }
    
    public void rechercher() {
        listBordereauxAgence = bordereauAgenceFacade.findAll();
        if (listBordereauxAgence.isEmpty()) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }
    
    public void addBordereauAgence() {
        this.selectedBordereauAgence = new BordereauAgence();
    }
    
    public void saveBordereauAgence() {
        try {
            if (selectedBordereauAgence.getId() == null) {
                bordereauAgenceFacade.createBordereauAgence(selectedBordereauAgence);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauAgence ajouté"));
            } else {
                bordereauAgenceFacade.editBordereauAgence(selectedBordereauAgence);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauAgence mis à jour"));
            }
            PrimeFaces.current().executeScript("PF('manageBordereauAgenceDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-BordereauAgence");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
        }
    }
    
    public void deleteBordereauAgence() {
        if (selectedBordereauAgence != null) {
            try {
                bordereauAgenceFacade.remove(selectedBordereauAgence);
                listBordereauxAgence.remove(selectedBordereauAgence);
                selectedBordereauxAgence.remove(selectedBordereauAgence);
                selectedBordereauAgence = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauAgence supprimé"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-BordereauAgence");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun BordereauAgence sélectionné", null));
        }
    }
    
    public String getDeleteButtonMessage() {
        if (hasSelectedBordereauxAgence()) {
            int size = this.selectedBordereauxAgence.size();
            return size > 1 ? size + " BordereauxAgence sélectionnés" : "1 BordereauAgence sélectionné";
        }
        return "Delete";
    }
    
    public boolean hasSelectedBordereauxAgence() {
        return this.selectedBordereauxAgence != null && !this.selectedBordereauxAgence.isEmpty();
    }
    
    public void deleteSelectedBordereauxAgence() {
        if (selectedBordereauxAgence != null && !selectedBordereauxAgence.isEmpty()) {
            try {
                for (BordereauAgence bordereauAgence : selectedBordereauxAgence) {
                    bordereauAgenceFacade.remove(bordereauAgence);
                }
                listBordereauxAgence.removeAll(selectedBordereauxAgence);
                selectedBordereauxAgence = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauxAgence supprimés"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-BordereauAgence");
                PrimeFaces.current().executeScript("PF('dtBordereauAgence').clearFilters()");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun BordereauAgence sélectionné", null));
        }
    }
}
