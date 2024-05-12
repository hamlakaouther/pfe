/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.controller;

import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.administration.service.AdminUtilisateurFacade;
import dz.elit.pfe.commun.filter.LoginFilter;
import dz.elit.pfe.commun.util.MyUtil;
import java.io.Serializable;
import java.security.Principal;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 
 */
@Named
@SessionScoped
@Getter 
@Setter
public class MySessionController implements Serializable {

    @Inject
    private AdminUtilisateurFacade utilisateurFacade;
    
    @ManagedProperty(value = "#{myApplicationController}")
    private MyApplicationController myApplicationController;
    
    private AdminUtilisateur utilisateurCourant;
    private String theme = "elit-metro";
    private HttpSession sessionUtilisateur;

    public MySessionController() {
    }

    @PostConstruct
    protected void initController() {
        initUser();
    }

    public void initUser() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        sessionUtilisateur = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (principal != null) {
            utilisateurCourant = utilisateurFacade.findByLogin(principal.getName());
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().getSessionMap().put(principal.getName(), utilisateurCourant);
        } else {
            utilisateurCourant = null;
        }
    }

    public Integer getLoginValue() {
        Integer login_error = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LoginFilter.ATTRIBUT_LOGIN_ERREUR);
        return login_error == null ? 0 : login_error;
    }

    public String logout() {
        String navigateTo = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext e = context.getExternalContext();
            navigateTo = e.getRequestContextPath();
            e.redirect(e.encodeResourceURL(e.getRequestContextPath() + "/login.xhtml"));
            myApplicationController.removeMySessionController(this);
        } catch (NullPointerException ea) {
            System.out.println("======= NullPointerException dans la methode logout =======");
        } catch (Exception ea) {
            System.out.println("======= Exception dans la methode logout =======");
        }
        return navigateTo;
    }

    public void logoutUtilisateur() {
        if (sessionUtilisateur != null) {
            HttpSession sessionTemp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (sessionUtilisateur.getId().equals(sessionTemp.getId())) {
                MyUtil.addWarnMessage("Vous ne pouvez pas se déconnecter à partir de cette interface !");
            } else {
                try {
                    sessionUtilisateur.invalidate();
                    myApplicationController.removeMySessionController(this);
                    MyUtil.addInfoMessage("Utilisateur déjà connecter avec succès");
                } catch (Exception ex) {
                    MyUtil.addWarnMessage("Erreur lors de la destruction des sessions !");

                }
            }
            sessionTemp = null;
        } else {
            MyUtil.addWarnMessage("Erreur lors de la déconnection de l'utilisateur");
        }
    }

    public boolean isUserInRole(String role) {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }

    public boolean isSuperUser() {
        if (utilisateurCourant != null) {
            return utilisateurCourant.isSuperUtilisateur();
        } else {
            return false;
        }
    }

    public void updateUtilisateur() {
        if (utilisateurCourant != null) {
            try {
                utilisateurFacade.edit(utilisateurCourant);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getLibelleUser() {
        if (utilisateurCourant != null) {
            return utilisateurCourant.getLibelleUser();
        }
        return "Inconnu";
    }

    public AdminUtilisateur getUtilisateurCourant() {
        if (utilisateurCourant == null) {
            initUser();
        }
        return utilisateurCourant;
    }

    public String getTheme() {
        if (utilisateurCourant == null) {
            initUser();
        }
        theme = utilisateurCourant != null ? utilisateurCourant.getTheme() != null ? utilisateurCourant.getTheme() : theme : theme;
        return theme;
    }

    public void setTheme(String theme) {
        if (utilisateurCourant != null) {
            utilisateurCourant.setTheme(theme);
        }
        this.theme = theme;
    }

    public boolean isUserSysteme() {
        return utilisateurCourant.getLogin().equalsIgnoreCase("systeme.systeme");
    }

    public boolean urlcontains(String url, String keyWord) {
        return url.contains(keyWord);
    }

}
