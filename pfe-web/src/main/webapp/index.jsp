
<%@page import="dz.elit.pfe.administration.service.AdminUtilisateurFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="dz.elit.pfe.administration..entity.AdminUtilisateur"%>
<%@page import="dz.elit.pfe.commun.util.StaticUtil"%>
<%@page import="org.primefaces.push.Status.STATUS"%>
<%@page import="jakarta.faces.application.FacesMessage"%>
<%@page import="jakarta.faces.context.FacesContext"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.security.Principal"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    Principal loginUser = request.getUserPrincipal();

//    if (loginUser != null) {
//        Context c = new InitialContext();
//        
//       // AdminUtilisateurFacade utilisateurFacade = (AdminUtilisateurFacade) c.lookup(StaticUtil.JNDI_LOOK_UP_PATH + "/AdminUtilisateurFacade!" + AdminUtilisateurFacade.class.getName());
//    //    AdminUtilisateur utilisateur = utilisateurFacade.findByLogin(loginUser.getName());
////System.out.println("request.isUserInRole(PAS_PWD_CHANGED) *****************************= " + request.isUserInRole("PAS_PWD_CHANGED"));
////        if (request.isUserInRole("INACTIF")) {
////            response.sendRedirect(request.getContextPath() + "/pages/erreur/inactif.xhtml");
////        } else if (request.isUserInRole("EXPIRE")) {
////            response.sendRedirect(request.getContextPath() + "/pages/erreur/expire.xhtml");
////        } else if (request.isUserInRole("PAS_PWD_CHANGED")) {
////            response.sendRedirect(request.getContextPath() + "/pages/commun/changePwdUtilisateur.xhtml");
////        } else if (request.isUserInRole("PAS_ENCORS_ACTIF")) {
////            response.sendRedirect(request.getContextPath() + "/pages/erreur/pasEncorsActif.xhtml");
////        } 
////        //else if (utilisateur.getPwd().equals(StaticUtil.getDefaultEncryptPassword())) {
////      //      response.sendRedirect(request.getContextPath() + "/pages/commun/changePwdUtilisateur.xhtml");
////       // } 
////        else {
            response.sendRedirect(request.getContextPath() + "/accueil.xhtml");
       // }

//    } else {
//        response.sendRedirect(request.getContextPath() + "/login.xhtml");
//    }
%>
