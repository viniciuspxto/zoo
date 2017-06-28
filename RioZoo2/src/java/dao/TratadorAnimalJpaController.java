/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Animal;
import model.Tratador;
import model.TratadorAnimal;

/**
 *
 * @author viniciuspeixoto
 */
public class TratadorAnimalJpaController implements Serializable {

    public TratadorAnimalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TratadorAnimal tratadorAnimal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal animal = tratadorAnimal.getAnimal();
            if (animal != null) {
                animal = em.getReference(animal.getClass(), animal.getId());
                tratadorAnimal.setAnimal(animal);
            }
            Tratador tratador = tratadorAnimal.getTratador();
            if (tratador != null) {
                tratador = em.getReference(tratador.getClass(), tratador.getId());
                tratadorAnimal.setTratador(tratador);
            }
            em.persist(tratadorAnimal);
            if (animal != null) {
                animal.getTratadorAnimalList().add(tratadorAnimal);
                animal = em.merge(animal);
            }
            if (tratador != null) {
                tratador.getTratadorAnimalList().add(tratadorAnimal);
                tratador = em.merge(tratador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TratadorAnimal tratadorAnimal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TratadorAnimal persistentTratadorAnimal = em.find(TratadorAnimal.class, tratadorAnimal.getId());
            Animal animalOld = persistentTratadorAnimal.getAnimal();
            Animal animalNew = tratadorAnimal.getAnimal();
            Tratador tratadorOld = persistentTratadorAnimal.getTratador();
            Tratador tratadorNew = tratadorAnimal.getTratador();
            if (animalNew != null) {
                animalNew = em.getReference(animalNew.getClass(), animalNew.getId());
                tratadorAnimal.setAnimal(animalNew);
            }
            if (tratadorNew != null) {
                tratadorNew = em.getReference(tratadorNew.getClass(), tratadorNew.getId());
                tratadorAnimal.setTratador(tratadorNew);
            }
            tratadorAnimal = em.merge(tratadorAnimal);
            if (animalOld != null && !animalOld.equals(animalNew)) {
                animalOld.getTratadorAnimalList().remove(tratadorAnimal);
                animalOld = em.merge(animalOld);
            }
            if (animalNew != null && !animalNew.equals(animalOld)) {
                animalNew.getTratadorAnimalList().add(tratadorAnimal);
                animalNew = em.merge(animalNew);
            }
            if (tratadorOld != null && !tratadorOld.equals(tratadorNew)) {
                tratadorOld.getTratadorAnimalList().remove(tratadorAnimal);
                tratadorOld = em.merge(tratadorOld);
            }
            if (tratadorNew != null && !tratadorNew.equals(tratadorOld)) {
                tratadorNew.getTratadorAnimalList().add(tratadorAnimal);
                tratadorNew = em.merge(tratadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tratadorAnimal.getId();
                if (findTratadorAnimal(id) == null) {
                    throw new NonexistentEntityException("The tratadorAnimal with id " + id + " no longer exists.");
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
            TratadorAnimal tratadorAnimal;
            try {
                tratadorAnimal = em.getReference(TratadorAnimal.class, id);
                tratadorAnimal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tratadorAnimal with id " + id + " no longer exists.", enfe);
            }
            Animal animal = tratadorAnimal.getAnimal();
            if (animal != null) {
                animal.getTratadorAnimalList().remove(tratadorAnimal);
                animal = em.merge(animal);
            }
            Tratador tratador = tratadorAnimal.getTratador();
            if (tratador != null) {
                tratador.getTratadorAnimalList().remove(tratadorAnimal);
                tratador = em.merge(tratador);
            }
            em.remove(tratadorAnimal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TratadorAnimal> findTratadorAnimalEntities() {
        return findTratadorAnimalEntities(true, -1, -1);
    }

    public List<TratadorAnimal> findTratadorAnimalEntities(int maxResults, int firstResult) {
        return findTratadorAnimalEntities(false, maxResults, firstResult);
    }

    private List<TratadorAnimal> findTratadorAnimalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TratadorAnimal.class));
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

    public TratadorAnimal findTratadorAnimal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TratadorAnimal.class, id);
        } finally {
            em.close();
        }
    }

    public int getTratadorAnimalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TratadorAnimal> rt = cq.from(TratadorAnimal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
