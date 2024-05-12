/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.administration.entity;

import dz.elit.pfe.Bordeuraux.entity.BordereauAgence;
import dz.elit.pfe.Bordeuraux.entity.BordereauDirection;
import dz.elit.pfe.Caisse.entity.Caisse;
import dz.elit.pfe.Cheque.entity.Cheque;
import dz.elit.pfe.enums.TypeUniteOrganisationnelle;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * 
 */
@Entity
@Table(name = "unite_organisationnelle", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdminUniteOrganisationnelle.findById", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.id = :id"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByCode", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.code = :code"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNiveau", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.niveau = :niveau"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNiveauEqualEtSupEtUniteParent", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.uniteParent.code =:uniteParent OR a.niveau >=:niveau"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findAllOrderByNiveau2EtUniteParent", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.code =:uniteParent OR a.uniteParent.code =:uniteParent2"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNiveauEqual", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.niveau =:niveau"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNiveauType", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.typeUniteOrganisationnelle =:type"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByUniteParent", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.uniteParent.code =:uniteParent"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNivEqualEtUniteParent", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.uniteParent.code =:uniteParent AND a.niveau =:niveau"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNivEqualEtUniteParentUniteParent", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.uniteParent.uniteParent.code =:uniteParent AND a.niveau =:niveau"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findOnlyAllChilds", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.uniteParent =:adminUniteOrg"),
    @NamedQuery(name = "AdminUniteOrganisationnelle.findByNiveauEqualEtSup", query = "SELECT a FROM AdminUniteOrganisationnelle a WHERE a.niveau >=:niveau")
})
public class AdminUniteOrganisationnelle extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Attributs~~~~~~~~~~~~~~~~~~~~~~~~~~ */ 
    /* ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    /* Capital Social */
    @NotNull
    @Size(max = 30)
    @Column(name = "capital_social")
    private String capitalSocial;
    
    /* Code */
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "code", unique = true)
    private String code;
   
    /* Denomination Arab */
    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(name = "denomination_ar")
    private String denominationAr;
    
    /* Denomination Francais */
    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(name = "denomination_fr")
    private String denominationFr;
    
    /* Adresse */
    @NotNull
    @Size(max = 50)
    private String adresse;
    
    /* Email */
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^|([a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)", message = "Invalid email")
    private String email;
    
    /* Telephone */
    @NotNull
    @Size(max = 10)
    @Column(name = "telephone")
    private String telephone;
    
    /* Niveau */
    @NotNull
    @Column(name = "niveau")
    private Integer niveau;
    
    /* Type Unite Organisationnelle */
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_unite_organisationnelle")
    private TypeUniteOrganisationnelle typeUniteOrganisationnelle;
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Clés Étrangères~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* Fkey (appartient) Agence */
    @JoinColumn(name = "unite_parent", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdminUniteOrganisationnelle uniteParent;
    
    /* Fkey (appartient) DD */
    @OneToMany(mappedBy = "uniteParent")
    private List<AdminUniteOrganisationnelle> adminUniteOrganisationnelleList; /* I changed "adminUniteOrganisationnelleList" to "uniteOrganisationnelleFilsList" */
    
    /* Fkey (appartient) Utilisateur */
    @OneToMany(mappedBy = "adminUniteOrganisationnelle")
    private List<AdminUtilisateur> utilisateurList;
    
    /* Fkey (appartient) Caisse */
    @OneToMany(mappedBy = "uniteOrganisationnelleCaisse")
    private List<Caisse> caisseList;
    
    /* Fkey (rattaché) Bordereau Agence */
    @OneToMany(mappedBy = "uniteOrganisationnelleBordereauAgence")
    private List<BordereauAgence> bordereauAgenceList;
    
    /* Fkey (rattaché) Bordereau Direction */
    @OneToMany(mappedBy = "uniteOrganisationnelleBordereauDirection")
    private List<BordereauDirection> bordereauDirectionList;
    
    /* Fkey (uniteEncaissement) Cheque */
    @OneToMany(mappedBy = "uniteOrganisationnelleCheque")
    private List<Cheque> chequeList;
   
    /*@Transient un tuple qu'il n'existe pas dans la bdd but he will pretand that its
    private String affichage;*/

    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Constructeurs~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public AdminUniteOrganisationnelle() {
    }

    public AdminUniteOrganisationnelle(Integer id) {
        this.id = id;
    }

    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Getters et Les Setter~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDenominationAr() {
        return denominationAr;
    }

    public void setDenominationAr(String denominationAr) {
        this.denominationAr = denominationAr;
    }

    public String getDenominationFr() {
        return denominationFr;
    }

    public void setDenominationFr(String denominationFr) {
        this.denominationFr = denominationFr;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    @XmlTransient
    public List<AdminUniteOrganisationnelle> getAdminUniteOrganisationnelleList() {
        return adminUniteOrganisationnelleList;
    }

    public void setAdminUniteOrganisationnelleList(List<AdminUniteOrganisationnelle> adminUniteOrganisationnelleList) {
        this.adminUniteOrganisationnelleList = adminUniteOrganisationnelleList;
    }

    public AdminUniteOrganisationnelle getUniteParent() {
        return uniteParent;
    }

    public void setUniteParent(AdminUniteOrganisationnelle uniteParent) {
        this.uniteParent = uniteParent;
    }

    public List<AdminUtilisateur> getUtilisateurList() {
        return utilisateurList;
    }

    public void setUtilisateurList(List<AdminUtilisateur> utilisateurList) {
        this.utilisateurList = utilisateurList;
    }

    public List<Caisse> getCaisseList() {
        return caisseList;
    }

    public void setCaisseList(List<Caisse> caisseList) {
        this.caisseList = caisseList;
    }

    public List<BordereauAgence> getBordereauAgenceList() {
        return bordereauAgenceList;
    }

    public void setBordereauAgenceList(List<BordereauAgence> bordereauAgenceList) {
        this.bordereauAgenceList = bordereauAgenceList;
    }

    public List<BordereauDirection> getBordereauDirectionList() {
        return bordereauDirectionList;
    }

    public void setBordereauDirectionList(List<BordereauDirection> bordereauDirectionList) {
        this.bordereauDirectionList = bordereauDirectionList;
    }

    public List<Cheque> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<Cheque> chequeList) {
        this.chequeList = chequeList;
    }

    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Méthodes~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public String getAffichage() {
        return getVideDe(niveau) + code + "";
    }

    private String getVideDe(int i) {
        String res = "";
        for (int j = 1; j < i; j++) {
            res = res + "---";
        }
        return res;
    }

    public List<AdminUniteOrganisationnelle> getAllsubUniteWithCurrent() {
        List<AdminUniteOrganisationnelle> list = new ArrayList<>();
        list.add(this);
        for (AdminUniteOrganisationnelle adminUniteOrganisationnelle : adminUniteOrganisationnelleList) {
            list.addAll(adminUniteOrganisationnelle.getAllsubUniteWithCurrent());
        }
        return list;

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminUniteOrganisationnelle)) {
            return false;
        }
        AdminUniteOrganisationnelle other = (AdminUniteOrganisationnelle) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        String affichage = null;
        return "dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle[ id=" + id + ",affichage:" + affichage;
    }

    public TypeUniteOrganisationnelle getTypeUniteOrganisationnelle() {
        return typeUniteOrganisationnelle;
    }

    public void setTypeUniteOrganisationnelle(TypeUniteOrganisationnelle typeUniteOrganisationnelle) {
        this.typeUniteOrganisationnelle = typeUniteOrganisationnelle;
    }

    public String getCodeUniteSgc() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getTrie() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
