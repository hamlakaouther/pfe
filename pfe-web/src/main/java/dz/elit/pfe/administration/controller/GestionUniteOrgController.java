/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.administration.controller;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.service.AdminUniteOrganisationnelleFacade;
import dz.elit.pfe.commun.controller.Imprimer;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.util.MyUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author xps
 */
@Named
@ViewScoped
public class GestionUniteOrgController implements Serializable {

    @Inject
    private AdminUniteOrganisationnelleFacade uniteOrganisationnelleFacade;

    @Inject
    private @Getter
    @Setter
    MySessionController mySessionController;

    @ManagedProperty(value = "#{imprimer}")
    private @Getter
    @Setter
    Imprimer ctrImprimer;

    private @Getter
    @Setter
    List<AdminUniteOrganisationnelle> listUOS;
    private @Getter
    @Setter
    List<AdminUniteOrganisationnelle> selectedUOS;
    private @Getter
    @Setter
    AdminUniteOrganisationnelle selectedUO;

    public GestionUniteOrgController() {
    }

    @PostConstruct
    protected void initController() {
        findList();
        this.selectedUOS = new ArrayList<AdminUniteOrganisationnelle>();

    }

    public void rechercher() {
        listUOS = uniteOrganisationnelleFacade.findAll();
        if (listUOS.isEmpty() || listUOS.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }

    private void findList() {
        rechercher();
    }

    public void newUO() {
        this.selectedUO = new AdminUniteOrganisationnelle();
    }

    public void saveUnite() {
        if (!uniteOrganisationnelleFacade.isUniteExists(this.selectedUO.getCode())) {
            this.listUOS.add(this.selectedUO);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unité Organisationnelle ajouté"));
        } else if (this.selectedUO.getId() != null) {
            uniteOrganisationnelleFacade.editUnite(this.selectedUO);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unité Organisationnelle mis à jour"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'Unité Organisationnelle existe déjà.", null));
        }
        PrimeFaces.current().executeScript("PF('manageUniteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-unites");
    }

    /*public void saveUnite(ActionEvent actionEvent) {
        if (selectedUO != null) {
            // Vérifie si l'unité organisationnelle existe déjà
            if (!uniteOrganisationnelleFacade.isUniteExists(this.selectedUO.getCode())) {
                this.listUOS.add(this.selectedUO);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unité Organisationnelle ajouté"));
            } else if (this.selectedUO.getId() != null) {
                // Édite l'unité organisationnelle existante
                uniteOrganisationnelleFacade.editUnite(this.selectedUO);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unité Organisationnelle mis à jour"));
            } else {
                // Message d'erreur si l'unité existe déjà
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'Unité Organisationnelle existe déjà.", null));
            }

            // Ferme le dialogue et met à jour les composants nécessaires
            PrimeFaces.current().executeScript("PF('manageUniteDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-unites");
        }
    }*/

    public void deleteUnite() {
        this.listUOS.remove(this.selectedUO);
        this.selectedUOS.remove(this.selectedUO);
        this.selectedUO = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unité Organisationnelle supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-unites");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUnites()) {
            int size = this.selectedUOS.size();
            return size > 1 ? size + " Unités sélectionnés" : "1 Unité sélectionné";
        }
        return "Delete";
    }

    public boolean hasSelectedUnites() {
        return this.listUOS != null && !this.listUOS.isEmpty();
    }

    public void deleteSelectedUnites() {
        this.listUOS.removeAll(this.selectedUOS);
        this.selectedUOS = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unités Organisationnelles supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-unites");
        PrimeFaces.current().executeScript("PF('dtUnites').clearFilters()");
    }
}
