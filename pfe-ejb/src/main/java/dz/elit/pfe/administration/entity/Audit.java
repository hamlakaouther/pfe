package dz.elit.pfe.administration.entity;

import jakarta.faces.context.FacesContext;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.naming.NamingException;

/**
 *
 * @author Leghettas Rabah, Chekor Samir, Laidani Youcef
 */
@MappedSuperclass
public class Audit implements Serializable {

    @Column(name = "creer_le")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creerLe;

    @Column(name = "modifier_le")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifierLe;

    @JoinColumn(name = "creer_par", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdminUtilisateur creerPar;

    @JoinColumn(name = "modifier_par", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdminUtilisateur modifierPar;

    @JoinColumn(name = "id_unite_organisationnelle_createur", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    protected AdminUniteOrganisationnelle adminUniteOrganisationnelleCreateur;

//    @JoinColumn(name = "id_unite_organisationnelle_gestionnaire", referencedColumnName = "id")
//    @ManyToOne
//    protected AdminUniteOrganisationnelle adminUniteOrganisationnelleGestionnaire;
    @Transient
    protected AdminUtilisateur adminUtilisateur;

    public Audit() {
    }

    public Date getCreerLe() {
        return creerLe;
    }

    public void setCreerLe(Date creerLe) {
        this.creerLe = creerLe;
    }

    public Date getModifierLe() {
        return modifierLe;
    }

    public void setModifierLe(Date modifierLe) {
        this.modifierLe = modifierLe;
    }

    public AdminUtilisateur getCreerPar() {
        return creerPar;
    }

    public void setCreerPar(AdminUtilisateur creerPar) {
        this.creerPar = creerPar;
    }

    public AdminUtilisateur getModifierPar() {
        return modifierPar;
    }

    public void setModifierPar(AdminUtilisateur modifierPar) {
        this.modifierPar = modifierPar;
    }

    public AdminUniteOrganisationnelle getAdminUniteOrganisationnelleCreateur() {
        return adminUniteOrganisationnelleCreateur;
    }

    public void setAdminUniteOrganisationnelleCreateur(AdminUniteOrganisationnelle adminUniteOrganisationnelleCreateur) {
        this.adminUniteOrganisationnelleCreateur = adminUniteOrganisationnelleCreateur;
    }

//    public AdminUniteOrganisationnelle getAdminUniteOrganisationnelleGestionnaire() {
//        return adminUniteOrganisationnelleGestionnaire;
//    }
//
//    public void setAdminUniteOrganisationnelleGestionnaire(AdminUniteOrganisationnelle adminUniteOrganisationnelleGestionnaire) {
//        this.adminUniteOrganisationnelleGestionnaire = adminUniteOrganisationnelleGestionnaire;
//    }
    public AdminUtilisateur getAdminUtilisateur() {
        return adminUtilisateur;
    }

    public void setAdminUtilisateur(AdminUtilisateur adminUtilisateur) {
        this.adminUtilisateur = adminUtilisateur;
    }

    private void findUtilisateur() throws NamingException {
        if (this.adminUtilisateur == null && FacesContext.getCurrentInstance().getExternalContext() != null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            this.adminUtilisateur = (AdminUtilisateur) facesContext.getExternalContext().getSessionMap().get(principal.getName());
        }
    }

    @PrePersist
    public void prepersist() throws NamingException {
        findUtilisateur();
        if (this.adminUtilisateur != null) {
            if (getCreerPar() == null) {
                setCreerPar(this.adminUtilisateur);
            }
            setCreerLe(new Date());
            if (getAdminUniteOrganisationnelleCreateur() == null) {
                setAdminUniteOrganisationnelleCreateur(adminUtilisateur.getAdminUniteOrganisationnelle());
            }
        }
    }

//    @PreUpdate
//    public void preupdate() throws NamingException {
//        findUtilisateur();
//        if (adminUtilisateur != null) {
//            setModifierPar(adminUtilisateur);
//            setModifierLe(new Date());
//        }
//    }
}
