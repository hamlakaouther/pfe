package dz.elit.pfe.commun.util;

import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.commun.controller.MySessionController;
import java.security.Principal;
import jakarta.faces.application.Application;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author chekor.samir
 */
public class AllPagesListener implements PhaseListener {

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        Principal loginUser = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        MySessionController mySessionController = null;
        if (loginUser != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            if (session != null) {
                mySessionController = (MySessionController) session.getAttribute("mySessionController");
            }
            AdminUtilisateur utilisateur = null;
            if (mySessionController != null) {
                try {
                    utilisateur = (AdminUtilisateur) mySessionController.getUtilisateurCourant();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if (utilisateur != null) {
//                    if (!utilisateur.isIsPwdChanged()) {
//                        Application application = context.getApplication();
//                        ViewHandler viewHandler = application.getViewHandler();
//                        UIViewRoot viewRoot = viewHandler.createView(context, "/pages/commun/changePwdUtilisateur.xhtml");
//                        context.setViewRoot(viewRoot);
//                    }
//                }
            }
        }

    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }
}
