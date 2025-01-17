/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.AdministratorManagerLocal;
import eus.tartanga.crud.entities.Administrator;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
 * @author 2dam
 */
@Path("eus.tartanga.crud.entities.administrator")
public class AdministratorFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBAdministratorManager")
    private AdministratorManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(AdministratorFacadeREST.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Administrator administrator) throws CreateException {
        try {
            LOGGER.log(Level.INFO, "Creating Administrator{0}", administrator.getEmail());
            ejb.create(administrator);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(Administrator administrator) throws UpdateException {
        try {
            LOGGER.log(Level.INFO, "Updating Administrator{0}", administrator.getEmail());
            ejb.update(administrator);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @DELETE
    @Path("{email}")
    public void remove(@PathParam("email") String email) throws DeleteException, ReadException {
        try {
            LOGGER.log(Level.INFO, "Deleting Administrator{0}", email);
            ejb.remove(ejb.find(email));
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new DeleteException(e.getMessage());
        } catch (ReadException ex) {
            Logger.getLogger(AdministratorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_XML)
    public Administrator find(@PathParam("email") String email) throws ReadException {
        try {
            return ejb.find(email);
        } catch (ReadException ex) {
            Logger.getLogger(AdministratorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ejb.find(email);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Administrator> findAll() throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Reading data for all administrator{0}");
            return ejb.findAll();
        } catch (ReadException ex) {
            Logger.getLogger(AdministratorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ejb.findAll();

    }

}
