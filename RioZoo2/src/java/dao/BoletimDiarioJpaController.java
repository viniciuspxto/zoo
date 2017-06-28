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
import model.Tratador;
import model.Animal;
import model.BoletimDiario;

/**
 *
 * @author viniciuspeixoto
 */
public class BoletimDiarioJpaController implements Serializable {

    public BoletimDiarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BoletimDiario boletimDiario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tratador tratadorRespons = boletimDiario.getTratadorRespons();
            if (tratadorRespons != null) {
                tratadorRespons = em.getReference(tratadorRespons.getClass(), tratadorRespons.getId());
                boletimDiario.setTratadorRespons(tratadorRespons);
            }
            Animal animal = boletimDiario.getAnimal();
            if (animal != null) {
                animal = em.getReference(animal.getClass(), animal.getId());
                boletimDiario.setAnimal(animal);
            }
            em.persist(boletimDiario);
            if (tratadorRespons != null) {
                tratadorRespons.getBoletimDiarioList().add(boletimDiario);
                tratadorRespons = em.merge(tratadorRespons);
            }
            if (animal != null) {
                animal.getBoletimDiarioList().add(boletimDiario);
                animal = em.merge(animal);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BoletimDiario boletimDiario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BoletimDiario persistentBoletimDiario = em.find(BoletimDiario.class, boletimDiario.getId());
            Tratador tratadorResponsOld = persistentBoletimDiario.getTratadorRespons();
            Tratador tratadorResponsNew = boletimDiario.getTratadorRespons();
            Animal animalOld = persistentBoletimDiario.getAnimal();
            Animal animalNew = boletimDiario.getAnimal();
            if (tratadorResponsNew != null) {
                tratadorResponsNew = em.getReference(tratadorResponsNew.getClass(), tratadorResponsNew.getId());
                boletimDiario.setTratadorRespons(tratadorResponsNew);
            }
            if (animalNew != null) {
                animalNew = em.getReference(animalNew.getClass(), animalNew.getId());
                boletimDiario.setAnimal(animalNew);
            }
            boletimDiario = em.merge(boletimDiario);
            if (tratadorResponsOld != null && !tratadorResponsOld.equals(tratadorResponsNew)) {
                tratadorResponsOld.getBoletimDiarioList().remove(boletimDiario);
                tratadorResponsOld = em.merge(tratadorResponsOld);
            }
            if (tratadorResponsNew != null && !tratadorResponsNew.equals(tratadorResponsOld)) {
                tratadorResponsNew.getBoletimDiarioList().add(boletimDiario);
                tratadorResponsNew = em.merge(tratadorResponsNew);
            }
            if (animalOld != null && !animalOld.equals(animalNew)) {
                animalOld.getBoletimDiarioList().remove(boletimDiario);
                animalOld = em.merge(animalOld);
            }
            if (animalNew != null && !animalNew.equals(animalOld)) {
                animalNew.getBoletimDiarioList().add(boletimDiario);
                animalNew = em.merge(animalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boletimDiario.getId();
                if (findBoletimDiario(id) == null) {
                    throw new NonexistentEntityException("The boletimDiario with id " + id + " no longer exists.");
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
            BoletimDiario boletimDiario;
            try {
                boletimDiario = em.getReference(BoletimDiario.class, id);
                boletimDiario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boletimDiario with id " + id + " no longer exists.", enfe);
            }
            Tratador tratadorRespons = boletimDiario.getTratadorRespons();
            if (tratadorRespons != null) {
                tratadorRespons.getBoletimDiarioList().remove(boletimDiario);
                tratadorRespons = em.merge(tratadorRespons);
            }
            Animal animal = boletimDiario.getAnimal();
            if (animal != null) {
                animal.getBoletimDiarioList().remove(boletimDiario);
                animal = em.merge(animal);
            }
            em.remove(boletimDiario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BoletimDiario> findBoletimDiarioEntities() {
        return findBoletimDiarioEntities(true, -1, -1);
    }

    public List<BoletimDiario> findBoletimDiarioEntities(int maxResults, int firstResult) {
        return findBoletimDiarioEntities(false, maxResults, firstResult);
    }

    private List<BoletimDiario> findBoletimDiarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BoletimDiario.class));
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

    public BoletimDiario findBoletimDiario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BoletimDiario.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletimDiarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BoletimDiario> rt = cq.from(BoletimDiario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
