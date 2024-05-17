/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Cheque.controller;

import dz.elit.pfe.Cheque.entity.Cheque;
import dz.elit.pfe.Cheque.service.ChequeFacade;
import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.commun.controller.MySessionController;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author xps
 */
@Named
@ViewScoped
public class AddChequeController  implements Serializable {

    @Inject
    private ChequeFacade chequeFacade;
    private @Getter @Setter Cheque cheque; 
    private @Getter @Setter AdminUniteOrganisationnelle uniteOrganisationnelleSelected;
    private @Getter @Setter int etatCompte;
    private @Getter @Setter Date dateDuJour;
    private @Getter @Setter boolean disabled = false;
    
    @Inject
    private @Getter @Setter MySessionController mySessionController;

    public AddChequeController() {
    }

    @PostConstruct
    protected void initController() {
        dateDuJour = new Date();
        initAddCheque();
    }

    private void initAddCheque() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}