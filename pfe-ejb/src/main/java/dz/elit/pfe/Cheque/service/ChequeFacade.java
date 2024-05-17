/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dz.elit.pfe.Cheque.service;

import dz.elit.pfe.Cheque.entity.Cheque;
import dz.elit.pfe.commun.exception.MyException;
import dz.elit.pfe.commun.service.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.persistence.CacheStoreMode;
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
        query.setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.REFRESH);
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
    
    public List<Cheque> findAllCheque() {
        Query query = em.createNamedQuery("Cheque.findAll");
        /*Query query = em.createNamedQuery("AbstractFacade.findAll");*/
        return query.getResultList();
    }
    
    /*public List<Cheque> findChequeByAttributes(Integer id, String numCheque, String nomCheque, BigDecimal montant, LocalDateTime dateCreation, String rip, String etat, String numFacture, String codeClient, BigDecimal montantFacture) {

        StringBuilder queryStringBuilder = new StringBuilder("SELECT c FROM Cheque AS c WHERE 1=1 ");

        if (id != null) {
            queryStringBuilder.append(" AND c.id = :id ");
        }
        if (numCheque != null && !numCheque.equals("")) {
            queryStringBuilder.append(" AND c.numCheque like :numCheque ");
        }
        if (nomCheque != null && !nomCheque.equals("")) {
            queryStringBuilder.append(" AND c.nomCheque like :nomCheque ");
        }
        if (montant != null) {
            queryStringBuilder.append(" AND c.montant = :montant ");
        }
        if (dateCreation != null) {
            queryStringBuilder.append(" AND c.dateCreation = :dateCreation ");
        }
        if (rip != null && !rip.equals("")) {
            queryStringBuilder.append(" AND c.rip like :rip ");
        }
        if (etat != null && !etat.equals("")) {
            queryStringBuilder.append(" AND c.etat like :etat ");
        }
        if (numFacture != null && !numFacture.equals("")) {
            queryStringBuilder.append(" AND c.numFacture like :numFacture ");
        }
        if (codeClient != null && !codeClient.equals("")) {
            queryStringBuilder.append(" AND c.codeClient like :codeClient ");
        }
        if (montantFacture != null) {
            queryStringBuilder.append(" AND c.montantFacture = :montantFacture ");
        }

        queryStringBuilder.append(" ORDER BY c.rip ");

        Query q = em.createQuery(queryStringBuilder.toString());

        if (id != null) {
            q.setParameter("id", id);
        }
        if (numCheque != null && !numCheque.equals("")) {
            q.setParameter("numCheque", numCheque + "%");
        }
        if (nomCheque != null && !nomCheque.equals("")) {
            q.setParameter("nomCheque", nomCheque + "%");
        }
        if (montant != null) {
            q.setParameter("montant", montant);
        }
        if (dateCreation != null) {
            q.setParameter("dateCreation", dateCreation);
        }
        if (rip != null && !rip.equals("")) {
            q.setParameter("rip", rip + "%");
        }
        if (etat != null && !etat.equals("")) {
            q.setParameter("etat", etat + "%");
        }
        if (numFacture != null && !numFacture.equals("")) {
            q.setParameter("numFacture", numFacture + "%");
        }
        if (codeClient != null && !codeClient.equals("")) {
            q.setParameter("codeClient", codeClient + "%");
        }
        if (montantFacture != null) {
            q.setParameter("montantFacture", montantFacture);
        }

        return q.getResultList();
    }*/
    
    @Override
    public void remove(Cheque cheque) {
        super.remove(cheque);
    }
}

