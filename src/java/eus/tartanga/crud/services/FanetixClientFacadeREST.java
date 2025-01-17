/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.FanetixClientManagerLocal;
import eus.tartanga.crud.entities.FanetixClient;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Irati
 */
@Path("eus.tartanga.crud.entities.fanetixclient")
public class FanetixClientFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBFanetixClientManager")
    private FanetixClientManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(FanetixClientFacadeREST.class.getName());

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createClient(FanetixClient client) throws CreateException {
        try {
            LOGGER.log(Level.INFO, "Creating Client{0}", client.getEmail());
            ejb.createClient(client);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateClient(@PathParam("email") String email, FanetixClient client) throws UpdateException {
        try {
            LOGGER.log(Level.INFO, "Updating Client {0}", client.getEmail());
            ejb.updateClient(client);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @DELETE
    @Path("{email}")
    public void removeClient(@PathParam("email") String email) throws DeleteException {
        try {
            LOGGER.log(Level.INFO, "Deleting Client {0}", email);
            ejb.removeClient(ejb.findClient(email));
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new DeleteException(e.getMessage());
        } catch (ReadException ex) {
            Logger.getLogger(FanetixClientFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient findClient(@PathParam("email") String email) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Reading data for client {0}", email);
            return ejb.findClient(email);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FanetixClient> findAllClients() throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Reading data for all clients {0}");
            return ejb.findAllClients();
        } catch (ReadException ex) {
            Logger.getLogger(FanetixClientFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ejb.findAllClients();
    }
    
    @GET
    @Path("signIn/{email}/{passwd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient signIn(@PathParam("email") String email, @PathParam("passwd") String passwd) throws ReadException {
        try {
           return ejb.signIn(email, passwd);
        } catch (ReadException e) {
            LOGGER.severe("Error during sign-in process: " + e.getMessage());
            throw new ReadException("Sign-in failed: " + e.getMessage());
        }
    }
}
