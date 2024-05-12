/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 
 * scop application pour les information static
 */
@Named /* (name = "myApplicationController", eager = true) */
@ApplicationScoped
@Getter
@Setter
public class MyApplicationController  implements Serializable {
    
    private Map<String, String> themes;
    private List<MySessionController> listMySessionController = new ArrayList();

    public MyApplicationController() {
    }
@PostConstruct
    protected void initController() {
    }
   
   

    public void removeMySessionController(MySessionController mySessionController) {
        listMySessionController.remove(mySessionController);
    }

    public void logoutMySessionController(MySessionController mySessionController) {
        mySessionController.logoutUtilisateur();
    }
}
