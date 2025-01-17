/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.FanetixClient;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public class EJBFanetixClientManager implements FanetixClientManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    @Override
    public void createClient(FanetixClient client) throws CreateException {
        try {
            em.persist(client);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateClient(FanetixClient client) throws UpdateException {
        try {
            em.merge(client);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removeClient(FanetixClient client) throws DeleteException {
        try {
            em.remove(client);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public FanetixClient findClient(String email) throws ReadException {
        FanetixClient client = null;
        try {
            client = em.find(FanetixClient.class, email);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return client;
    }

    @Override
    public List<FanetixClient> findAllClients() throws ReadException {
        List<FanetixClient> clients;
        try {
            clients = em.createNamedQuery("findAllClients").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return clients;
    }

    @Override
    public FanetixClient signIn(String email, String passwd) throws ReadException {
        FanetixClient administrator = null;
        try {
            administrator = (FanetixClient) em.createNamedQuery("clientSignIn")
                    .setParameter("email", email)
                    .setParameter("passwd", passwd)
                    .getSingleResult();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return administrator;
    }
}
