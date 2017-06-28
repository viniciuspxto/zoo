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
import model.Animal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Rotina;

/**
 *
 * @author viniciuspeixoto
 */
public class RotinaJpaController implements Serializable {

    public RotinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rotina rotina) {
        if (rotina.getAnimalList() == null) {
            rotina.setAnimalList(new ArrayList<Animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Animal> attachedAnimalList = new ArrayList<Animal>();
            for (Animal animalListAnimalToAttach : rotina.getAnimalList()) {
                animalListAnimalToAttach = em.getReference(animalListAnimalToAttach.getClass(), animalListAnimalToAttach.getId());
                attachedAnimalList.add(animalListAnimalToAttach);
            }
            rotina.setAnimalList(attachedAnimalList);
            em.persist(rotina);
            for (Animal animalListAnimal : rotina.getAnimalList()) {
                Rotina oldRotinaOfAnimalListAnimal = animalListAnimal.getRotina();
                animalListAnimal.setRotina(rotina);
                animalListAnimal = em.merge(animalListAnimal);
                if (oldRotinaOfAnimalListAnimal != null) {
                    oldRotinaOfAnimalListAnimal.getAnimalList().remove(animalListAnimal);
                    oldRotinaOfAnimalListAnimal = em.merge(oldRotinaOfAnimalListAnimal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rotina rotina) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rotina persistentRotina = em.find(Rotina.class, rotina.getId());
            List<Animal> animalListOld = persistentRotina.getAnimalList();
            List<Animal> animalListNew = rotina.getAnimalList();
            List<String> illegalOrphanMessages = null;
            for (Animal animalListOldAnimal : animalListOld) {
                if (!animalListNew.contains(animalListOldAnimal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Animal " + animalListOldAnimal + " since its rotina field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Animal> attachedAnimalListNew = new ArrayList<Animal>();
            for (Animal animalListNewAnimalToAttach : animalListNew) {
                animalListNewAnimalToAttach = em.getReference(animalListNewAnimalToAttach.getClass(), animalListNewAnimalToAttach.getId());
                attachedAnimalListNew.add(animalListNewAnimalToAttach);
            }
            animalListNew = attachedAnimalListNew;
            rotina.setAnimalList(animalListNew);
            rotina = em.merge(rotina);
            for (Animal animalListNewAnimal : animalListNew) {
                if (!animalListOld.contains(animalListNewAnimal)) {
                    Rotina oldRotinaOfAnimalListNewAnimal = animalListNewAnimal.getRotina();
                    animalListNewAnimal.setRotina(rotina);
                    animalListNewAnimal = em.merge(animalListNewAnimal);
                    if (oldRotinaOfAnimalListNewAnimal != null && !oldRotinaOfAnimalListNewAnimal.equals(rotina)) {
                        oldRotinaOfAnimalListNewAnimal.getAnimalList().remove(animalListNewAnimal);
                        oldRotinaOfAnimalListNewAnimal = em.merge(oldRotinaOfAnimalListNewAnimal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rotina.getId();
                if (findRotina(id) == null) {
                    throw new NonexistentEntityException("The rotina with id " + id + " no longer exists.");
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
            Rotina rotina;
            try {
                rotina = em.getReference(Rotina.class, id);
                rotina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rotina with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Animal> animalListOrphanCheck = rotina.getAnimalList();
            for (Animal animalListOrphanCheckAnimal : animalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rotina (" + rotina + ") cannot be destroyed since the Animal " + animalListOrphanCheckAnimal + " in its animalList field has a non-nullable rotina field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rotina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rotina> findRotinaEntities() {
        return findRotinaEntities(true, -1, -1);
    }

    public List<Rotina> findRotinaEntities(int maxResults, int firstResult) {
        return findRotinaEntities(false, maxResults, firstResult);
    }

    private List<Rotina> findRotinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rotina.class));
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

    public Rotina findRotina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rotina.class, id);
        } finally {
            em.close();
        }
    }

    public int getRotinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rotina> rt = cq.from(Rotina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
