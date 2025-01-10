/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.FanetixClientManagerLocal;
import eus.tartanga.crud.entities.FanetixClient;
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
    public void createClient(FanetixClient client) {
        try {
            LOGGER.log(Level.INFO, "Creating Client{0}", client.getEmail());
            ejb.createClient(client);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateClient(@PathParam("email") String email, FanetixClient client) {
        try {
            LOGGER.log(Level.INFO, "Updating Client {0}", client.getEmail());
            ejb.updateClient(client);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{email}")
    public void removeClient(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Deleting Client {0}", email);
            ejb.removeClient(ejb.findClient(email));
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient findClient(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Reading data for client {0}", email);
            return ejb.findClient(email);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
        return ejb.findClient(email);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FanetixClient> findAllClients() {
        LOGGER.log(Level.INFO, "Reading data for all clients {0}");
        return ejb.findAllClients();
    }
}
