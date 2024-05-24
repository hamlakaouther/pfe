/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Caisse.service;

import dz.elit.pfe.Caisse.entity.Caisse;
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
public class CaisseFacade extends AbstractFacade<Caisse> {
    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em; /* em = EntityManager */

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public CaisseFacade() {
        super(Caisse.class);
    }
    
    public Caisse findCaisseById(int id) {
        Query query = em.createNamedQuery("Caisse.findById");
        query.setParameter("id", id);
        List<Caisse> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    public Caisse findCaisseByCode(String code) {
        Query query = em.createNamedQuery("Caisse.findByCode");
        query.setParameter("code", code);
        List<Caisse> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    private boolean isCaisseExists(int id) {
        return findCaisseById(id) != null;
    }

    public void createCaisse(Caisse caisse) throws MyException {
        if (isCaisseExists(caisse.getId())) {
            throw new MyException("La Caisse " + caisse.getId() + " existe déjà ");
        } else {
            super.create(caisse);
        }
    }

    public void editCaisse(Caisse caisse) {
        super.edit(caisse);
    }
     public List<Caisse> findAllCaisse() {
        Query query = em.createNamedQuery("Caisse.findAll");
        /*Query query = em.createNamedQuery("AbstractFacade.findAll");*/
        return query.getResultList();
    }

    @Override
    public void remove(Caisse caisse) {
        super.remove(caisse);
    }
    
    
}