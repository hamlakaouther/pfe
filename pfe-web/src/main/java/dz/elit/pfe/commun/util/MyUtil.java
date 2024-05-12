/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.util;

import dz.elit.pfe.commun.exception.MyException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 *
 * @author chekor.samir
 */
public abstract class MyUtil {
    
     public static final Map<String, List<String>> hiddenList = new HashMap<String, List<String>>() {
        {
            put("AdminUtilisateur", Arrays.asList("pwd"));
        }
    };

    //*************** Déclaration des ressources bundles ************************
    /**
     * Bundle commun pour tous les modules .
     */
    public static final String BUNDLE_COMMUN = "/bundle/bundleCommun";
    /**
     * Bundle pour le module administration.
     */
    public static final String BUNDLE_ADMIN = "/bundle/bundleAdmin";
    /**
     * Bundle pour le module Trésorerie Client.
     */

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static void addInfoMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info :", msg));
    }

    public static void addWarnMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention :", msg));
    }
    
    public static void addWarnMessage(String libelle, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, libelle, msg));
    }

    public static void addErrorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur :", msg));
    }
    
   public static void addListErrorMessage(MyException exception) {
       for(String error : exception.getErrors()){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur :", error));
       } 
    }

    public static void addFatalMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal :", msg));
    }
    
    /**
     * Gets a string for the given key from this resource bundle name
     * "BUNDLE_COMMUN" . for All module
     *
     * @param value : the key for the desired value
     * @return the string value for the given key
     */
    public static String getBundleCommun(String value) {
        try {
            return ResourceBundle.getBundle(BUNDLE_COMMUN).getString(value.trim());
        } catch (MissingResourceException e) {
            e.printStackTrace();
            return "?:" + value + ":?";
        } catch (Exception e) {
            e.printStackTrace();
            return "?:Can't find bundle:?";
        }
    }

    /**
     * Gets a string for the given key from this resource bundle name
     * "BUNDLE_ADMIN" . for module administartion
     *
     * @param value : the key for the desired value
     * @return the string value for the given key
     */
    public static String getBundleAdmin(String value) {
        try {
            return ResourceBundle.getBundle(BUNDLE_ADMIN).getString(value.trim());
        } catch (MissingResourceException e) {
            e.printStackTrace();
            return "?:" + value + ":?";
        } catch (Exception e) {
            e.printStackTrace();
            return "?:Can't find bundle:?";
        }
    }
  
    public static String getStackTrace(Exception ex) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
    
    public static String getDetailStackTrace(Exception ex) {
        String st = getStackTrace(ex);
        if (st.contains("org.eclipse.persistence.exceptions.OptimisticLockException")) {
           //return "Cannot be merged because it has changed or been deleted since it was last read";
            return "Problème d'accès concurrentiel, il se peut que l'objet a été changé. Veuillez vérifier ! ";
        } else {
            String[] tst = st.split("Détail");
            if (tst.length > 1) {
                String[] tst1 = tst[1].split("\\.");
                if (tst1.length >= 1) {
                    return tst1[0].replaceFirst(":", "");
                }
            }
        }
        return null;
    }
    
    public static char convertCharToQwerty(char azerty) {
        switch (azerty) {
            case '&':
                return '1';
            case 'é':
                return '2';
            case '"':
                return '3';
            case '\'':
                return '4';
            case '(':
                return '5';
            case '-':
                return '6';
            case 'è':
                return '7';
            case '_':
                return '8';
            case 'ç':
                return '9';
            case 'à':
                return '0';
            case 'a':
                return 'q';
            case 'A':
                return 'Q';
            case 'q':
                return 'a';
            case 'Q':
                return 'A';
            case 'z':
                return 'w';
            case 'Z':
                return 'W';
            case 'w':
                return 'z';
            case 'W':
                return 'Z';
            case 'm':
                return ';';
            case 'M':
                return ';';
            case ',':
                return 'm';
            case '?':
                return 'M';
            case ';':
                return ',';
            case '.':
                return ',';
            case ':':
                return '.';
            case '/':
                return '.';
            case '!':
                return '/';
            case '§':
                return '/';
            case 'ù':
                return '\'';
            case '%':
                return '\'';
            case '*':
                return '\\';
            case 'µ':
                return '\\';
            case '^':
                return '[';
            case '¨':
                return '[';
            case '$':
                return ']';
            case '£':
                return ']';
            case '<':
                return '\\';
            case '>':
                return '|';
            case '²':
                return '`';
            case ')':
                return '-';
            case '°':
                return '-';
            case '=':
                return '=';
            case '+':
                return '=';
            default:
                return azerty;
        }
    }
}
