/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Bordeuraux.controller;

import dz.elit.pfe.Bordeuraux.service.BordereauDirectionFacade;
import dz.elit.pfe.Bordeuraux.entity.BordereauDirection;
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
public class ListBordereauDirectionController implements Serializable {
    @Inject
    private BordereauDirectionFacade bordereauDirectionFacade;
    
    @Inject
    private @Getter @Setter MySessionController mySessionController;
    
    @Inject
    private @Getter @Setter Imprimer ctrImprimer;
    
    private @Getter @Setter List<BordereauDirection> listBordereauxDirection;
    private @Getter @Setter List<BordereauDirection> selectedBordereauxDirection;
    private @Getter @Setter BordereauDirection selectedBordereauDirection;
    
    public ListBordereauDirectionController() {
    }
    
    @PostConstruct
    protected void initController() {
        findList();
        this.selectedBordereauxDirection = new ArrayList<>();
    }
    
    private void findList() {
        rechercher();
    }
    
    public void rechercher() {
        listBordereauxDirection = bordereauDirectionFacade.findAllBordereauDirection();
        if (listBordereauxDirection.isEmpty()) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }
    
    public void addBordereauDirection() {
        this.selectedBordereauDirection = new BordereauDirection();
    }
    
    public void saveBordereauDirection() {
        try {
            if (selectedBordereauDirection.getId() == null) {
                bordereauDirectionFacade.createBordereauDirection(selectedBordereauDirection);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauDirection ajouté"));
            } else {
                bordereauDirectionFacade.editBordereauDirection(selectedBordereauDirection);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauDirection mis à jour"));
            }
            PrimeFaces.current().executeScript("PF('manageBordereauDirectionDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-BordereauDirection");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
        }
    }
    
    public void deleteBordereauDirection() {
        if (selectedBordereauDirection != null) {
            try {
                bordereauDirectionFacade.remove(selectedBordereauDirection);
                listBordereauxDirection.remove(selectedBordereauDirection);
                selectedBordereauxDirection.remove(selectedBordereauDirection);
                selectedBordereauDirection = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauDirection supprimé"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-BordereauDirection");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun BordereauDirection sélectionné", null));
        }
    }
    
    public String getDeleteButtonMessage() {
        if (hasSelectedBordereauxDirection()) {
            int size = this.selectedBordereauxDirection.size();
            return size > 1 ? size + " BordereauxDirection sélectionnés" : "1 BordereauDirection sélectionné";
        }
        return "Delete";
    }
    
    public boolean hasSelectedBordereauxDirection() {
        return this.selectedBordereauxDirection != null && !this.selectedBordereauxDirection.isEmpty();
    }
    
    public void deleteSelectedBordereauxDirection() {
        if (selectedBordereauxDirection != null && !selectedBordereauxDirection.isEmpty()) {
            try {
                for (BordereauDirection bordereauDirection : selectedBordereauxDirection) {
                    bordereauDirectionFacade.remove(bordereauDirection);
                }
                listBordereauxDirection.removeAll(selectedBordereauxDirection);
                selectedBordereauxDirection = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("BordereauxDirection supprimés"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-BordereauDirection");
                PrimeFaces.current().executeScript("PF('dtBordereauDirection').clearFilters()");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun BordereauDirection sélectionné", null));
        }
    }
}
