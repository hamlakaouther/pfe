/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Caisse.controller;

import dz.elit.pfe.Caisse.entity.Caisse;
import dz.elit.pfe.Caisse.service.CaisseFacade;
import dz.elit.pfe.commun.controller.Imprimer;
import dz.elit.pfe.commun.controller.MySessionController;
import dz.elit.pfe.commun.util.MyUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author xps
 */
@Named
@ViewScoped
public class ListCaisseController implements Serializable {
    @Inject
    private CaisseFacade caisseFacade;    

    @Inject
    private @Getter @Setter MySessionController mySessionController;
    
    @ManagedProperty(value = "#{imprimer}")
    private @Getter @Setter Imprimer ctrImprimer;
    private @Getter @Setter Caisse caisse;
    private @Getter @Setter List<Caisse> listCaisses;
    
    //Les variables de recherche
   
    
    public ListCaisseController() {
    }
    
    @PostConstruct
    protected void initController() {
        findList();
    }
    
    public void rechercher() {
        listCaisses = caisseFacade.findAll();
        if (listCaisses.isEmpty() || listCaisses.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_resultat_recherche_null"));
        }
    }

    private void findList() {
        rechercher();
    }
    
    public String addCaisse() {
        caisse = new Caisse();
        return "addCaisse";
    }
}

