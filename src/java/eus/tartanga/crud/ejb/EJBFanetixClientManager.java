/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.FanetixClient;
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
    public void createClient(FanetixClient client) {
        try {
            em.persist(client);
        } catch (Exception e) {
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void updateClient(FanetixClient client) {
        try {
            em.merge(client);
        } catch (Exception e) {
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void removeClient(FanetixClient client) {
        try {
            em.remove(client);
        } catch (Exception e) {
            // throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public FanetixClient findClient(String email) {
        FanetixClient client = null;
        try {
            client = em.find(FanetixClient.class, email);
        } catch (Exception e) {
            //throw new InternalServerErrorException(e.getMessage());
        }
        return client;
    }

    @Override
    public List<FanetixClient> findAllClients() {
        List<FanetixClient> clients;
        clients = em.createNamedQuery("findAllClients").getResultList();
        return clients;
    }

}
