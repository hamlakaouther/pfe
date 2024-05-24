/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Bordeuraux.service;

import dz.elit.pfe.Bordeuraux.entity.BordereauDirection;
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
public class BordereauDirectionFacade extends AbstractFacade<BordereauDirection> {
    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em; /* em = EntityManager */

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public BordereauDirectionFacade() {
        super(BordereauDirection.class);
    }
    
    public BordereauDirection findBordereauDirectionById(int id) {
        Query query = em.createNamedQuery("BordereauDirection.findById");
        query.setParameter("id", id);
        List<BordereauDirection> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    public BordereauDirection findBordereauDirectionByCode(String code) {
        Query query = em.createNamedQuery("BordereauDirection.findByCode");
        query.setParameter("code", code);
        List<BordereauDirection> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    public List<BordereauDirection> findAllBordereauDirection() {
        Query query = em.createNamedQuery("BordereauDirection.findAll");
        return query.getResultList();
    }

    private boolean isBordereauDirectionExists(int id) {
        return findBordereauDirectionById(id) != null;
    }

    public void createBordereauDirection(BordereauDirection bordereauDirection) throws MyException {
        if (isBordereauDirectionExists(bordereauDirection.getId())) {
            throw new MyException("Le BordereauDirection " + bordereauDirection.getId() + " existe déjà ");
        } else {
            super.create(bordereauDirection);
        }
    }

    public void editBordereauDirection(BordereauDirection bordereauDirection) {
        super.edit(bordereauDirection);
    }

    @Override
    public void remove(BordereauDirection bordereauDirection) {
        super.remove(bordereauDirection);
    }
}

