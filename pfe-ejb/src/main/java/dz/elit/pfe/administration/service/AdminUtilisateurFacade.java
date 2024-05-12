/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.administration.service;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.commun.exception.MyException;
import dz.elit.pfe.commun.service.AbstractFacade;
import java.util.Date;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 *
 */
@Stateless
public class AdminUtilisateurFacade extends AbstractFacade<AdminUtilisateur> {

    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminUtilisateurFacade() {
        super(AdminUtilisateur.class);
    }

    public List<AdminUtilisateur> findUtilisateursByUniteOrg(String codeUniteOrg) {
        Query query = em.createNamedQuery("AdminUtilisateur.findUtilisateursByUniteOrg");
        query.setParameter("codeUniteOrg", codeUniteOrg);
        return query.getResultList();
    }

    public AdminUtilisateur findUtilisateursById(int id) {
        Query query = em.createNamedQuery("AdminUtilisateur.findById");
        query.setParameter("id", id);
        List<AdminUtilisateur> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public AdminUtilisateur findByLogin(String login) {
        Query query = em.createNamedQuery("AdminUtilisateur.findByLogin");
        query.setParameter("login", login);
        query.setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        List<AdminUtilisateur> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public AdminUtilisateur findByCodeAgent(String codeAgent) {
        Query query = em.createNamedQuery("AdminUtilisateur.findByCodeAgent");
        query.setParameter("codeAgent", codeAgent);
        List<AdminUtilisateur> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<AdminUtilisateur> findByNomPrenomLogin(String nom, String prenom, String login, AdminUniteOrganisationnelle uniteOrganisationnelleSelected) {
        StringBuilder queryStringBuilder = new StringBuilder("SELECT a FROM AdminUtilisateur AS a WHERE 1=1 ");
        if (nom != null && !nom.equals("")) {
            queryStringBuilder.append(" AND  a.nom like :nom ");
        }
        if (prenom != null && !prenom.equals("")) {
            queryStringBuilder.append(" AND  a.prenom like :prenom ");
        }
        if (login != null && !login.equals("")) {
            queryStringBuilder.append(" AND  a.login like :login ");
        }
        if (uniteOrganisationnelleSelected != null && uniteOrganisationnelleSelected.getCode() != null) {
            queryStringBuilder.append(" AND  a.adminUniteOrganisationnelle = :uniteOrganisationnelleSelected ");
        }
        queryStringBuilder.append(" ORDER BY a.login ");

        Query q = em.createQuery(queryStringBuilder.toString());

        if (nom != null && !nom.equals("")) {
            q.setParameter("nom", nom + "%");
        }
        if (prenom != null && !prenom.equals("")) {
            q.setParameter("prenom", prenom + "%");
        }
        if (login != null && !login.equals("")) {
            q.setParameter("login", login + "%");
        }
        if (uniteOrganisationnelleSelected != null && uniteOrganisationnelleSelected.getCode() != null) {
            q.setParameter("uniteOrganisationnelleSelected", uniteOrganisationnelleSelected);
        }
        return q.getResultList();
    }

    private boolean isExisteLogin(String login) {
        AdminUtilisateur utilisateur = findByLogin(login);
        if (utilisateur == null) {
            return false;
        } else {
            return true;
        }
    }

    public void createUtilisateur(AdminUtilisateur utilisateur) throws MyException {
        if (isExisteLogin(utilisateur.getLogin())) {
            throw new MyException("Le login " + utilisateur.getLogin() + " existe déjà ");
        } else {
            utilisateur.setCodeAgent(returnMaxCodeAgent());
            super.create(utilisateur);
        }
    }

    public void editUtilisateur(AdminUtilisateur utilisateur) {
        super.edit(utilisateur);
    }

    @Override
    public void remove(AdminUtilisateur utilisateur) {
        super.remove(utilisateur);
    }

    public List<AdminUtilisateur> findAllOrderByUnite() {
        Query query = em.createNamedQuery("AdminUtilisateur.findAll");
        return query.getResultList();
    }

    public void findByIdWithHintGroupe(List<Integer> groupeUserID) {
        Query query = em.createNamedQuery("AdminGroupe.findByListId");
        query.setParameter("listId", groupeUserID);
        query.setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.REFRESH);

    }

    public String returnMaxCodeAgent() {
        Query q = em.createNamedQuery("AdminUtilisateur.findMaxCodeAgent");
        String i = (String) q.getSingleResult();
        int code = i != null ? Integer.parseInt(i) + 1 : 1;
        String newCode = "00000000000000".substring(0, 5 - (code + "").length()) + code;
        return newCode;
    }

    public List<Object[]> chargerDetailStatUser(List<Integer> UOStringList, Date start, Date end) {
        Query query = getEntityManager().createQuery("SELECT uo , count (u) "
                + "FROM AdminUtilisateur u LEFT JOIN u.adminUniteOrganisationnelle uo "
                + "WHERE uo.id in :uolist "
                + "GROUP BY uo.id");
        query.setParameter("uolist", UOStringList);
        return query.getResultList();
    }

    public void editUtilisateurModeSelectionEncaissement(Integer id, boolean modeSelectionEncaissement) {
        Query query = em.createQuery("UPDATE AdminUtilisateur a SET a.modeSelectionEncaissement= :modeSelectionEncaissement WHERE a.id = :id");
        query.setParameter("modeSelectionEncaissement", modeSelectionEncaissement);
        query.setParameter("id", id);
        query.executeUpdate();

    }
}
