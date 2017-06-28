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
import model.RegistroClinico;
import model.Vacina;
import model.Veterinario;

/**
 *
 * @author viniciuspeixoto
 */
public class RegistroClinicoJpaController implements Serializable {

    public RegistroClinicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroClinico registroClinico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal animal = registroClinico.getAnimal();
            if (animal != null) {
                animal = em.getReference(animal.getClass(), animal.getId());
                registroClinico.setAnimal(animal);
            }
            Vacina vacina = registroClinico.getVacina();
            if (vacina != null) {
                vacina = em.getReference(vacina.getClass(), vacina.getId());
                registroClinico.setVacina(vacina);
            }
            Veterinario veterinario = registroClinico.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getId());
                registroClinico.setVeterinario(veterinario);
            }
            em.persist(registroClinico);
            if (animal != null) {
                animal.getRegistroClinicoList().add(registroClinico);
                animal = em.merge(animal);
            }
            if (vacina != null) {
                vacina.getRegistroClinicoList().add(registroClinico);
                vacina = em.merge(vacina);
            }
            if (veterinario != null) {
                veterinario.getRegistroClinicoList().add(registroClinico);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroClinico registroClinico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroClinico persistentRegistroClinico = em.find(RegistroClinico.class, registroClinico.getId());
            Animal animalOld = persistentRegistroClinico.getAnimal();
            Animal animalNew = registroClinico.getAnimal();
            Vacina vacinaOld = persistentRegistroClinico.getVacina();
            Vacina vacinaNew = registroClinico.getVacina();
            Veterinario veterinarioOld = persistentRegistroClinico.getVeterinario();
            Veterinario veterinarioNew = registroClinico.getVeterinario();
            if (animalNew != null) {
                animalNew = em.getReference(animalNew.getClass(), animalNew.getId());
                registroClinico.setAnimal(animalNew);
            }
            if (vacinaNew != null) {
                vacinaNew = em.getReference(vacinaNew.getClass(), vacinaNew.getId());
                registroClinico.setVacina(vacinaNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getId());
                registroClinico.setVeterinario(veterinarioNew);
            }
            registroClinico = em.merge(registroClinico);
            if (animalOld != null && !animalOld.equals(animalNew)) {
                animalOld.getRegistroClinicoList().remove(registroClinico);
                animalOld = em.merge(animalOld);
            }
            if (animalNew != null && !animalNew.equals(animalOld)) {
                animalNew.getRegistroClinicoList().add(registroClinico);
                animalNew = em.merge(animalNew);
            }
            if (vacinaOld != null && !vacinaOld.equals(vacinaNew)) {
                vacinaOld.getRegistroClinicoList().remove(registroClinico);
                vacinaOld = em.merge(vacinaOld);
            }
            if (vacinaNew != null && !vacinaNew.equals(vacinaOld)) {
                vacinaNew.getRegistroClinicoList().add(registroClinico);
                vacinaNew = em.merge(vacinaNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getRegistroClinicoList().remove(registroClinico);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getRegistroClinicoList().add(registroClinico);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registroClinico.getId();
                if (findRegistroClinico(id) == null) {
                    throw new NonexistentEntityException("The registroClinico with id " + id + " no longer exists.");
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
            RegistroClinico registroClinico;
            try {
                registroClinico = em.getReference(RegistroClinico.class, id);
                registroClinico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroClinico with id " + id + " no longer exists.", enfe);
            }
            Animal animal = registroClinico.getAnimal();
            if (animal != null) {
                animal.getRegistroClinicoList().remove(registroClinico);
                animal = em.merge(animal);
            }
            Vacina vacina = registroClinico.getVacina();
            if (vacina != null) {
                vacina.getRegistroClinicoList().remove(registroClinico);
                vacina = em.merge(vacina);
            }
            Veterinario veterinario = registroClinico.getVeterinario();
            if (veterinario != null) {
                veterinario.getRegistroClinicoList().remove(registroClinico);
                veterinario = em.merge(veterinario);
            }
            em.remove(registroClinico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroClinico> findRegistroClinicoEntities() {
        return findRegistroClinicoEntities(true, -1, -1);
    }

    public List<RegistroClinico> findRegistroClinicoEntities(int maxResults, int firstResult) {
        return findRegistroClinicoEntities(false, maxResults, firstResult);
    }

    private List<RegistroClinico> findRegistroClinicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroClinico.class));
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

    public RegistroClinico findRegistroClinico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroClinico.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroClinicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroClinico> rt = cq.from(RegistroClinico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
