package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartanga.crud.entities.Administrator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import javax.persistence.NoResultException;

/**
 *
 * @author 2dam
 */
@Stateless
public class EJBAdministratorManager implements AdministratorManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    @Override
    public void create(Administrator administrator) throws CreateException {
        try {
            em.persist(administrator);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void update(Administrator administrator) throws UpdateException {
        try {
            em.merge(administrator);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void remove(Administrator administrator) throws DeleteException {
        try {
            em.remove(administrator);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public Administrator find(String email) throws ReadException {
        Administrator administrator = null;
        try {
            administrator = em.find(Administrator.class, email);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return administrator;
    }

    @Override
    public List<Administrator> findAll() throws ReadException {
        List<Administrator> administrator;
        try {
            administrator = em.createNamedQuery("findAll").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return administrator;
    }

    @Override
    public Administrator signIn(String email, String passwd) throws ReadException {
        try {
            return em.createNamedQuery("adminSignIn", Administrator.class)
                    .setParameter("email", email)
                    .setParameter("passwd", passwd)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Devolver null si no se encuentra la entidad.
        } catch (Exception e) {
            throw new ReadException("An error occurred during the sign-in process: " + e.getMessage());
        }
    }

}
