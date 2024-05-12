/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Banque.entity;

import dz.elit.pfe.Bordeuraux.entity.BordereauDirection;
import dz.elit.pfe.Cheque.entity.Cheque;
import dz.elit.pfe.administration.entity.Audit;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author xps
 */
@Entity
@Table(name = "banque", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banque.findByNom", query = "SELECT c FROM Banque c WHERE c.nom = :nom"),
    @NamedQuery(name = "Banque.findByCode", query = "SELECT c FROM Banque c WHERE c.code = :code"),
    @NamedQuery(name = "Banque.findById", query = "SELECT c FROM Banque c WHERE c.id = :id")
})
public class Banque extends Audit implements Serializable {

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
    
    /* nom */
    @Basic(optional = false)
    @NotNull
    @Size(max =255)
    @Column(name = "nom", unique = true)
    private String nom;
    
    /* num_compte */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "num_compte", nullable = false)
    private String numCompte;
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Clés Étrangères~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* Fkey (concerné) Cheque */
    @OneToMany(mappedBy = "banqueCheque")
    private List<Cheque> chequeList;
    
    /* Fkey (envoyé) Bordereau Direction */
    @OneToMany(mappedBy = "banqueBordereauDirection")
    private List<BordereauDirection> bordereauDirectionList;
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Constructeurs~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Banque() {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public List<Cheque> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<Cheque> chequeList) {
        this.chequeList = chequeList;
    }

    public List<BordereauDirection> getBordereauDirectionList() {
        return bordereauDirectionList;
    }

    public void setBordereauDirectionList(List<BordereauDirection> bordereauDirectionList) {
        this.bordereauDirectionList = bordereauDirectionList;
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
        if (!(object instanceof Banque)) {
            return false;
        }
        Banque other = (Banque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dz.elit.pfe.Banque.entity[ id=" + id + " ]";
    }
    
}