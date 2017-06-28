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
import model.Rotina;
import model.RegistroClinico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Animal;
import model.BoletimDiario;
import model.TratadorAnimal;
import model.Consulta;

/**
 *
 * @author viniciuspeixoto
 */
public class AnimalJpaController implements Serializable {

    public AnimalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Animal animal) {
        if (animal.getRegistroClinicoList() == null) {
            animal.setRegistroClinicoList(new ArrayList<RegistroClinico>());
        }
        if (animal.getBoletimDiarioList() == null) {
            animal.setBoletimDiarioList(new ArrayList<BoletimDiario>());
        }
        if (animal.getTratadorAnimalList() == null) {
            animal.setTratadorAnimalList(new ArrayList<TratadorAnimal>());
        }
        if (animal.getConsultaList() == null) {
            animal.setConsultaList(new ArrayList<Consulta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tratador tratadorResponsavel = animal.getTratadorResponsavel();
            if (tratadorResponsavel != null) {
                tratadorResponsavel = em.getReference(tratadorResponsavel.getClass(), tratadorResponsavel.getId());
                animal.setTratadorResponsavel(tratadorResponsavel);
            }
            Rotina rotina = animal.getRotina();
            if (rotina != null) {
                rotina = em.getReference(rotina.getClass(), rotina.getId());
                animal.setRotina(rotina);
            }
            List<RegistroClinico> attachedRegistroClinicoList = new ArrayList<RegistroClinico>();
            for (RegistroClinico registroClinicoListRegistroClinicoToAttach : animal.getRegistroClinicoList()) {
                registroClinicoListRegistroClinicoToAttach = em.getReference(registroClinicoListRegistroClinicoToAttach.getClass(), registroClinicoListRegistroClinicoToAttach.getId());
                attachedRegistroClinicoList.add(registroClinicoListRegistroClinicoToAttach);
            }
            animal.setRegistroClinicoList(attachedRegistroClinicoList);
            List<BoletimDiario> attachedBoletimDiarioList = new ArrayList<BoletimDiario>();
            for (BoletimDiario boletimDiarioListBoletimDiarioToAttach : animal.getBoletimDiarioList()) {
                boletimDiarioListBoletimDiarioToAttach = em.getReference(boletimDiarioListBoletimDiarioToAttach.getClass(), boletimDiarioListBoletimDiarioToAttach.getId());
                attachedBoletimDiarioList.add(boletimDiarioListBoletimDiarioToAttach);
            }
            animal.setBoletimDiarioList(attachedBoletimDiarioList);
            List<TratadorAnimal> attachedTratadorAnimalList = new ArrayList<TratadorAnimal>();
            for (TratadorAnimal tratadorAnimalListTratadorAnimalToAttach : animal.getTratadorAnimalList()) {
                tratadorAnimalListTratadorAnimalToAttach = em.getReference(tratadorAnimalListTratadorAnimalToAttach.getClass(), tratadorAnimalListTratadorAnimalToAttach.getId());
                attachedTratadorAnimalList.add(tratadorAnimalListTratadorAnimalToAttach);
            }
            animal.setTratadorAnimalList(attachedTratadorAnimalList);
            List<Consulta> attachedConsultaList = new ArrayList<Consulta>();
            for (Consulta consultaListConsultaToAttach : animal.getConsultaList()) {
                consultaListConsultaToAttach = em.getReference(consultaListConsultaToAttach.getClass(), consultaListConsultaToAttach.getId());
                attachedConsultaList.add(consultaListConsultaToAttach);
            }
            animal.setConsultaList(attachedConsultaList);
            em.persist(animal);
            if (tratadorResponsavel != null) {
                tratadorResponsavel.getAnimalList().add(animal);
                tratadorResponsavel = em.merge(tratadorResponsavel);
            }
            if (rotina != null) {
                rotina.getAnimalList().add(animal);
                rotina = em.merge(rotina);
            }
            for (RegistroClinico registroClinicoListRegistroClinico : animal.getRegistroClinicoList()) {
                Animal oldAnimalOfRegistroClinicoListRegistroClinico = registroClinicoListRegistroClinico.getAnimal();
                registroClinicoListRegistroClinico.setAnimal(animal);
                registroClinicoListRegistroClinico = em.merge(registroClinicoListRegistroClinico);
                if (oldAnimalOfRegistroClinicoListRegistroClinico != null) {
                    oldAnimalOfRegistroClinicoListRegistroClinico.getRegistroClinicoList().remove(registroClinicoListRegistroClinico);
                    oldAnimalOfRegistroClinicoListRegistroClinico = em.merge(oldAnimalOfRegistroClinicoListRegistroClinico);
                }
            }
            for (BoletimDiario boletimDiarioListBoletimDiario : animal.getBoletimDiarioList()) {
                Animal oldAnimalOfBoletimDiarioListBoletimDiario = boletimDiarioListBoletimDiario.getAnimal();
                boletimDiarioListBoletimDiario.setAnimal(animal);
                boletimDiarioListBoletimDiario = em.merge(boletimDiarioListBoletimDiario);
                if (oldAnimalOfBoletimDiarioListBoletimDiario != null) {
                    oldAnimalOfBoletimDiarioListBoletimDiario.getBoletimDiarioList().remove(boletimDiarioListBoletimDiario);
                    oldAnimalOfBoletimDiarioListBoletimDiario = em.merge(oldAnimalOfBoletimDiarioListBoletimDiario);
                }
            }
            for (TratadorAnimal tratadorAnimalListTratadorAnimal : animal.getTratadorAnimalList()) {
                Animal oldAnimalOfTratadorAnimalListTratadorAnimal = tratadorAnimalListTratadorAnimal.getAnimal();
                tratadorAnimalListTratadorAnimal.setAnimal(animal);
                tratadorAnimalListTratadorAnimal = em.merge(tratadorAnimalListTratadorAnimal);
                if (oldAnimalOfTratadorAnimalListTratadorAnimal != null) {
                    oldAnimalOfTratadorAnimalListTratadorAnimal.getTratadorAnimalList().remove(tratadorAnimalListTratadorAnimal);
                    oldAnimalOfTratadorAnimalListTratadorAnimal = em.merge(oldAnimalOfTratadorAnimalListTratadorAnimal);
                }
            }
            for (Consulta consultaListConsulta : animal.getConsultaList()) {
                Animal oldAnimalOfConsultaListConsulta = consultaListConsulta.getAnimal();
                consultaListConsulta.setAnimal(animal);
                consultaListConsulta = em.merge(consultaListConsulta);
                if (oldAnimalOfConsultaListConsulta != null) {
                    oldAnimalOfConsultaListConsulta.getConsultaList().remove(consultaListConsulta);
                    oldAnimalOfConsultaListConsulta = em.merge(oldAnimalOfConsultaListConsulta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Animal animal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal persistentAnimal = em.find(Animal.class, animal.getId());
            Tratador tratadorResponsavelOld = persistentAnimal.getTratadorResponsavel();
            Tratador tratadorResponsavelNew = animal.getTratadorResponsavel();
            Rotina rotinaOld = persistentAnimal.getRotina();
            Rotina rotinaNew = animal.getRotina();
            List<RegistroClinico> registroClinicoListOld = persistentAnimal.getRegistroClinicoList();
            List<RegistroClinico> registroClinicoListNew = animal.getRegistroClinicoList();
            List<BoletimDiario> boletimDiarioListOld = persistentAnimal.getBoletimDiarioList();
            List<BoletimDiario> boletimDiarioListNew = animal.getBoletimDiarioList();
            List<TratadorAnimal> tratadorAnimalListOld = persistentAnimal.getTratadorAnimalList();
            List<TratadorAnimal> tratadorAnimalListNew = animal.getTratadorAnimalList();
            List<Consulta> consultaListOld = persistentAnimal.getConsultaList();
            List<Consulta> consultaListNew = animal.getConsultaList();
            List<String> illegalOrphanMessages = null;
            for (RegistroClinico registroClinicoListOldRegistroClinico : registroClinicoListOld) {
                if (!registroClinicoListNew.contains(registroClinicoListOldRegistroClinico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroClinico " + registroClinicoListOldRegistroClinico + " since its animal field is not nullable.");
                }
            }
            for (BoletimDiario boletimDiarioListOldBoletimDiario : boletimDiarioListOld) {
                if (!boletimDiarioListNew.contains(boletimDiarioListOldBoletimDiario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BoletimDiario " + boletimDiarioListOldBoletimDiario + " since its animal field is not nullable.");
                }
            }
            for (TratadorAnimal tratadorAnimalListOldTratadorAnimal : tratadorAnimalListOld) {
                if (!tratadorAnimalListNew.contains(tratadorAnimalListOldTratadorAnimal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TratadorAnimal " + tratadorAnimalListOldTratadorAnimal + " since its animal field is not nullable.");
                }
            }
            for (Consulta consultaListOldConsulta : consultaListOld) {
                if (!consultaListNew.contains(consultaListOldConsulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consulta " + consultaListOldConsulta + " since its animal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tratadorResponsavelNew != null) {
                tratadorResponsavelNew = em.getReference(tratadorResponsavelNew.getClass(), tratadorResponsavelNew.getId());
                animal.setTratadorResponsavel(tratadorResponsavelNew);
            }
            if (rotinaNew != null) {
                rotinaNew = em.getReference(rotinaNew.getClass(), rotinaNew.getId());
                animal.setRotina(rotinaNew);
            }
            List<RegistroClinico> attachedRegistroClinicoListNew = new ArrayList<RegistroClinico>();
            for (RegistroClinico registroClinicoListNewRegistroClinicoToAttach : registroClinicoListNew) {
                registroClinicoListNewRegistroClinicoToAttach = em.getReference(registroClinicoListNewRegistroClinicoToAttach.getClass(), registroClinicoListNewRegistroClinicoToAttach.getId());
                attachedRegistroClinicoListNew.add(registroClinicoListNewRegistroClinicoToAttach);
            }
            registroClinicoListNew = attachedRegistroClinicoListNew;
            animal.setRegistroClinicoList(registroClinicoListNew);
            List<BoletimDiario> attachedBoletimDiarioListNew = new ArrayList<BoletimDiario>();
            for (BoletimDiario boletimDiarioListNewBoletimDiarioToAttach : boletimDiarioListNew) {
                boletimDiarioListNewBoletimDiarioToAttach = em.getReference(boletimDiarioListNewBoletimDiarioToAttach.getClass(), boletimDiarioListNewBoletimDiarioToAttach.getId());
                attachedBoletimDiarioListNew.add(boletimDiarioListNewBoletimDiarioToAttach);
            }
            boletimDiarioListNew = attachedBoletimDiarioListNew;
            animal.setBoletimDiarioList(boletimDiarioListNew);
            List<TratadorAnimal> attachedTratadorAnimalListNew = new ArrayList<TratadorAnimal>();
            for (TratadorAnimal tratadorAnimalListNewTratadorAnimalToAttach : tratadorAnimalListNew) {
                tratadorAnimalListNewTratadorAnimalToAttach = em.getReference(tratadorAnimalListNewTratadorAnimalToAttach.getClass(), tratadorAnimalListNewTratadorAnimalToAttach.getId());
                attachedTratadorAnimalListNew.add(tratadorAnimalListNewTratadorAnimalToAttach);
            }
            tratadorAnimalListNew = attachedTratadorAnimalListNew;
            animal.setTratadorAnimalList(tratadorAnimalListNew);
            List<Consulta> attachedConsultaListNew = new ArrayList<Consulta>();
            for (Consulta consultaListNewConsultaToAttach : consultaListNew) {
                consultaListNewConsultaToAttach = em.getReference(consultaListNewConsultaToAttach.getClass(), consultaListNewConsultaToAttach.getId());
                attachedConsultaListNew.add(consultaListNewConsultaToAttach);
            }
            consultaListNew = attachedConsultaListNew;
            animal.setConsultaList(consultaListNew);
            animal = em.merge(animal);
            if (tratadorResponsavelOld != null && !tratadorResponsavelOld.equals(tratadorResponsavelNew)) {
                tratadorResponsavelOld.getAnimalList().remove(animal);
                tratadorResponsavelOld = em.merge(tratadorResponsavelOld);
            }
            if (tratadorResponsavelNew != null && !tratadorResponsavelNew.equals(tratadorResponsavelOld)) {
                tratadorResponsavelNew.getAnimalList().add(animal);
                tratadorResponsavelNew = em.merge(tratadorResponsavelNew);
            }
            if (rotinaOld != null && !rotinaOld.equals(rotinaNew)) {
                rotinaOld.getAnimalList().remove(animal);
                rotinaOld = em.merge(rotinaOld);
            }
            if (rotinaNew != null && !rotinaNew.equals(rotinaOld)) {
                rotinaNew.getAnimalList().add(animal);
                rotinaNew = em.merge(rotinaNew);
            }
            for (RegistroClinico registroClinicoListNewRegistroClinico : registroClinicoListNew) {
                if (!registroClinicoListOld.contains(registroClinicoListNewRegistroClinico)) {
                    Animal oldAnimalOfRegistroClinicoListNewRegistroClinico = registroClinicoListNewRegistroClinico.getAnimal();
                    registroClinicoListNewRegistroClinico.setAnimal(animal);
                    registroClinicoListNewRegistroClinico = em.merge(registroClinicoListNewRegistroClinico);
                    if (oldAnimalOfRegistroClinicoListNewRegistroClinico != null && !oldAnimalOfRegistroClinicoListNewRegistroClinico.equals(animal)) {
                        oldAnimalOfRegistroClinicoListNewRegistroClinico.getRegistroClinicoList().remove(registroClinicoListNewRegistroClinico);
                        oldAnimalOfRegistroClinicoListNewRegistroClinico = em.merge(oldAnimalOfRegistroClinicoListNewRegistroClinico);
                    }
                }
            }
            for (BoletimDiario boletimDiarioListNewBoletimDiario : boletimDiarioListNew) {
                if (!boletimDiarioListOld.contains(boletimDiarioListNewBoletimDiario)) {
                    Animal oldAnimalOfBoletimDiarioListNewBoletimDiario = boletimDiarioListNewBoletimDiario.getAnimal();
                    boletimDiarioListNewBoletimDiario.setAnimal(animal);
                    boletimDiarioListNewBoletimDiario = em.merge(boletimDiarioListNewBoletimDiario);
                    if (oldAnimalOfBoletimDiarioListNewBoletimDiario != null && !oldAnimalOfBoletimDiarioListNewBoletimDiario.equals(animal)) {
                        oldAnimalOfBoletimDiarioListNewBoletimDiario.getBoletimDiarioList().remove(boletimDiarioListNewBoletimDiario);
                        oldAnimalOfBoletimDiarioListNewBoletimDiario = em.merge(oldAnimalOfBoletimDiarioListNewBoletimDiario);
                    }
                }
            }
            for (TratadorAnimal tratadorAnimalListNewTratadorAnimal : tratadorAnimalListNew) {
                if (!tratadorAnimalListOld.contains(tratadorAnimalListNewTratadorAnimal)) {
                    Animal oldAnimalOfTratadorAnimalListNewTratadorAnimal = tratadorAnimalListNewTratadorAnimal.getAnimal();
                    tratadorAnimalListNewTratadorAnimal.setAnimal(animal);
                    tratadorAnimalListNewTratadorAnimal = em.merge(tratadorAnimalListNewTratadorAnimal);
                    if (oldAnimalOfTratadorAnimalListNewTratadorAnimal != null && !oldAnimalOfTratadorAnimalListNewTratadorAnimal.equals(animal)) {
                        oldAnimalOfTratadorAnimalListNewTratadorAnimal.getTratadorAnimalList().remove(tratadorAnimalListNewTratadorAnimal);
                        oldAnimalOfTratadorAnimalListNewTratadorAnimal = em.merge(oldAnimalOfTratadorAnimalListNewTratadorAnimal);
                    }
                }
            }
            for (Consulta consultaListNewConsulta : consultaListNew) {
                if (!consultaListOld.contains(consultaListNewConsulta)) {
                    Animal oldAnimalOfConsultaListNewConsulta = consultaListNewConsulta.getAnimal();
                    consultaListNewConsulta.setAnimal(animal);
                    consultaListNewConsulta = em.merge(consultaListNewConsulta);
                    if (oldAnimalOfConsultaListNewConsulta != null && !oldAnimalOfConsultaListNewConsulta.equals(animal)) {
                        oldAnimalOfConsultaListNewConsulta.getConsultaList().remove(consultaListNewConsulta);
                        oldAnimalOfConsultaListNewConsulta = em.merge(oldAnimalOfConsultaListNewConsulta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = animal.getId();
                if (findAnimal(id) == null) {
                    throw new NonexistentEntityException("The animal with id " + id + " no longer exists.");
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
            Animal animal;
            try {
                animal = em.getReference(Animal.class, id);
                animal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The animal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RegistroClinico> registroClinicoListOrphanCheck = animal.getRegistroClinicoList();
            for (RegistroClinico registroClinicoListOrphanCheckRegistroClinico : registroClinicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Animal (" + animal + ") cannot be destroyed since the RegistroClinico " + registroClinicoListOrphanCheckRegistroClinico + " in its registroClinicoList field has a non-nullable animal field.");
            }
            List<BoletimDiario> boletimDiarioListOrphanCheck = animal.getBoletimDiarioList();
            for (BoletimDiario boletimDiarioListOrphanCheckBoletimDiario : boletimDiarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Animal (" + animal + ") cannot be destroyed since the BoletimDiario " + boletimDiarioListOrphanCheckBoletimDiario + " in its boletimDiarioList field has a non-nullable animal field.");
            }
            List<TratadorAnimal> tratadorAnimalListOrphanCheck = animal.getTratadorAnimalList();
            for (TratadorAnimal tratadorAnimalListOrphanCheckTratadorAnimal : tratadorAnimalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Animal (" + animal + ") cannot be destroyed since the TratadorAnimal " + tratadorAnimalListOrphanCheckTratadorAnimal + " in its tratadorAnimalList field has a non-nullable animal field.");
            }
            List<Consulta> consultaListOrphanCheck = animal.getConsultaList();
            for (Consulta consultaListOrphanCheckConsulta : consultaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Animal (" + animal + ") cannot be destroyed since the Consulta " + consultaListOrphanCheckConsulta + " in its consultaList field has a non-nullable animal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tratador tratadorResponsavel = animal.getTratadorResponsavel();
            if (tratadorResponsavel != null) {
                tratadorResponsavel.getAnimalList().remove(animal);
                tratadorResponsavel = em.merge(tratadorResponsavel);
            }
            Rotina rotina = animal.getRotina();
            if (rotina != null) {
                rotina.getAnimalList().remove(animal);
                rotina = em.merge(rotina);
            }
            em.remove(animal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Animal> findAnimalEntities() {
        return findAnimalEntities(true, -1, -1);
    }

    public List<Animal> findAnimalEntities(int maxResults, int firstResult) {
        return findAnimalEntities(false, maxResults, firstResult);
    }

    private List<Animal> findAnimalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Animal.class));
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

    public Animal findAnimal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Animal.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnimalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Animal> rt = cq.from(Animal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
