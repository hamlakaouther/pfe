/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Cheque.service;

import dz.elit.pfe.Cheque.entity.Cheque;
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
public class ChequeFacade extends AbstractFacade<Cheque> {
    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em; /* em = EntityManager */

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ChequeFacade() {
        super(Cheque.class);
    }
    
    public Cheque findChequeById(int id) {
        Query query = em.createNamedQuery("Cheque.findById");
        query.setParameter("id", id);
        List<Cheque> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
        /* EXPLICATION
        ~~Vérifie si la liste est vide~~
        list.isEmpty(): --> Condition

        ~~Opérateur ternaire : sépare la condition des valeurs à retourner en fonction de celle-ci~~
        ?: --> Opérateur ternaire

        ~~Retourne null si la liste est vide~~
        null: --> Retour si vrai

        ~~Retourne le premier élément de la liste si elle n'est pas vide~~
        : --> Retour si faux
        list.get(0): --> Récupère le premier élément
        */
    
    public Cheque findChequeByRip(String rip) {
        Query query = em.createNamedQuery("Cheque.findByRip");
        query.setParameter("rip", rip);
        List<Cheque> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
    
    /*public List<Cheque> findAll() {
        Query query = em.createNamedQuery("Cheque.findAll");
        return query.getResultList();
    }*/
    
    private boolean isChequeExists(int id) {
        return findChequeById(id) != null;
    }

    public void createCheque(Cheque cheque) throws MyException {
        if (isChequeExists(cheque.getId())) {
            throw new MyException("Le Cheque " + cheque.getId() + " existe déjà ");
        } else {
            super.create(cheque);
        }
    }

    public void editCheque(Cheque cheque) {
        super.edit(cheque);
    }

    @Override
    public void remove(Cheque cheque) {
        super.remove(cheque);
    }
}

