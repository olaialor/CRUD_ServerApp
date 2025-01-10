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

/**
 *
 * @author 2dam
 */
@Stateless
public class EJBAdministratorManager implements AdministratorManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    @Override
    public void create(Administrator administrator) {
        try {
            em.persist(administrator);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void update(Administrator administrator) {
        try {
            em.merge(administrator);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void remove(Administrator administrator) {
        try {
            em.remove(administrator);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Administrator find(String email) {
        Administrator administrator = null;
        try {
            administrator= em.find(Administrator.class, email);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return administrator ;
    }

    @Override
    public List<Administrator> findAll() {
        List<Administrator> administrator;
        try {
            administrator= em.createNamedQuery("findAll").getResultList();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return administrator ;
    }  
}

