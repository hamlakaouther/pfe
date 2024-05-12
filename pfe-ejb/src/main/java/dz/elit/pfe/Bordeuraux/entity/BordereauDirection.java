/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Bordeuraux.entity;

import dz.elit.pfe.Banque.entity.Banque;
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
@Table(name = "bordereau_direction", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BordereauDirection.findById", query = "SELECT c FROM  BordereauDirection c WHERE c.id = :id"),
    @NamedQuery(name = "BordereauDirection.findByCode", query = "SELECT c FROM  BordereauDirection c WHERE c.code = :code")
})
public class BordereauDirection extends Audit implements Serializable {

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
    
    /*  date_creation */
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    /*   date_envoi  */
    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi ;
    
    /* num_compte */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "num_compte")
    private String numCompte;
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Clés Étrangères~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* Fkey (rattaché) UO */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unite_organisationnelle", referencedColumnName = "id")
    private AdminUniteOrganisationnelle uniteOrganisationnelleBordereauDirection;
    
    /* Fkey (envoyé) Banque*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_banque", referencedColumnName = "id")
    private Banque banqueBordereauDirection;

    /* Fkey (contient) Cheque */
    @OneToMany(mappedBy = "bordereauDirectionCheque")
    private List<Cheque> chequeList;

    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Constructeurs~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BordereauDirection() {
    }
    
    
    /* ~~~~~~~~~~~~~~~~~~~Les Getters et les Setters~~~~~~~~~~~~~~~~~~~ */
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public AdminUniteOrganisationnelle getUniteOrganisationnelleBordereauDirection() {
        return uniteOrganisationnelleBordereauDirection;
    }

    public void setUniteOrganisationnelleBordereauDirection(AdminUniteOrganisationnelle uniteOrganisationnelleBordereauDirection) {
        this.uniteOrganisationnelleBordereauDirection = uniteOrganisationnelleBordereauDirection;
    }

    public Banque getBanqueBordereauDirection() {
        return banqueBordereauDirection;
    }

    public void setBanqueBordereauDirection(Banque banqueBordereauDirection) {
        this.banqueBordereauDirection = banqueBordereauDirection;
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
        if (!(object instanceof BordereauDirection)) {
            return false;
        }
        BordereauDirection other = (BordereauDirection) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dz.elit.pfe.Bordeuraux.entity.BordereauDirection[ id=" + id + " ]";
    }
    
}

