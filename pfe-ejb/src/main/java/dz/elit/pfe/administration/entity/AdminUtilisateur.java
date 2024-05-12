/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.administration.entity;

import dz.elit.pfe.Cheque.entity.Cheque;
import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author xps
 */
@Entity
//@Cacheable(false)

@Table(name = "utilisateur", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdminUtilisateur.findByLogin", query = "SELECT u FROM AdminUtilisateur u WHERE u.login=:login"),
    @NamedQuery(name = "AdminUtilisateur.findByCodeAgent", query = "SELECT u FROM AdminUtilisateur u WHERE u.codeAgent=:codeAgent"),
    @NamedQuery(name = "AdminUtilisateur.findUtilisateursByUniteOrg", query = "SELECT u FROM AdminUtilisateur u WHERE u.adminUniteOrganisationnelle.code=:codeUniteOrg"),
    @NamedQuery(name = "AdminUtilisateur.findUtilisateursByUniteOrgPwdChanged", query = "SELECT u FROM AdminUtilisateur u WHERE u.active = true AND u.adminUniteOrganisationnelle.code=:codeUniteOrg AND u.isPwdChanged =:isPwdChanged"),
    @NamedQuery(name = "AdminUtilisateur.findAll", query = "SELECT u FROM AdminUtilisateur u"),
    @NamedQuery(name = "AdminUtilisateur.findMaxCodeAgent", query = "SELECT Max(u.codeAgent) FROM AdminUtilisateur u"),
    @NamedQuery(name = "AdminUtilisateur.findById", query = "SELECT a FROM AdminUtilisateur a WHERE a.id = :id")})

public class AdminUtilisateur extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Attributs~~~~~~~~~~~~~~~~~~~~~~~~~~ */  
    /* ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    /* Nom */
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "nom")
    private String nom;
    
    /* Prenom */
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "prenom")
    private String prenom;
    
    /* Username */
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "login", unique = true)
    private String login;
    
    /* Password */
    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(name = "pwd")
    private String pwd;
    
    /* fonction */
    @Size(max = 50)
    @Column(name = "fonction")
    private String fonction;
    
    /* Email */
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    
    /* Telephone Bureau */
    @Size(max = 9)
    @Pattern(regexp = "([0-9]*)", message = "Format téléphone incorrecte ")
    @Column(name = "tel_bureau")
    private String telephoneBureau;
    
    /* Telephone Mobile */
    @Size(max = 10)
    @Pattern(regexp = "^|([0-9]{10}$)", message = "Format téléphone incorrecte ")
    @Column(name = "tel_mobile")
    private String telephoneMobile;
    
    /* Fax */
    @Size(max = 9)
    @Pattern(regexp = "([0-9]*)", message = "Format fax incorrecte ")
    @Column(name = "fax")
    private String fax;
    
    /* Theme */
    @Size(max = 50)
    @Column(name = "theme")
    private String theme = "elit-metro";
    
    /* Adresse 1 */
    @Size(max = 50)
    @Column(name = "adresse1")
    private String adresse1;
    
    /* Adresse 2 */
    @Size(max = 50)
    @Column(name = "adresse2")
    private String adresse2;
    
    /* Active */
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    
    /* Support */
    @NotNull
    @Column(name = "support")
    private boolean support;
    
    /* Ticket Caisse */
    @NotNull
    @Column(name = "ticket_caisse")
    private boolean ticketCaisse;
    
    /* Language Qr Code */
    @Size(max = 10)
    @Column(name = "language_qr_code")
    private String languageQrCode = "EN";
    
    /* Super Utilisateur */
    @Column(name = "super_utilisateur")
    private boolean superUtilisateur;
    
    /* Date d'Activation */
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_activation")
    @Temporal(TemporalType.DATE)
    private Date dateActivation;
    
    /* Date d'Expiration */
    @Column(name = "date_expiration")
    @Temporal(TemporalType.DATE)
    private Date dateExpiration;
    
    /* Code Agent */
    @Size(max = 3)
    @Column(name = "code_agent")
    private String codeAgent;
    
    /* isPwdChanged */ 
    @Column(name = "is_pwd_changed", columnDefinition = "boolean  default false")
    private boolean isPwdChanged = false;
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~Les Clés Étrangères~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* Fkey (appartient) Utilisateur */
    @JoinColumn(name = "id_unite_organisationnelle", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdminUniteOrganisationnelle adminUniteOrganisationnelle;
    
    /* Fkey (manipuler) Cheque */
    @OneToMany(mappedBy = "utilisateurCheque")
    private List<Cheque> chequeList;

    
    /* ~~~~~~~~~~~~~~~~~~~Les Constructeurs~~~~~~~~~~~~~~~~~~~ */ 
    public AdminUtilisateur() {
    }


    /* ~~~~~~~~~~~~~~~~~~~Les Getters et les Setters~~~~~~~~~~~~~~~~~~~ */ 
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public List<Cheque> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<Cheque> chequeList) {
        this.chequeList = chequeList;
    }
    
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSuperUtilisateur() {
        return superUtilisateur;
    }

    public void setSuperUtilisateur(boolean superUtilisateur) {
        this.superUtilisateur = superUtilisateur;
    }

    public Date getDateActivation() {
        return dateActivation;
    }

    public void setDateActivation(Date dateActivation) {
        this.dateActivation = dateActivation;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneBureau() {
        return telephoneBureau;
    }

    public void setTelephoneBureau(String telephoneBureau) {
        this.telephoneBureau = telephoneBureau;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public AdminUniteOrganisationnelle getAdminUniteOrganisationnelle() {
        return adminUniteOrganisationnelle;
    }

    public void setAdminUniteOrganisationnelle(AdminUniteOrganisationnelle adminUniteOrganisationnelle) {
        this.adminUniteOrganisationnelle = adminUniteOrganisationnelle;
    }
    
    public boolean isSupport() {
        return support;
    }

    public void setSupport(boolean support) {
        this.support = support;
    }

    public String getLanguageQrCode() {
        return languageQrCode;
    }

    public void setLanguageQrCode(String languageQrCode) {
        this.languageQrCode = languageQrCode;
    }

    public String getCodeAgent() {
        return codeAgent;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public boolean isIsPwdChanged() {
        return isPwdChanged;
    }

    public void setIsPwdChanged(boolean isPwdChanged) {
        this.isPwdChanged = isPwdChanged;
    }

    public boolean isTicketCaisse() {
        return ticketCaisse;
    }

    public void setTicketCaisse(boolean ticketCaisse) {
        this.ticketCaisse = ticketCaisse;
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
        if (!(object instanceof AdminUtilisateur)) {
            return false;
        }
        AdminUtilisateur other = (AdminUtilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dz.elit.pfe.administration.entity.AdminUtilisateur[ id=" + id + " ]";
    }

    /**
     *
     * @throws NamingException
     */
    @Override
    @PrePersist
    public void prepersist() throws NamingException {

        super.prepersist();
        setAdminUniteOrganisationnelleCreateur(this.getAdminUniteOrganisationnelle());

    }

    public String getLibelleUser() {

        return nom + " " + prenom;
    }
}
