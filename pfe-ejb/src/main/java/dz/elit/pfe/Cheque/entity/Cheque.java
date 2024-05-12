/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Cheque.entity;

import dz.elit.pfe.Banque.entity.Banque;
import dz.elit.pfe.Bordeuraux.entity.BordereauAgence;
import dz.elit.pfe.Bordeuraux.entity.BordereauDirection;
import dz.elit.pfe.Caisse.entity.Caisse;
import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.administration.entity.Audit;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author xps
 */
@Entity
@Table(name = "cheque", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cheque.findByRip", query = "SELECT c FROM Cheque c WHERE c.rip = :rip"),
    @NamedQuery(name = "Cheque.findById", query = "SELECT c FROM Cheque c WHERE c.id = :id")
})
public class Cheque extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Attributs~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    /* num_cheque */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "num_cheque")
    private String numCheque;

    /* nom_cheque */
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "nom_cheque")
    private String nomCheque;

    /* montant */
    @Basic(optional = false)
    @NotNull
    @Column(name = "montant", precision = 24, scale = 4)
    private BigDecimal montant;

    /* montant_restant */
    @Column(name = "montant_restant", precision = 24, scale = 4)
    private BigDecimal montantRestant;

    /* date_creation */
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    /* date_envoi_banque */
    @Column(name = "date_envoi_banque")
    private LocalDateTime dateEnvoiBanque;

    /* date_envoi_dd */
    @Column(name = "date_envoi_dd")
    private LocalDateTime dateEnvoiDd;

    /* date_encaissement */
    @Column(name = "date_encaissement")
    private LocalDateTime dateEncaissement;

    /* rip */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "rip", unique = true)
    private String rip;

    /* etat */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "etat")
    private String etat;

    /* num_facture */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "num_facture")
    private String numFacture;

    /* code_client */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "code_client")
    private String codeClient;

    /* montant_facture */
    @Basic(optional = false)
    @NotNull
    @Column(name = "montant_facture", precision = 24, scale = 4)
    private BigDecimal montantFacture;

    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Clés Étrangères~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* Fkey (concerné) Banque */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_banque", referencedColumnName = "id")
    private Banque banqueCheque;

    /* Fkey (concerne) Caisse */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caisse", referencedColumnName = "id")
    private Caisse caisseCheque;

    /* Fkey (manipuler) Utilisateur */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id")
    private AdminUtilisateur utilisateurCheque;

    /* Fkey (contient) Bordereau Agence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bordereau_agence", referencedColumnName = "id")
    private BordereauAgence bordereauAgenceCheque;

    /* Fkey (uniteEncaissement) UO */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unite_organisationnelle", referencedColumnName = "id")
    private AdminUniteOrganisationnelle uniteOrganisationnelleCheque;

    /* Fkey (contient) Bordereau Direction */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bordereau_direction", referencedColumnName = "id")
    private BordereauDirection bordereauDirectionCheque;

    
    /* ~~~~~~~~~~~~~~~~~~~Les Constructeurs~~~~~~~~~~~~~~~~~~~ */
    public Cheque() {
    }
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Getters et Les Setter~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getNomCheque() {
        return nomCheque;
    }

    public void setNomCheque(String nomCheque) {
        this.nomCheque = nomCheque;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateEnvoiBanque() {
        return dateEnvoiBanque;
    }

    public void setDateEnvoiBanque(LocalDateTime dateEnvoiBanque) {
        this.dateEnvoiBanque = dateEnvoiBanque;
    }

    public LocalDateTime getDateEnvoiDd() {
        return dateEnvoiDd;
    }

    public void setDateEnvoiDd(LocalDateTime dateEnvoiDd) {
        this.dateEnvoiDd = dateEnvoiDd;
    }

    public LocalDateTime getDateEncaissement() {
        return dateEncaissement;
    }

    public void setDateEncaissement(LocalDateTime dateEncaissement) {
        this.dateEncaissement = dateEncaissement;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public BigDecimal getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(BigDecimal montantFacture) {
        this.montantFacture = montantFacture;
    }

    public Banque getBanqueCheque() {
        return banqueCheque;
    }

    public void setBanqueCheque(Banque banqueCheque) {
        this.banqueCheque = banqueCheque;
    }

    public Caisse getCaisseCheque() {
        return caisseCheque;
    }

    public void setCaisseCheque(Caisse caisseCheque) {
        this.caisseCheque = caisseCheque;
    }

    public AdminUtilisateur getUtilisateurCheque() {
        return utilisateurCheque;
    }

    public void setUtilisateurCheque(AdminUtilisateur utilisateurCheque) {
        this.utilisateurCheque = utilisateurCheque;
    }

    public BordereauAgence getBordereauAgenceCheque() {
        return bordereauAgenceCheque;
    }

    public void setBordereauAgenceCheque(BordereauAgence bordereauAgenceCheque) {
        this.bordereauAgenceCheque = bordereauAgenceCheque;
    }

    public AdminUniteOrganisationnelle getUniteOrganisationnelleCheque() {
        return uniteOrganisationnelleCheque;
    }

    public void setUniteOrganisationnelleCheque(AdminUniteOrganisationnelle uniteOrganisationnelleCheque) {
        this.uniteOrganisationnelleCheque = uniteOrganisationnelleCheque;
    }

    public BordereauDirection getBordereauDirectionCheque() {
        return bordereauDirectionCheque;
    }

    public void setBordereauDirectionCheque(BordereauDirection bordereauDirectionCheque) {
        this.bordereauDirectionCheque = bordereauDirectionCheque;
    }
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Méthodes~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cheque)) {
            return false;
        }
        Cheque other = (Cheque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "dz.elit.pfe.Cheque.entity.Cheque{" + "id=" + id + '}';
    }
    
}


