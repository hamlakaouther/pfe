/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Cheque.controller;

import dz.elit.pfe.Cheque.entity.Cheque;
import dz.elit.pfe.Cheque.service.ChequeFacade;
import dz.elit.pfe.commun.controller.Imprimer;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.util.MyUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class ListChequeController implements Serializable {

    @Inject
    private ChequeFacade chequeFacade;

    @Inject
    private @Getter
    @Setter
    MySessionController mySessionController;

    @ManagedProperty(value = "#{imprimer}")
    private @Getter @Setter Imprimer ctrImprimer;
    private @Getter @Setter List<Cheque> listCheques;
    private @Getter @Setter List<Cheque> selectedCheques;
    private @Getter @Setter Cheque selectedCheque;

    //Les variables de recherche
    /*private @Getter @Setter Integer id;
    private @Getter @Setter String numCheque;
    private @Getter @Setter String nomCheque;
    private @Getter @Setter BigDecimal montant;
    private @Getter @Setter LocalDateTime dateCreation;
    private @Getter @Setter String rip;
    private @Getter @Setter String etat;
    private @Getter @Setter String numFacture;
    private @Getter @Setter String codeClient;
    private @Getter @Setter BigDecimal montantFacture;*/
    public ListChequeController() {
    }

    @PostConstruct
    protected void initController() {
        findList();
        this.selectedCheques = new ArrayList<Cheque>();

    }

    public void rechercher() {
        listCheques = chequeFacade.findAll();
        if (listCheques.isEmpty() || listCheques.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }

    private void findList() {
        rechercher();
    }

    public void addCheque() {
        this.selectedCheque = new Cheque();
    }

    public void saveCheque() {
        System.err.println("test save");    
        System.err.println(selectedCheque);
        if (!chequeFacade.isChequeExists(this.selectedCheque.getRip())) {
            this.listCheques.add(this.selectedCheque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Chèque ajouté"));
        } else if (this.selectedCheque.getId() != null) {
            chequeFacade.editCheque(this.selectedCheque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Chèque mis à jour"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le RIP existe déjà.", null));
        }
        PrimeFaces.current().executeScript("PF('manageChequeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cheques");
    }

    public void deleteCheque() {
        this.listCheques.remove(this.selectedCheque);
        this.selectedCheques.remove(this.selectedCheque);
        this.selectedCheque = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Chèque supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cheques");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCheques()) {
            int size = this.selectedCheques.size();
            return size > 1 ? size + " chèques sélectionnés" : "1 chèque sélectionné";
        }
        return "Delete";
    }

    public boolean hasSelectedCheques() {
        return this.listCheques != null && !this.listCheques.isEmpty();
    }

    public void deleteSelectedCheques() {
        this.listCheques.removeAll(this.selectedCheques);
        this.selectedCheques = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Chèques supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cheques");
        PrimeFaces.current().executeScript("PF('dtCheques').clearFilters()");
    }
}
