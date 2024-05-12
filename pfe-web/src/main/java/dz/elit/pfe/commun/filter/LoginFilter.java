/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.filter;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author xps
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/login.xhtml"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.REQUEST})
public class LoginFilter implements Filter {

    private static final int MINUTEBLOCAGE = 2;

    /**
     *
     */
    public static final int NBRETENTAVIE = 3;

    /**
     *
     */
    public static final String DATEBLOCAGENAME = "dateBlocage";
    public static final String VERROU_PAGE = "/pages/erreur/verrou.xhtml";
    public static final String ATTRIBUT_LOGIN_ERREUR = "login_error";
    public static final String ATTRIBUT_NBR_TENTATIVE = "nbrTentative";
    public static final String WELCOME_PAGE = "/index.xhtml";
    public static final String LOGIN_PAGE = "/login.xhtml";
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    /**
     *
     */
    public LoginFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = (HttpSession) httpRequest.getSession();
        Principal loginUser = httpRequest.getUserPrincipal();

//        if (loginUser != null) {
////            System.out.println("httpRequest.isUserInRole(PAS_PWD_CHANGED)---------------------- = " + httpRequest.isUserInRole("PAS_PWD_CHANGED"));
//            if (httpRequest.isUserInRole("INACTIF")) {
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/pages/erreur/inactif.xhtml");
//            } else if (httpRequest.isUserInRole("EXPIRE")) {
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/pages/erreur/expire.xhtml");
//            } else if (httpRequest.isUserInRole("PAS_ENCORS_ACTIF")) {
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/pages/erreur/pasEncorsActif.xhtml");
//            } else if (httpRequest.isUserInRole("PAS_PWD_CHANGED")) {
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/pages/commun/changePwdUtilisateur.xhtml");
//            }  else {
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/pages/administration/indexAdmin.xhtml");
//            }
//        } else {
//            Object object = session.getAttribute(LoginFilter.DATEBLOCAGENAME);
//            if (object == null || !(object instanceof Date)) {
//                chain.doFilter(request, response);
//            } else {
//                Date date = (Date) object;
//                Calendar c = Calendar.getInstance();
//                c.setTime(date);
//                c.add(Calendar.MINUTE, LoginFilter.MINUTEBLOCAGE);
//                Date newDate = new Date();
//                if (c.getTime().after(newDate)) {
//                    httpResponse.sendRedirect(httpRequest.getContextPath() + VERROU_PAGE);
//                } else {                  
//                    chain.doFilter(request, response);
//                }
//            }
//        }
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {

    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }


    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("LoginFilter()");
        }
        StringBuffer sb = new StringBuffer("LoginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    /**
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    /**
     *
     * @param msg
     */
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
