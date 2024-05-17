/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Caisse.entity;

import dz.elit.pfe.Cheque.entity.Cheque;
import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author xps
 */
@Entity
@Table(name = "caisse", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caisse.findByCode", query = "SELECT c FROM Caisse c WHERE c.code = :code"),
    @NamedQuery(name = "Caisse.findAll", query = "SELECT c FROM Caisse c"),
    @NamedQuery(name = "Caisse.findById", query = "SELECT c FROM Caisse c WHERE c.id = :id")
})
public class Caisse extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Attributs~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Basic(optional = false)
    @Column(name = "id") 
    private Integer id;
    
    /* code */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "code", unique = true)
    private String code;
    
    /* etat */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "etat")
    private String  etat;

    /* date_ouverture */
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_ouverture")
    private LocalDateTime  dateOuverture;
    
    /* date_arret */
    @Column(name = "date_arret")
    private LocalDateTime  dateArret;
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Clés Étrangères~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* Fkey (appartient) UO */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unite_organisationnelle", referencedColumnName = "id")
    private AdminUniteOrganisationnelle uniteOrganisationnelleCaisse;
    
    /* Fkey (concerne) Cheque */
    @OneToMany(mappedBy = "caisseCheque")
    private List<Cheque> chequeList;

    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Constructeurs~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Caisse() {
    }
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Getters et Les Setter~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDateTime getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDateTime dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDateTime getDateArret() {
        return dateArret;
    }

    public void setDateArret(LocalDateTime dateArret) {
        this.dateArret = dateArret;
    }

    public AdminUniteOrganisationnelle getUniteOrganisationnelleCaisse() {
        return uniteOrganisationnelleCaisse;
    }

    public void setUniteOrganisationnelleCaisse(AdminUniteOrganisationnelle uniteOrganisationnelleCaisse) {
        this.uniteOrganisationnelleCaisse = uniteOrganisationnelleCaisse;
    }

    public List<Cheque> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<Cheque> chequeList) {
        this.chequeList = chequeList;
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
        if (!(object instanceof Caisse)) {
            return false;
        }
        Caisse other = (Caisse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dz.elit.pfe.Caisse.entity.Caisse[ id=" + id + " ]";
    }
    
}

