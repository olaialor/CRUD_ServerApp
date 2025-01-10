/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.AdministratorManagerLocal;
import eus.tartanga.crud.entities.Administrator;
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
    public void create(Administrator administrator) {
        try {
            LOGGER.log(Level.INFO, "Creating Administrator{0}", administrator.getEmail());
            ejb.create(administrator);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(Administrator administrator) {
        try {
            LOGGER.log(Level.INFO, "Updating Administrator{0}", administrator.getEmail());
            ejb.update(administrator);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{email}")
    public void remove(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Deleting Administrator{0}", email);
            ejb.remove(ejb.find(email));
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_XML)
    public Administrator find(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Reading data for Administrator{0}", email);
            return ejb.find(email);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //  throw new InternalServerErrorException(e.getMessage());
        }
       return ejb.find(email);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Administrator> findAll() {
        LOGGER.log(Level.INFO, "Reading data for all administrator{0}");
        return ejb.findAll();

    }

}
