/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Equipe;
import model.Endereco;
import model.BoletimDiario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TratadorAnimal;
import model.Animal;
import model.Tratador;

/**
 *
 * @author viniciuspeixoto
 */
public class TratadorJpaController implements Serializable {

    public TratadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tratador tratador) throws PreexistingEntityException, Exception {
        if (tratador.getBoletimDiarioList() == null) {
            tratador.setBoletimDiarioList(new ArrayList<BoletimDiario>());
        }
        if (tratador.getTratadorAnimalList() == null) {
            tratador.setTratadorAnimalList(new ArrayList<TratadorAnimal>());
        }
        if (tratador.getAnimalList() == null) {
            tratador.setAnimalList(new ArrayList<Animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe equipe = tratador.getEquipe();
            if (equipe != null) {
                equipe = em.getReference(equipe.getClass(), equipe.getId());
                tratador.setEquipe(equipe);
            }
            Endereco endereco = tratador.getEndereco();
            if (endereco != null) {
                endereco = em.getReference(endereco.getClass(), endereco.getId());
                tratador.setEndereco(endereco);
            }
            List<BoletimDiario> attachedBoletimDiarioList = new ArrayList<BoletimDiario>();
            for (BoletimDiario boletimDiarioListBoletimDiarioToAttach : tratador.getBoletimDiarioList()) {
                boletimDiarioListBoletimDiarioToAttach = em.getReference(boletimDiarioListBoletimDiarioToAttach.getClass(), boletimDiarioListBoletimDiarioToAttach.getId());
                attachedBoletimDiarioList.add(boletimDiarioListBoletimDiarioToAttach);
            }
            tratador.setBoletimDiarioList(attachedBoletimDiarioList);
            List<TratadorAnimal> attachedTratadorAnimalList = new ArrayList<TratadorAnimal>();
            for (TratadorAnimal tratadorAnimalListTratadorAnimalToAttach : tratador.getTratadorAnimalList()) {
                tratadorAnimalListTratadorAnimalToAttach = em.getReference(tratadorAnimalListTratadorAnimalToAttach.getClass(), tratadorAnimalListTratadorAnimalToAttach.getId());
                attachedTratadorAnimalList.add(tratadorAnimalListTratadorAnimalToAttach);
            }
            tratador.setTratadorAnimalList(attachedTratadorAnimalList);
            List<Animal> attachedAnimalList = new ArrayList<Animal>();
            for (Animal animalListAnimalToAttach : tratador.getAnimalList()) {
                animalListAnimalToAttach = em.getReference(animalListAnimalToAttach.getClass(), animalListAnimalToAttach.getId());
                attachedAnimalList.add(animalListAnimalToAttach);
            }
            tratador.setAnimalList(attachedAnimalList);
            em.persist(tratador);
            if (equipe != null) {
                equipe.getTratadorList().add(tratador);
                equipe = em.merge(equipe);
            }
            if (endereco != null) {
                endereco.getTratadorList().add(tratador);
                endereco = em.merge(endereco);
            }
            for (BoletimDiario boletimDiarioListBoletimDiario : tratador.getBoletimDiarioList()) {
                Tratador oldTratadorResponsOfBoletimDiarioListBoletimDiario = boletimDiarioListBoletimDiario.getTratadorRespons();
                boletimDiarioListBoletimDiario.setTratadorRespons(tratador);
                boletimDiarioListBoletimDiario = em.merge(boletimDiarioListBoletimDiario);
                if (oldTratadorResponsOfBoletimDiarioListBoletimDiario != null) {
                    oldTratadorResponsOfBoletimDiarioListBoletimDiario.getBoletimDiarioList().remove(boletimDiarioListBoletimDiario);
                    oldTratadorResponsOfBoletimDiarioListBoletimDiario = em.merge(oldTratadorResponsOfBoletimDiarioListBoletimDiario);
                }
            }
            for (TratadorAnimal tratadorAnimalListTratadorAnimal : tratador.getTratadorAnimalList()) {
                Tratador oldTratadorOfTratadorAnimalListTratadorAnimal = tratadorAnimalListTratadorAnimal.getTratador();
                tratadorAnimalListTratadorAnimal.setTratador(tratador);
                tratadorAnimalListTratadorAnimal = em.merge(tratadorAnimalListTratadorAnimal);
                if (oldTratadorOfTratadorAnimalListTratadorAnimal != null) {
                    oldTratadorOfTratadorAnimalListTratadorAnimal.getTratadorAnimalList().remove(tratadorAnimalListTratadorAnimal);
                    oldTratadorOfTratadorAnimalListTratadorAnimal = em.merge(oldTratadorOfTratadorAnimalListTratadorAnimal);
                }
            }
            for (Animal animalListAnimal : tratador.getAnimalList()) {
                Tratador oldTratadorResponsavelOfAnimalListAnimal = animalListAnimal.getTratadorResponsavel();
                animalListAnimal.setTratadorResponsavel(tratador);
                animalListAnimal = em.merge(animalListAnimal);
                if (oldTratadorResponsavelOfAnimalListAnimal != null) {
                    oldTratadorResponsavelOfAnimalListAnimal.getAnimalList().remove(animalListAnimal);
                    oldTratadorResponsavelOfAnimalListAnimal = em.merge(oldTratadorResponsavelOfAnimalListAnimal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTratador(tratador.getId()) != null) {
                throw new PreexistingEntityException("Tratador " + tratador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tratador tratador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tratador persistentTratador = em.find(Tratador.class, tratador.getId());
            Equipe equipeOld = persistentTratador.getEquipe();
            Equipe equipeNew = tratador.getEquipe();
            Endereco enderecoOld = persistentTratador.getEndereco();
            Endereco enderecoNew = tratador.getEndereco();
            List<BoletimDiario> boletimDiarioListOld = persistentTratador.getBoletimDiarioList();
            List<BoletimDiario> boletimDiarioListNew = tratador.getBoletimDiarioList();
            List<TratadorAnimal> tratadorAnimalListOld = persistentTratador.getTratadorAnimalList();
            List<TratadorAnimal> tratadorAnimalListNew = tratador.getTratadorAnimalList();
            List<Animal> animalListOld = persistentTratador.getAnimalList();
            List<Animal> animalListNew = tratador.getAnimalList();
            List<String> illegalOrphanMessages = null;
            for (BoletimDiario boletimDiarioListOldBoletimDiario : boletimDiarioListOld) {
                if (!boletimDiarioListNew.contains(boletimDiarioListOldBoletimDiario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BoletimDiario " + boletimDiarioListOldBoletimDiario + " since its tratadorRespons field is not nullable.");
                }
            }
            for (Animal animalListOldAnimal : animalListOld) {
                if (!animalListNew.contains(animalListOldAnimal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Animal " + animalListOldAnimal + " since its tratadorResponsavel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (equipeNew != null) {
                equipeNew = em.getReference(equipeNew.getClass(), equipeNew.getId());
                tratador.setEquipe(equipeNew);
            }
            if (enderecoNew != null) {
                enderecoNew = em.getReference(enderecoNew.getClass(), enderecoNew.getId());
                tratador.setEndereco(enderecoNew);
            }
            List<BoletimDiario> attachedBoletimDiarioListNew = new ArrayList<BoletimDiario>();
            for (BoletimDiario boletimDiarioListNewBoletimDiarioToAttach : boletimDiarioListNew) {
                boletimDiarioListNewBoletimDiarioToAttach = em.getReference(boletimDiarioListNewBoletimDiarioToAttach.getClass(), boletimDiarioListNewBoletimDiarioToAttach.getId());
                attachedBoletimDiarioListNew.add(boletimDiarioListNewBoletimDiarioToAttach);
            }
            boletimDiarioListNew = attachedBoletimDiarioListNew;
            tratador.setBoletimDiarioList(boletimDiarioListNew);
            List<TratadorAnimal> attachedTratadorAnimalListNew = new ArrayList<TratadorAnimal>();
            for (TratadorAnimal tratadorAnimalListNewTratadorAnimalToAttach : tratadorAnimalListNew) {
                tratadorAnimalListNewTratadorAnimalToAttach = em.getReference(tratadorAnimalListNewTratadorAnimalToAttach.getClass(), tratadorAnimalListNewTratadorAnimalToAttach.getId());
                attachedTratadorAnimalListNew.add(tratadorAnimalListNewTratadorAnimalToAttach);
            }
            tratadorAnimalListNew = attachedTratadorAnimalListNew;
            tratador.setTratadorAnimalList(tratadorAnimalListNew);
            List<Animal> attachedAnimalListNew = new ArrayList<Animal>();
            for (Animal animalListNewAnimalToAttach : animalListNew) {
                animalListNewAnimalToAttach = em.getReference(animalListNewAnimalToAttach.getClass(), animalListNewAnimalToAttach.getId());
                attachedAnimalListNew.add(animalListNewAnimalToAttach);
            }
            animalListNew = attachedAnimalListNew;
            tratador.setAnimalList(animalListNew);
            tratador = em.merge(tratador);
            if (equipeOld != null && !equipeOld.equals(equipeNew)) {
                equipeOld.getTratadorList().remove(tratador);
                equipeOld = em.merge(equipeOld);
            }
            if (equipeNew != null && !equipeNew.equals(equipeOld)) {
                equipeNew.getTratadorList().add(tratador);
                equipeNew = em.merge(equipeNew);
            }
            if (enderecoOld != null && !enderecoOld.equals(enderecoNew)) {
                enderecoOld.getTratadorList().remove(tratador);
                enderecoOld = em.merge(enderecoOld);
            }
            if (enderecoNew != null && !enderecoNew.equals(enderecoOld)) {
                enderecoNew.getTratadorList().add(tratador);
                enderecoNew = em.merge(enderecoNew);
            }
            for (BoletimDiario boletimDiarioListNewBoletimDiario : boletimDiarioListNew) {
                if (!boletimDiarioListOld.contains(boletimDiarioListNewBoletimDiario)) {
                    Tratador oldTratadorResponsOfBoletimDiarioListNewBoletimDiario = boletimDiarioListNewBoletimDiario.getTratadorRespons();
                    boletimDiarioListNewBoletimDiario.setTratadorRespons(tratador);
                    boletimDiarioListNewBoletimDiario = em.merge(boletimDiarioListNewBoletimDiario);
                    if (oldTratadorResponsOfBoletimDiarioListNewBoletimDiario != null && !oldTratadorResponsOfBoletimDiarioListNewBoletimDiario.equals(tratador)) {
                        oldTratadorResponsOfBoletimDiarioListNewBoletimDiario.getBoletimDiarioList().remove(boletimDiarioListNewBoletimDiario);
                        oldTratadorResponsOfBoletimDiarioListNewBoletimDiario = em.merge(oldTratadorResponsOfBoletimDiarioListNewBoletimDiario);
                    }
                }
            }
            for (TratadorAnimal tratadorAnimalListOldTratadorAnimal : tratadorAnimalListOld) {
                if (!tratadorAnimalListNew.contains(tratadorAnimalListOldTratadorAnimal)) {
                    tratadorAnimalListOldTratadorAnimal.setTratador(null);
                    tratadorAnimalListOldTratadorAnimal = em.merge(tratadorAnimalListOldTratadorAnimal);
                }
            }
            for (TratadorAnimal tratadorAnimalListNewTratadorAnimal : tratadorAnimalListNew) {
                if (!tratadorAnimalListOld.contains(tratadorAnimalListNewTratadorAnimal)) {
                    Tratador oldTratadorOfTratadorAnimalListNewTratadorAnimal = tratadorAnimalListNewTratadorAnimal.getTratador();
                    tratadorAnimalListNewTratadorAnimal.setTratador(tratador);
                    tratadorAnimalListNewTratadorAnimal = em.merge(tratadorAnimalListNewTratadorAnimal);
                    if (oldTratadorOfTratadorAnimalListNewTratadorAnimal != null && !oldTratadorOfTratadorAnimalListNewTratadorAnimal.equals(tratador)) {
                        oldTratadorOfTratadorAnimalListNewTratadorAnimal.getTratadorAnimalList().remove(tratadorAnimalListNewTratadorAnimal);
                        oldTratadorOfTratadorAnimalListNewTratadorAnimal = em.merge(oldTratadorOfTratadorAnimalListNewTratadorAnimal);
                    }
                }
            }
            for (Animal animalListNewAnimal : animalListNew) {
                if (!animalListOld.contains(animalListNewAnimal)) {
                    Tratador oldTratadorResponsavelOfAnimalListNewAnimal = animalListNewAnimal.getTratadorResponsavel();
                    animalListNewAnimal.setTratadorResponsavel(tratador);
                    animalListNewAnimal = em.merge(animalListNewAnimal);
                    if (oldTratadorResponsavelOfAnimalListNewAnimal != null && !oldTratadorResponsavelOfAnimalListNewAnimal.equals(tratador)) {
                        oldTratadorResponsavelOfAnimalListNewAnimal.getAnimalList().remove(animalListNewAnimal);
                        oldTratadorResponsavelOfAnimalListNewAnimal = em.merge(oldTratadorResponsavelOfAnimalListNewAnimal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tratador.getId();
                if (findTratador(id) == null) {
                    throw new NonexistentEntityException("The tratador with id " + id + " no longer exists.");
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
            Tratador tratador;
            try {
                tratador = em.getReference(Tratador.class, id);
                tratador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tratador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BoletimDiario> boletimDiarioListOrphanCheck = tratador.getBoletimDiarioList();
            for (BoletimDiario boletimDiarioListOrphanCheckBoletimDiario : boletimDiarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tratador (" + tratador + ") cannot be destroyed since the BoletimDiario " + boletimDiarioListOrphanCheckBoletimDiario + " in its boletimDiarioList field has a non-nullable tratadorRespons field.");
            }
            List<Animal> animalListOrphanCheck = tratador.getAnimalList();
            for (Animal animalListOrphanCheckAnimal : animalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tratador (" + tratador + ") cannot be destroyed since the Animal " + animalListOrphanCheckAnimal + " in its animalList field has a non-nullable tratadorResponsavel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Equipe equipe = tratador.getEquipe();
            if (equipe != null) {
                equipe.getTratadorList().remove(tratador);
                equipe = em.merge(equipe);
            }
            Endereco endereco = tratador.getEndereco();
            if (endereco != null) {
                endereco.getTratadorList().remove(tratador);
                endereco = em.merge(endereco);
            }
            List<TratadorAnimal> tratadorAnimalList = tratador.getTratadorAnimalList();
            for (TratadorAnimal tratadorAnimalListTratadorAnimal : tratadorAnimalList) {
                tratadorAnimalListTratadorAnimal.setTratador(null);
                tratadorAnimalListTratadorAnimal = em.merge(tratadorAnimalListTratadorAnimal);
            }
            em.remove(tratador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tratador> findTratadorEntities() {
        return findTratadorEntities(true, -1, -1);
    }

    public List<Tratador> findTratadorEntities(int maxResults, int firstResult) {
        return findTratadorEntities(false, maxResults, firstResult);
    }

    private List<Tratador> findTratadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tratador.class));
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

    public Tratador findTratador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tratador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTratadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tratador> rt = cq.from(Tratador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
