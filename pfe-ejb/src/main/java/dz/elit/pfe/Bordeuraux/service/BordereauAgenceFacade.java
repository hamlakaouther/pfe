/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Bordeuraux.service;

import dz.elit.pfe.Bordeuraux.entity.BordereauAgence;
import dz.elit.pfe.commun.exception.MyException;
import dz.elit.pfe.commun.service.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author xps
 */
@Stateless
public class BordereauAgenceFacade extends AbstractFacade<BordereauAgence> {
    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em; /* em = EntityManager */

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public BordereauAgenceFacade() {
        super(BordereauAgence.class);
    }
    
    public BordereauAgence findBordereauAgenceById(int id) {
        Query query = em.createNamedQuery("BordereauAgence.findById");
        query.setParameter("id", id);
        List<BordereauAgence> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    public BordereauAgence findBordereauAgenceByCode(String code) {
        Query query = em.createNamedQuery("BordereauAgence.findByCode");
        query.setParameter("code", code);
        List<BordereauAgence> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    private boolean isBordereauAgenceExists(int id) {
        return findBordereauAgenceById(id) != null;
    }

    public void createBordereauAgence(BordereauAgence bordereauAgence) throws MyException {
        if (isBordereauAgenceExists(bordereauAgence.getId())) {
            throw new MyException("Le BordereauAgence " + bordereauAgence.getId() + " existe déjà ");
        } else {
            super.create(bordereauAgence);
        }
    }

    public void editBordereauAgence(BordereauAgence bordereauAgence) {
        super.edit(bordereauAgence);
    }
     
    public List<BordereauAgence> findAllBordereauAgence() {
        Query query = em.createNamedQuery("BordereauAgence.findAll");
        return query.getResultList();
    }

    @Override
    public void remove(BordereauAgence bordereauAgence) {
        super.remove(bordereauAgence);
    }
}
