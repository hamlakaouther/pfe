/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.administration.service;

import dz.elit.pfe.administration.entity.AdminUniteOrganisationnelle;
import dz.elit.pfe.administration.entity.AdminUtilisateur;
import dz.elit.pfe.commun.service.AbstractFacade;
import dz.elit.pfe.enums.NiveauUnite;
import dz.elit.pfe.enums.TypeUniteOrganisationnelle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 * @author leghettas.rabah
 */
@Stateless
public class AdminUniteOrganisationnelleFacade extends AbstractFacade<AdminUniteOrganisationnelle> {

    @PersistenceContext(unitName = "pfe-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private AdminUniteOrganisationnelle societe;

    public AdminUniteOrganisationnelleFacade() {
        super(AdminUniteOrganisationnelle.class);
    }

    public AdminUniteOrganisationnelle getSociete() {
        return societe;
    }

    public void setSociete(AdminUniteOrganisationnelle societe) {
        this.societe = societe;
    }

    @Override
    public void create(AdminUniteOrganisationnelle uniteOrg) {
        super.create(uniteOrg);
    }

    public AdminUniteOrganisationnelle findByCode(String code) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByCode");
        query.setParameter("code", code);
        List<AdminUniteOrganisationnelle> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public AdminUniteOrganisationnelle findByCodeUniteSgc(String codeUniteSgc) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByCodeUniteSgc");
        query.setParameter("codeUniteSgc", codeUniteSgc);
        List<AdminUniteOrganisationnelle> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<AdminUniteOrganisationnelle> findByCodeUO(String code) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByCode");
        query.setParameter("code", code);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findUniteOrg() {
        Query query = em.createQuery("Select DISTINCT(c.idUniteOrganisationnelle) from AdminDroitVisibilite c where c.idObjetVisibilite.entity = 'AdminUniteOrganisationnelle' ORDER BY c.idUniteOrganisationnelle.trie");
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByTrie() {
        return this.findAllOrderByAttribut("trie", false);
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByTrieAsc() {
        return this.findAllOrderByAttributAsc("trie", false);
    }

    public List<AdminUniteOrganisationnelle> findAllChildsOrderByTrie(AdminUniteOrganisationnelle adminUniteOrg) {
        return this.getAllChilds(adminUniteOrg);
    }

    @Override
    public List<AdminUniteOrganisationnelle> findAll() {
        Query query = em.createQuery("Select c from AdminUniteOrganisationnelle c order by c.denominationFr ");
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findOnlyAllChilds(AdminUniteOrganisationnelle adminUniteOrg) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findOnlyAllChilds");
        query.setParameter("adminUniteOrg", adminUniteOrg);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByNiveau(int niveau) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByNiveauEqualEtSup");
        query.setParameter("niveau", niveau);
        return query.getResultList();

    }

    public List<AdminUniteOrganisationnelle> findAllOrderByNiv(int niveau) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByNiveauEqual");
        query.setParameter("niveau", niveau);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByNivEtUniteParent(int niveau, String uniteParent) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByNivEqualEtUniteParent");
        query.setParameter("niveau", niveau);
        query.setParameter("uniteParent", uniteParent);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByUniteParent(String uniteParent) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByUniteParent");
        query.setParameter("uniteParent", uniteParent);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByNivEtUniteParentUniteParent(int niveau, String uniteParent) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByNivEqualEtUniteParentUniteParent");
        query.setParameter("niveau", niveau);
        query.setParameter("uniteParent", uniteParent);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByNiveauEtUniteParent(int niveau, String uniteParent) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findByNiveauEqualEtSupEtUniteParent");
        query.setParameter("niveau", niveau);
        query.setParameter("uniteParent", uniteParent);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findAllOrderByNiveau2EtUniteParent(String uniteParent, String uniteParent2) {
        Query query = em.createNamedQuery("AdminUniteOrganisationnelle.findAllOrderByNiveau2EtUniteParent");
//        query.setParameter("niveau", niveau);
        query.setParameter("uniteParent", uniteParent);
        query.setParameter("uniteParent2", uniteParent2);
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> findUniteOrganisationnelleByNiveau(AdminUtilisateur adminUtilisateur) {
        List<AdminUniteOrganisationnelle> listUniteOrganisationnelles;
        listUniteOrganisationnelles = new ArrayList<>();

        societe = new AdminUniteOrganisationnelle();
        societe = adminUtilisateur.getAdminUniteOrganisationnelle();

        if (societe.getNiveau() == NiveauUnite.AGENCE) {
            String codeUO = societe.getCode();
            listUniteOrganisationnelles = findByCodeUO(codeUO);
        }
        if (societe.getNiveau() == NiveauUnite.DD) {
            String uniteParent = adminUtilisateur.getAdminUniteOrganisationnelle().getCode();
            String uniteParent2 = uniteParent;
            listUniteOrganisationnelles = findAllOrderByNiveau2EtUniteParent(uniteParent, uniteParent2);
        }
        if (societe.getNiveau() == NiveauUnite.POLE) {
            listUniteOrganisationnelles = findAllOrderByNiveau(4);
        }
        if (societe.getNiveau() == NiveauUnite.SOCIETE) {
            listUniteOrganisationnelles = findAllOrderByNiveau(2);
        }
        if (societe.getNiveau() == NiveauUnite.MM) {
            listUniteOrganisationnelles = findAllOrderByTrie();
        }
        return listUniteOrganisationnelles;
    }

    public List<AdminUniteOrganisationnelle> findUniteOrganisationnelleByNiv(AdminUtilisateur adminUtilisateur) {
        List<AdminUniteOrganisationnelle> listUniteOrganisationnelles;
        listUniteOrganisationnelles = new ArrayList<>();

        societe = new AdminUniteOrganisationnelle();
        societe = adminUtilisateur.getAdminUniteOrganisationnelle();

        if (societe.getNiveau() == NiveauUnite.MM) {
            listUniteOrganisationnelles = findAllOrderByNiv(2);
        }
        return listUniteOrganisationnelles;
    }

    public List<AdminUniteOrganisationnelle> getAllChilds(AdminUniteOrganisationnelle adminUniteOrganisationnelle) {
        List<AdminUniteOrganisationnelle> listAllChild = new ArrayList<>();
        listAllChild.add(adminUniteOrganisationnelle);
        for (AdminUniteOrganisationnelle auo : adminUniteOrganisationnelle.getAdminUniteOrganisationnelleList()) {
            {
                listAllChild.addAll(getAllChilds(auo));
            }
        }
        return listAllChild;
    }

    public List<AdminUniteOrganisationnelle> getAllParents(AdminUniteOrganisationnelle adminUniteOrganisationnelle) {
        List<AdminUniteOrganisationnelle> listAllParents = new ArrayList();
        listAllParents.add(adminUniteOrganisationnelle);
        if (adminUniteOrganisationnelle.getUniteParent() != null) {
            listAllParents.addAll(getAllParents(adminUniteOrganisationnelle.getUniteParent()));
        }
        return listAllParents;
    }

    public String getFilialeFromUniteOrganisationnelle(Integer idUniteOrganisationnelle) {
        AdminUniteOrganisationnelle uniteOrganisationnelle = find(idUniteOrganisationnelle);
        switch (uniteOrganisationnelle.getTypeUniteOrganisationnelle()) {
            case societe:
                return uniteOrganisationnelle.getCodeUniteSgc();
            default:
                return getFilialeFromUniteOrganisationnelle(uniteOrganisationnelle.getUniteParent());
        }
    }

    public List<AdminUniteOrganisationnelle> getAllDDChilds(AdminUniteOrganisationnelle adminUniteOrganisationnelle) {
        List<AdminUniteOrganisationnelle> listAllChild = new ArrayList<>();
        if (adminUniteOrganisationnelle.getTypeUniteOrganisationnelle().equals("dd")) {
            listAllChild.add(adminUniteOrganisationnelle);
        } else {
            for (AdminUniteOrganisationnelle auo : adminUniteOrganisationnelle.getAdminUniteOrganisationnelleList()) {
                {
                    listAllChild.addAll(getAllDDChilds(auo));
                }
            }
        }

        return listAllChild;
    }

    public List<AdminUniteOrganisationnelle> getAllAgenceChilds(AdminUniteOrganisationnelle adminUniteOrganisationnelle) {
        List<AdminUniteOrganisationnelle> listAllChild = new ArrayList<>();
        if (adminUniteOrganisationnelle.getTypeUniteOrganisationnelle().equals("agence")) {
            listAllChild.add(adminUniteOrganisationnelle);
        } else {
            for (AdminUniteOrganisationnelle auo : adminUniteOrganisationnelle.getAdminUniteOrganisationnelleList()) {
                {
                    listAllChild.addAll(getAllDDChilds(auo));
                }
            }
        }

        return listAllChild;
    }

    public String getFilialeFromUniteOrganisationnelle(AdminUniteOrganisationnelle uniteOrganisationnelle) {
        switch (uniteOrganisationnelle.getTypeUniteOrganisationnelle()) {
            case societe:
                return uniteOrganisationnelle.getCodeUniteSgc();
            default:
                return getFilialeFromUniteOrganisationnelle(uniteOrganisationnelle.getUniteParent());
        }
    }

    public List<AdminUniteOrganisationnelle> findAllAgenceAndAllDD() {
        Query query = em.createQuery("SELECT a FROM AdminUniteOrganisationnelle a WHERE a.typeUniteOrganisationnelle = :type1 OR a.typeUniteOrganisationnelle = :type2 ORDER BY a.codeUniteSgc");
        query.setParameter("type1", TypeUniteOrganisationnelle.agence.name());
        query.setParameter("type2", TypeUniteOrganisationnelle.dd.name());
        return query.getResultList();
    }

    public List<AdminUniteOrganisationnelle> getAllNotAgenceChilds(AdminUniteOrganisationnelle adminUniteOrganisationnelle) {
        List<AdminUniteOrganisationnelle> listAllChild = new ArrayList<>();
        if (!adminUniteOrganisationnelle.getTypeUniteOrganisationnelle().equals("agence")) {
            listAllChild.add(adminUniteOrganisationnelle);
        }
        for (AdminUniteOrganisationnelle auo : adminUniteOrganisationnelle.getAdminUniteOrganisationnelleList()) {
            {
                listAllChild.addAll(getAllNotAgenceChilds(auo));
            }

        }

        return listAllChild;
    }

    public List<AdminUniteOrganisationnelle> findAllDD(Boolean whithPoleAndSDC) {
        List<String> type = new ArrayList();
        type.add(TypeUniteOrganisationnelle.dd.name());
        if (whithPoleAndSDC) {
            type.add(TypeUniteOrganisationnelle.pole.name());
            type.add(TypeUniteOrganisationnelle.societe.name());
        }
        Query query = em.createQuery("SELECT a FROM AdminUniteOrganisationnelle a WHERE a.typeUniteOrganisationnelle IN :type ORDER BY a.niveau, a.code");
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<Integer> getUniteId(List<AdminUniteOrganisationnelle> listUnite) {
        return listUnite.stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
    }
    
    public boolean isUniteExists(String code) {
        return findByCode(code) != null;
    }
    
    public void editUnite(AdminUniteOrganisationnelle Unite) {
        super.edit(Unite);
    }
}
