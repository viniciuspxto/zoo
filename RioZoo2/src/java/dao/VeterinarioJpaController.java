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
import model.Endereco;
import model.RegistroClinico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Consulta;
import model.Veterinario;

/**
 *
 * @author viniciuspeixoto
 */
public class VeterinarioJpaController implements Serializable {

    public VeterinarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veterinario veterinario) throws PreexistingEntityException, Exception {
        if (veterinario.getRegistroClinicoList() == null) {
            veterinario.setRegistroClinicoList(new ArrayList<RegistroClinico>());
        }
        if (veterinario.getConsultaList() == null) {
            veterinario.setConsultaList(new ArrayList<Consulta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco endereco = veterinario.getEndereco();
            if (endereco != null) {
                endereco = em.getReference(endereco.getClass(), endereco.getId());
                veterinario.setEndereco(endereco);
            }
            List<RegistroClinico> attachedRegistroClinicoList = new ArrayList<RegistroClinico>();
            for (RegistroClinico registroClinicoListRegistroClinicoToAttach : veterinario.getRegistroClinicoList()) {
                registroClinicoListRegistroClinicoToAttach = em.getReference(registroClinicoListRegistroClinicoToAttach.getClass(), registroClinicoListRegistroClinicoToAttach.getId());
                attachedRegistroClinicoList.add(registroClinicoListRegistroClinicoToAttach);
            }
            veterinario.setRegistroClinicoList(attachedRegistroClinicoList);
            List<Consulta> attachedConsultaList = new ArrayList<Consulta>();
            for (Consulta consultaListConsultaToAttach : veterinario.getConsultaList()) {
                consultaListConsultaToAttach = em.getReference(consultaListConsultaToAttach.getClass(), consultaListConsultaToAttach.getId());
                attachedConsultaList.add(consultaListConsultaToAttach);
            }
            veterinario.setConsultaList(attachedConsultaList);
            em.persist(veterinario);
            if (endereco != null) {
                endereco.getVeterinarioList().add(veterinario);
                endereco = em.merge(endereco);
            }
            for (RegistroClinico registroClinicoListRegistroClinico : veterinario.getRegistroClinicoList()) {
                Veterinario oldVeterinarioOfRegistroClinicoListRegistroClinico = registroClinicoListRegistroClinico.getVeterinario();
                registroClinicoListRegistroClinico.setVeterinario(veterinario);
                registroClinicoListRegistroClinico = em.merge(registroClinicoListRegistroClinico);
                if (oldVeterinarioOfRegistroClinicoListRegistroClinico != null) {
                    oldVeterinarioOfRegistroClinicoListRegistroClinico.getRegistroClinicoList().remove(registroClinicoListRegistroClinico);
                    oldVeterinarioOfRegistroClinicoListRegistroClinico = em.merge(oldVeterinarioOfRegistroClinicoListRegistroClinico);
                }
            }
            for (Consulta consultaListConsulta : veterinario.getConsultaList()) {
                Veterinario oldVeterinarioOfConsultaListConsulta = consultaListConsulta.getVeterinario();
                consultaListConsulta.setVeterinario(veterinario);
                consultaListConsulta = em.merge(consultaListConsulta);
                if (oldVeterinarioOfConsultaListConsulta != null) {
                    oldVeterinarioOfConsultaListConsulta.getConsultaList().remove(consultaListConsulta);
                    oldVeterinarioOfConsultaListConsulta = em.merge(oldVeterinarioOfConsultaListConsulta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVeterinario(veterinario.getId()) != null) {
                throw new PreexistingEntityException("Veterinario " + veterinario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veterinario veterinario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veterinario persistentVeterinario = em.find(Veterinario.class, veterinario.getId());
            Endereco enderecoOld = persistentVeterinario.getEndereco();
            Endereco enderecoNew = veterinario.getEndereco();
            List<RegistroClinico> registroClinicoListOld = persistentVeterinario.getRegistroClinicoList();
            List<RegistroClinico> registroClinicoListNew = veterinario.getRegistroClinicoList();
            List<Consulta> consultaListOld = persistentVeterinario.getConsultaList();
            List<Consulta> consultaListNew = veterinario.getConsultaList();
            List<String> illegalOrphanMessages = null;
            for (RegistroClinico registroClinicoListOldRegistroClinico : registroClinicoListOld) {
                if (!registroClinicoListNew.contains(registroClinicoListOldRegistroClinico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroClinico " + registroClinicoListOldRegistroClinico + " since its veterinario field is not nullable.");
                }
            }
            for (Consulta consultaListOldConsulta : consultaListOld) {
                if (!consultaListNew.contains(consultaListOldConsulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consulta " + consultaListOldConsulta + " since its veterinario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (enderecoNew != null) {
                enderecoNew = em.getReference(enderecoNew.getClass(), enderecoNew.getId());
                veterinario.setEndereco(enderecoNew);
            }
            List<RegistroClinico> attachedRegistroClinicoListNew = new ArrayList<RegistroClinico>();
            for (RegistroClinico registroClinicoListNewRegistroClinicoToAttach : registroClinicoListNew) {
                registroClinicoListNewRegistroClinicoToAttach = em.getReference(registroClinicoListNewRegistroClinicoToAttach.getClass(), registroClinicoListNewRegistroClinicoToAttach.getId());
                attachedRegistroClinicoListNew.add(registroClinicoListNewRegistroClinicoToAttach);
            }
            registroClinicoListNew = attachedRegistroClinicoListNew;
            veterinario.setRegistroClinicoList(registroClinicoListNew);
            List<Consulta> attachedConsultaListNew = new ArrayList<Consulta>();
            for (Consulta consultaListNewConsultaToAttach : consultaListNew) {
                consultaListNewConsultaToAttach = em.getReference(consultaListNewConsultaToAttach.getClass(), consultaListNewConsultaToAttach.getId());
                attachedConsultaListNew.add(consultaListNewConsultaToAttach);
            }
            consultaListNew = attachedConsultaListNew;
            veterinario.setConsultaList(consultaListNew);
            veterinario = em.merge(veterinario);
            if (enderecoOld != null && !enderecoOld.equals(enderecoNew)) {
                enderecoOld.getVeterinarioList().remove(veterinario);
                enderecoOld = em.merge(enderecoOld);
            }
            if (enderecoNew != null && !enderecoNew.equals(enderecoOld)) {
                enderecoNew.getVeterinarioList().add(veterinario);
                enderecoNew = em.merge(enderecoNew);
            }
            for (RegistroClinico registroClinicoListNewRegistroClinico : registroClinicoListNew) {
                if (!registroClinicoListOld.contains(registroClinicoListNewRegistroClinico)) {
                    Veterinario oldVeterinarioOfRegistroClinicoListNewRegistroClinico = registroClinicoListNewRegistroClinico.getVeterinario();
                    registroClinicoListNewRegistroClinico.setVeterinario(veterinario);
                    registroClinicoListNewRegistroClinico = em.merge(registroClinicoListNewRegistroClinico);
                    if (oldVeterinarioOfRegistroClinicoListNewRegistroClinico != null && !oldVeterinarioOfRegistroClinicoListNewRegistroClinico.equals(veterinario)) {
                        oldVeterinarioOfRegistroClinicoListNewRegistroClinico.getRegistroClinicoList().remove(registroClinicoListNewRegistroClinico);
                        oldVeterinarioOfRegistroClinicoListNewRegistroClinico = em.merge(oldVeterinarioOfRegistroClinicoListNewRegistroClinico);
                    }
                }
            }
            for (Consulta consultaListNewConsulta : consultaListNew) {
                if (!consultaListOld.contains(consultaListNewConsulta)) {
                    Veterinario oldVeterinarioOfConsultaListNewConsulta = consultaListNewConsulta.getVeterinario();
                    consultaListNewConsulta.setVeterinario(veterinario);
                    consultaListNewConsulta = em.merge(consultaListNewConsulta);
                    if (oldVeterinarioOfConsultaListNewConsulta != null && !oldVeterinarioOfConsultaListNewConsulta.equals(veterinario)) {
                        oldVeterinarioOfConsultaListNewConsulta.getConsultaList().remove(consultaListNewConsulta);
                        oldVeterinarioOfConsultaListNewConsulta = em.merge(oldVeterinarioOfConsultaListNewConsulta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veterinario.getId();
                if (findVeterinario(id) == null) {
                    throw new NonexistentEntityException("The veterinario with id " + id + " no longer exists.");
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
            Veterinario veterinario;
            try {
                veterinario = em.getReference(Veterinario.class, id);
                veterinario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veterinario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RegistroClinico> registroClinicoListOrphanCheck = veterinario.getRegistroClinicoList();
            for (RegistroClinico registroClinicoListOrphanCheckRegistroClinico : registroClinicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the RegistroClinico " + registroClinicoListOrphanCheckRegistroClinico + " in its registroClinicoList field has a non-nullable veterinario field.");
            }
            List<Consulta> consultaListOrphanCheck = veterinario.getConsultaList();
            for (Consulta consultaListOrphanCheckConsulta : consultaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Consulta " + consultaListOrphanCheckConsulta + " in its consultaList field has a non-nullable veterinario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Endereco endereco = veterinario.getEndereco();
            if (endereco != null) {
                endereco.getVeterinarioList().remove(veterinario);
                endereco = em.merge(endereco);
            }
            em.remove(veterinario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veterinario> findVeterinarioEntities() {
        return findVeterinarioEntities(true, -1, -1);
    }

    public List<Veterinario> findVeterinarioEntities(int maxResults, int firstResult) {
        return findVeterinarioEntities(false, maxResults, firstResult);
    }

    private List<Veterinario> findVeterinarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veterinario.class));
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

    public Veterinario findVeterinario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veterinario.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeterinarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veterinario> rt = cq.from(Veterinario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
