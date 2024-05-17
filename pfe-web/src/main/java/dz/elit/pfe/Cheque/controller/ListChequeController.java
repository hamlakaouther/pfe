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
import java.util.List;
import java.util.UUID;
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
    private @Getter @Setter MySessionController mySessionController;
    
    @ManagedProperty(value = "#{imprimer}")
    private @Getter @Setter Imprimer ctrImprimer;
    private @Getter @Setter List<Cheque> listCheques;
    private @Getter @Setter List<Cheque> cheques;
    private @Getter @Setter Cheque cheque;
    
    //Les variables de recherche
    private @Getter @Setter Integer id;
    private @Getter @Setter String numCheque;
    private @Getter @Setter String nomCheque;
    private @Getter @Setter BigDecimal montant;
    private @Getter @Setter LocalDateTime dateCreation;
    private @Getter @Setter String rip;
    private @Getter @Setter String etat;
    private @Getter @Setter String numFacture;
    private @Getter @Setter String codeClient;
    private @Getter @Setter BigDecimal montantFacture;
    
    public ListChequeController() {
    }
    
    @PostConstruct
    protected void initController() {
        findList();
    }
    
    public void rechercher() {
        listCheques = chequeFacade.findAll();
        if (listCheques.isEmpty() || listCheques.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
    }
        /*listCheques = ChequeFacade.findChequeByRip("systeme.systeme");
        if (listCheques.isEmpty() || listCheques.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }*/
    }

    private void findList() {
        rechercher();
    }
    
    public void addCheque() {
        this.cheque = new Cheque();
    }
    
    public void saveCheque() {
        if (this.cheque.getRip() == null) {
            this.cheque.setRip(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.listCheques.add(this.cheque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Chèque Ajouter"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Chèque mis à jour"));
        }

        PrimeFaces.current().executeScript("PF('manageChequeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    
    public boolean hasSelectedCheques() {
        return this.listCheques != null && !this.listCheques.isEmpty();
    }
    
    public String getDeleteButtonMessage() {
        if (hasSelectedCheques()) {
            int size = this.listCheques.size();
            return size > 1 ? size + " chèques sélectionnés" : "1 chèque sélectionné";
        }
        return "Delete";
    }
    
}
