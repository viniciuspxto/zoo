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
import model.Administrador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Endereco;
import model.Veterinario;
import model.Tratador;

/**
 *
 * @author viniciuspeixoto
 */
public class EnderecoJpaController implements Serializable {

    public EnderecoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereco endereco) {
        if (endereco.getAdministradorList() == null) {
            endereco.setAdministradorList(new ArrayList<Administrador>());
        }
        if (endereco.getVeterinarioList() == null) {
            endereco.setVeterinarioList(new ArrayList<Veterinario>());
        }
        if (endereco.getTratadorList() == null) {
            endereco.setTratadorList(new ArrayList<Tratador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Administrador> attachedAdministradorList = new ArrayList<Administrador>();
            for (Administrador administradorListAdministradorToAttach : endereco.getAdministradorList()) {
                administradorListAdministradorToAttach = em.getReference(administradorListAdministradorToAttach.getClass(), administradorListAdministradorToAttach.getId());
                attachedAdministradorList.add(administradorListAdministradorToAttach);
            }
            endereco.setAdministradorList(attachedAdministradorList);
            List<Veterinario> attachedVeterinarioList = new ArrayList<Veterinario>();
            for (Veterinario veterinarioListVeterinarioToAttach : endereco.getVeterinarioList()) {
                veterinarioListVeterinarioToAttach = em.getReference(veterinarioListVeterinarioToAttach.getClass(), veterinarioListVeterinarioToAttach.getId());
                attachedVeterinarioList.add(veterinarioListVeterinarioToAttach);
            }
            endereco.setVeterinarioList(attachedVeterinarioList);
            List<Tratador> attachedTratadorList = new ArrayList<Tratador>();
            for (Tratador tratadorListTratadorToAttach : endereco.getTratadorList()) {
                tratadorListTratadorToAttach = em.getReference(tratadorListTratadorToAttach.getClass(), tratadorListTratadorToAttach.getId());
                attachedTratadorList.add(tratadorListTratadorToAttach);
            }
            endereco.setTratadorList(attachedTratadorList);
            em.persist(endereco);
            for (Administrador administradorListAdministrador : endereco.getAdministradorList()) {
                Endereco oldEnderecoOfAdministradorListAdministrador = administradorListAdministrador.getEndereco();
                administradorListAdministrador.setEndereco(endereco);
                administradorListAdministrador = em.merge(administradorListAdministrador);
                if (oldEnderecoOfAdministradorListAdministrador != null) {
                    oldEnderecoOfAdministradorListAdministrador.getAdministradorList().remove(administradorListAdministrador);
                    oldEnderecoOfAdministradorListAdministrador = em.merge(oldEnderecoOfAdministradorListAdministrador);
                }
            }
            for (Veterinario veterinarioListVeterinario : endereco.getVeterinarioList()) {
                Endereco oldEnderecoOfVeterinarioListVeterinario = veterinarioListVeterinario.getEndereco();
                veterinarioListVeterinario.setEndereco(endereco);
                veterinarioListVeterinario = em.merge(veterinarioListVeterinario);
                if (oldEnderecoOfVeterinarioListVeterinario != null) {
                    oldEnderecoOfVeterinarioListVeterinario.getVeterinarioList().remove(veterinarioListVeterinario);
                    oldEnderecoOfVeterinarioListVeterinario = em.merge(oldEnderecoOfVeterinarioListVeterinario);
                }
            }
            for (Tratador tratadorListTratador : endereco.getTratadorList()) {
                Endereco oldEnderecoOfTratadorListTratador = tratadorListTratador.getEndereco();
                tratadorListTratador.setEndereco(endereco);
                tratadorListTratador = em.merge(tratadorListTratador);
                if (oldEnderecoOfTratadorListTratador != null) {
                    oldEnderecoOfTratadorListTratador.getTratadorList().remove(tratadorListTratador);
                    oldEnderecoOfTratadorListTratador = em.merge(oldEnderecoOfTratadorListTratador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereco endereco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco persistentEndereco = em.find(Endereco.class, endereco.getId());
            List<Administrador> administradorListOld = persistentEndereco.getAdministradorList();
            List<Administrador> administradorListNew = endereco.getAdministradorList();
            List<Veterinario> veterinarioListOld = persistentEndereco.getVeterinarioList();
            List<Veterinario> veterinarioListNew = endereco.getVeterinarioList();
            List<Tratador> tratadorListOld = persistentEndereco.getTratadorList();
            List<Tratador> tratadorListNew = endereco.getTratadorList();
            List<Administrador> attachedAdministradorListNew = new ArrayList<Administrador>();
            for (Administrador administradorListNewAdministradorToAttach : administradorListNew) {
                administradorListNewAdministradorToAttach = em.getReference(administradorListNewAdministradorToAttach.getClass(), administradorListNewAdministradorToAttach.getId());
                attachedAdministradorListNew.add(administradorListNewAdministradorToAttach);
            }
            administradorListNew = attachedAdministradorListNew;
            endereco.setAdministradorList(administradorListNew);
            List<Veterinario> attachedVeterinarioListNew = new ArrayList<Veterinario>();
            for (Veterinario veterinarioListNewVeterinarioToAttach : veterinarioListNew) {
                veterinarioListNewVeterinarioToAttach = em.getReference(veterinarioListNewVeterinarioToAttach.getClass(), veterinarioListNewVeterinarioToAttach.getId());
                attachedVeterinarioListNew.add(veterinarioListNewVeterinarioToAttach);
            }
            veterinarioListNew = attachedVeterinarioListNew;
            endereco.setVeterinarioList(veterinarioListNew);
            List<Tratador> attachedTratadorListNew = new ArrayList<Tratador>();
            for (Tratador tratadorListNewTratadorToAttach : tratadorListNew) {
                tratadorListNewTratadorToAttach = em.getReference(tratadorListNewTratadorToAttach.getClass(), tratadorListNewTratadorToAttach.getId());
                attachedTratadorListNew.add(tratadorListNewTratadorToAttach);
            }
            tratadorListNew = attachedTratadorListNew;
            endereco.setTratadorList(tratadorListNew);
            endereco = em.merge(endereco);
            for (Administrador administradorListOldAdministrador : administradorListOld) {
                if (!administradorListNew.contains(administradorListOldAdministrador)) {
                    administradorListOldAdministrador.setEndereco(null);
                    administradorListOldAdministrador = em.merge(administradorListOldAdministrador);
                }
            }
            for (Administrador administradorListNewAdministrador : administradorListNew) {
                if (!administradorListOld.contains(administradorListNewAdministrador)) {
                    Endereco oldEnderecoOfAdministradorListNewAdministrador = administradorListNewAdministrador.getEndereco();
                    administradorListNewAdministrador.setEndereco(endereco);
                    administradorListNewAdministrador = em.merge(administradorListNewAdministrador);
                    if (oldEnderecoOfAdministradorListNewAdministrador != null && !oldEnderecoOfAdministradorListNewAdministrador.equals(endereco)) {
                        oldEnderecoOfAdministradorListNewAdministrador.getAdministradorList().remove(administradorListNewAdministrador);
                        oldEnderecoOfAdministradorListNewAdministrador = em.merge(oldEnderecoOfAdministradorListNewAdministrador);
                    }
                }
            }
            for (Veterinario veterinarioListOldVeterinario : veterinarioListOld) {
                if (!veterinarioListNew.contains(veterinarioListOldVeterinario)) {
                    veterinarioListOldVeterinario.setEndereco(null);
                    veterinarioListOldVeterinario = em.merge(veterinarioListOldVeterinario);
                }
            }
            for (Veterinario veterinarioListNewVeterinario : veterinarioListNew) {
                if (!veterinarioListOld.contains(veterinarioListNewVeterinario)) {
                    Endereco oldEnderecoOfVeterinarioListNewVeterinario = veterinarioListNewVeterinario.getEndereco();
                    veterinarioListNewVeterinario.setEndereco(endereco);
                    veterinarioListNewVeterinario = em.merge(veterinarioListNewVeterinario);
                    if (oldEnderecoOfVeterinarioListNewVeterinario != null && !oldEnderecoOfVeterinarioListNewVeterinario.equals(endereco)) {
                        oldEnderecoOfVeterinarioListNewVeterinario.getVeterinarioList().remove(veterinarioListNewVeterinario);
                        oldEnderecoOfVeterinarioListNewVeterinario = em.merge(oldEnderecoOfVeterinarioListNewVeterinario);
                    }
                }
            }
            for (Tratador tratadorListOldTratador : tratadorListOld) {
                if (!tratadorListNew.contains(tratadorListOldTratador)) {
                    tratadorListOldTratador.setEndereco(null);
                    tratadorListOldTratador = em.merge(tratadorListOldTratador);
                }
            }
            for (Tratador tratadorListNewTratador : tratadorListNew) {
                if (!tratadorListOld.contains(tratadorListNewTratador)) {
                    Endereco oldEnderecoOfTratadorListNewTratador = tratadorListNewTratador.getEndereco();
                    tratadorListNewTratador.setEndereco(endereco);
                    tratadorListNewTratador = em.merge(tratadorListNewTratador);
                    if (oldEnderecoOfTratadorListNewTratador != null && !oldEnderecoOfTratadorListNewTratador.equals(endereco)) {
                        oldEnderecoOfTratadorListNewTratador.getTratadorList().remove(tratadorListNewTratador);
                        oldEnderecoOfTratadorListNewTratador = em.merge(oldEnderecoOfTratadorListNewTratador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereco.getId();
                if (findEndereco(id) == null) {
                    throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.");
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
            Endereco endereco;
            try {
                endereco = em.getReference(Endereco.class, id);
                endereco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endereco with id " + id + " no longer exists.", enfe);
            }
            List<Administrador> administradorList = endereco.getAdministradorList();
            for (Administrador administradorListAdministrador : administradorList) {
                administradorListAdministrador.setEndereco(null);
                administradorListAdministrador = em.merge(administradorListAdministrador);
            }
            List<Veterinario> veterinarioList = endereco.getVeterinarioList();
            for (Veterinario veterinarioListVeterinario : veterinarioList) {
                veterinarioListVeterinario.setEndereco(null);
                veterinarioListVeterinario = em.merge(veterinarioListVeterinario);
            }
            List<Tratador> tratadorList = endereco.getTratadorList();
            for (Tratador tratadorListTratador : tratadorList) {
                tratadorListTratador.setEndereco(null);
                tratadorListTratador = em.merge(tratadorListTratador);
            }
            em.remove(endereco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereco> findEnderecoEntities() {
        return findEnderecoEntities(true, -1, -1);
    }

    public List<Endereco> findEnderecoEntities(int maxResults, int firstResult) {
        return findEnderecoEntities(false, maxResults, firstResult);
    }

    private List<Endereco> findEnderecoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
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

    public Endereco findEndereco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereco.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereco> rt = cq.from(Endereco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
