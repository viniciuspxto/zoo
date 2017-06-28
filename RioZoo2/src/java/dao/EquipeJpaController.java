/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Tratador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Equipe;

/**
 *
 * @author viniciuspeixoto
 */
public class EquipeJpaController implements Serializable {

    public EquipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipe equipe) {
        if (equipe.getTratadorList() == null) {
            equipe.setTratadorList(new ArrayList<Tratador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tratador> attachedTratadorList = new ArrayList<Tratador>();
            for (Tratador tratadorListTratadorToAttach : equipe.getTratadorList()) {
                tratadorListTratadorToAttach = em.getReference(tratadorListTratadorToAttach.getClass(), tratadorListTratadorToAttach.getId());
                attachedTratadorList.add(tratadorListTratadorToAttach);
            }
            equipe.setTratadorList(attachedTratadorList);
            em.persist(equipe);
            for (Tratador tratadorListTratador : equipe.getTratadorList()) {
                Equipe oldEquipeOfTratadorListTratador = tratadorListTratador.getEquipe();
                tratadorListTratador.setEquipe(equipe);
                tratadorListTratador = em.merge(tratadorListTratador);
                if (oldEquipeOfTratadorListTratador != null) {
                    oldEquipeOfTratadorListTratador.getTratadorList().remove(tratadorListTratador);
                    oldEquipeOfTratadorListTratador = em.merge(oldEquipeOfTratadorListTratador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipe equipe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe persistentEquipe = em.find(Equipe.class, equipe.getId());
            List<Tratador> tratadorListOld = persistentEquipe.getTratadorList();
            List<Tratador> tratadorListNew = equipe.getTratadorList();
            List<String> illegalOrphanMessages = null;
            for (Tratador tratadorListOldTratador : tratadorListOld) {
                if (!tratadorListNew.contains(tratadorListOldTratador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tratador " + tratadorListOldTratador + " since its equipe field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tratador> attachedTratadorListNew = new ArrayList<Tratador>();
            for (Tratador tratadorListNewTratadorToAttach : tratadorListNew) {
                tratadorListNewTratadorToAttach = em.getReference(tratadorListNewTratadorToAttach.getClass(), tratadorListNewTratadorToAttach.getId());
                attachedTratadorListNew.add(tratadorListNewTratadorToAttach);
            }
            tratadorListNew = attachedTratadorListNew;
            equipe.setTratadorList(tratadorListNew);
            equipe = em.merge(equipe);
            for (Tratador tratadorListNewTratador : tratadorListNew) {
                if (!tratadorListOld.contains(tratadorListNewTratador)) {
                    Equipe oldEquipeOfTratadorListNewTratador = tratadorListNewTratador.getEquipe();
                    tratadorListNewTratador.setEquipe(equipe);
                    tratadorListNewTratador = em.merge(tratadorListNewTratador);
                    if (oldEquipeOfTratadorListNewTratador != null && !oldEquipeOfTratadorListNewTratador.equals(equipe)) {
                        oldEquipeOfTratadorListNewTratador.getTratadorList().remove(tratadorListNewTratador);
                        oldEquipeOfTratadorListNewTratador = em.merge(oldEquipeOfTratadorListNewTratador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipe.getId();
                if (findEquipe(id) == null) {
                    throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe equipe;
            try {
                equipe = em.getReference(Equipe.class, id);
                equipe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tratador> tratadorListOrphanCheck = equipe.getTratadorList();
            for (Tratador tratadorListOrphanCheckTratador : tratadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipe (" + equipe + ") cannot be destroyed since the Tratador " + tratadorListOrphanCheckTratador + " in its tratadorList field has a non-nullable equipe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipe> findEquipeEntities() {
        return findEquipeEntities(true, -1, -1);
    }

    public List<Equipe> findEquipeEntities(int maxResults, int firstResult) {
        return findEquipeEntities(false, maxResults, firstResult);
    }

    private List<Equipe> findEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipe.class));
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

    public Equipe findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipe> rt = cq.from(Equipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
