/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Banque.service;

import dz.elit.pfe.Banque.entity.Banque;
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
public class BanqueFacade extends AbstractFacade<Banque> {
    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em; /* em = EntityManager */

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public BanqueFacade() {
        super(Banque.class);
    }
    
    public Banque findBanqueById(int id) {
        Query query = em.createNamedQuery("Banque.findById");
        query.setParameter("id", id);
        List<Banque> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    public Banque findBanqueByCode(String code) {
        Query query = em.createNamedQuery("Banque.findByCode");
        query.setParameter("code", code);
        List<Banque> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
    
    public Banque findBanqueByNom(String nom) {
        Query query = em.createNamedQuery("Banque.findByNom");
        query.setParameter("nom", nom);
        List<Banque> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
     
    public boolean isBanqueExists(int id) {
        return findBanqueById(id) != null;
    }

    public void createBanque(Banque banque) throws MyException {
        if (isBanqueExists(banque.getId())) {
            throw new MyException("La Banque " + banque.getId() + " existe déjà ");
        } else {
            super.create(banque);
        }
    }

    public void editBanque(Banque banque) {
        super.edit(banque);
    }

    @Override
    public void remove(Banque banque) {
        super.remove(banque);
    }
     public List<Banque> findAllBanque() {
        Query query = em.createNamedQuery("Banque.findAll");
        /*Query query = em.createNamedQuery("AbstractFacade.findAll");*/
        return query.getResultList();
    }
    
}