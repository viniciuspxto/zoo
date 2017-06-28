/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.RegistroClinico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Vacina;

/**
 *
 * @author viniciuspeixoto
 */
public class VacinaJpaController implements Serializable {

    public VacinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vacina vacina) {
        if (vacina.getRegistroClinicoList() == null) {
            vacina.setRegistroClinicoList(new ArrayList<RegistroClinico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RegistroClinico> attachedRegistroClinicoList = new ArrayList<RegistroClinico>();
            for (RegistroClinico registroClinicoListRegistroClinicoToAttach : vacina.getRegistroClinicoList()) {
                registroClinicoListRegistroClinicoToAttach = em.getReference(registroClinicoListRegistroClinicoToAttach.getClass(), registroClinicoListRegistroClinicoToAttach.getId());
                attachedRegistroClinicoList.add(registroClinicoListRegistroClinicoToAttach);
            }
            vacina.setRegistroClinicoList(attachedRegistroClinicoList);
            em.persist(vacina);
            for (RegistroClinico registroClinicoListRegistroClinico : vacina.getRegistroClinicoList()) {
                Vacina oldVacinaOfRegistroClinicoListRegistroClinico = registroClinicoListRegistroClinico.getVacina();
                registroClinicoListRegistroClinico.setVacina(vacina);
                registroClinicoListRegistroClinico = em.merge(registroClinicoListRegistroClinico);
                if (oldVacinaOfRegistroClinicoListRegistroClinico != null) {
                    oldVacinaOfRegistroClinicoListRegistroClinico.getRegistroClinicoList().remove(registroClinicoListRegistroClinico);
                    oldVacinaOfRegistroClinicoListRegistroClinico = em.merge(oldVacinaOfRegistroClinicoListRegistroClinico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vacina vacina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacina persistentVacina = em.find(Vacina.class, vacina.getId());
            List<RegistroClinico> registroClinicoListOld = persistentVacina.getRegistroClinicoList();
            List<RegistroClinico> registroClinicoListNew = vacina.getRegistroClinicoList();
            List<RegistroClinico> attachedRegistroClinicoListNew = new ArrayList<RegistroClinico>();
            for (RegistroClinico registroClinicoListNewRegistroClinicoToAttach : registroClinicoListNew) {
                registroClinicoListNewRegistroClinicoToAttach = em.getReference(registroClinicoListNewRegistroClinicoToAttach.getClass(), registroClinicoListNewRegistroClinicoToAttach.getId());
                attachedRegistroClinicoListNew.add(registroClinicoListNewRegistroClinicoToAttach);
            }
            registroClinicoListNew = attachedRegistroClinicoListNew;
            vacina.setRegistroClinicoList(registroClinicoListNew);
            vacina = em.merge(vacina);
            for (RegistroClinico registroClinicoListOldRegistroClinico : registroClinicoListOld) {
                if (!registroClinicoListNew.contains(registroClinicoListOldRegistroClinico)) {
                    registroClinicoListOldRegistroClinico.setVacina(null);
                    registroClinicoListOldRegistroClinico = em.merge(registroClinicoListOldRegistroClinico);
                }
            }
            for (RegistroClinico registroClinicoListNewRegistroClinico : registroClinicoListNew) {
                if (!registroClinicoListOld.contains(registroClinicoListNewRegistroClinico)) {
                    Vacina oldVacinaOfRegistroClinicoListNewRegistroClinico = registroClinicoListNewRegistroClinico.getVacina();
                    registroClinicoListNewRegistroClinico.setVacina(vacina);
                    registroClinicoListNewRegistroClinico = em.merge(registroClinicoListNewRegistroClinico);
                    if (oldVacinaOfRegistroClinicoListNewRegistroClinico != null && !oldVacinaOfRegistroClinicoListNewRegistroClinico.equals(vacina)) {
                        oldVacinaOfRegistroClinicoListNewRegistroClinico.getRegistroClinicoList().remove(registroClinicoListNewRegistroClinico);
                        oldVacinaOfRegistroClinicoListNewRegistroClinico = em.merge(oldVacinaOfRegistroClinicoListNewRegistroClinico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vacina.getId();
                if (findVacina(id) == null) {
                    throw new NonexistentEntityException("The vacina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacina vacina;
            try {
                vacina = em.getReference(Vacina.class, id);
                vacina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vacina with id " + id + " no longer exists.", enfe);
            }
            List<RegistroClinico> registroClinicoList = vacina.getRegistroClinicoList();
            for (RegistroClinico registroClinicoListRegistroClinico : registroClinicoList) {
                registroClinicoListRegistroClinico.setVacina(null);
                registroClinicoListRegistroClinico = em.merge(registroClinicoListRegistroClinico);
            }
            em.remove(vacina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vacina> findVacinaEntities() {
        return findVacinaEntities(true, -1, -1);
    }

    public List<Vacina> findVacinaEntities(int maxResults, int firstResult) {
        return findVacinaEntities(false, maxResults, firstResult);
    }

    private List<Vacina> findVacinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacina.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vacina findVacina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacina.class, id);
        } finally {
            em.close();
        }
    }

    public int getVacinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vacina> rt = cq.from(Vacina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
