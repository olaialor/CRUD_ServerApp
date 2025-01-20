package eus.tartanga.crud.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import eus.tartanga.crud.ejb.AdministratorManagerLocal;
import eus.tartanga.crud.entities.Administrator;
import eus.tartanga.crud.entities.Product;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Irati,Meylin
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
            LOGGER.log(Level.INFO, "Creating Administrator: {0}", administrator.getEmail());
            ejb.create(administrator);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Error creating administrator: {0}", e.getMessage());
            throw new WebApplicationException("Error creating administrator", 400);
        }
    }

    @PUT
    @Path("{email}")
    @Consumes(MediaType.APPLICATION_XML)
    public void update(Administrator administrator) {
        try {
            LOGGER.log(Level.INFO, "Updating Administrator: {0}", administrator.getEmail());
            ejb.update(administrator);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error updating administrator: {0}", e.getMessage());
            throw new WebApplicationException("Error updating administrator", 400);
        }
    }
    
   @DELETE
@Path("{email}")
public void remove(@PathParam("email") String email) {
    try {
        LOGGER.log(Level.INFO, "Attempting to delete Administrator: {0}", email);
        Administrator admin = ejb.find(email);
        
        if (admin == null) {
            LOGGER.log(Level.WARNING, "Administrator not found with email: {0}", email);
            throw new WebApplicationException("Administrator not found", 404);
        }else{
        
        // Confirmar que el administrador ha sido encontrado
        LOGGER.log(Level.INFO, "Administrator {0} found, proceeding with deletion", email);
        ejb.remove(admin);
        // Confirmar eliminación
        LOGGER.log(Level.INFO, "Administrator {0} deleted successfully", email);
        }
    } catch (DeleteException e) {
        LOGGER.log(Level.SEVERE, "Error deleting administrator: {0}", e.getMessage());
        throw new WebApplicationException("Error deleting administrator", 500);
    } catch (ReadException e) {
        LOGGER.log(Level.SEVERE, "Error retrieving administrator during deletion: {0}", e.getMessage());
        throw new WebApplicationException("Administrator not found", 404);
    }
}


    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_XML)
    public Administrator find(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Finding Administrator: {0}", email);
            Administrator admin = ejb.find(email);
            if (admin == null) {
                throw new WebApplicationException("Administrator not found", 404);
            }
            return admin;
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error finding administrator: {0}", e.getMessage());
            throw new WebApplicationException("Error finding administrator", 500);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Administrator> findAll() {
        try {
            LOGGER.log(Level.INFO, "Fetching all Administrators");
            return ejb.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error fetching administrators: {0}", e.getMessage());
            throw new WebApplicationException("Error fetching administrators", 500);
        }
    }

    @GET
    @Path("signIn/{email}/{passwd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Administrator signIn(@PathParam("email") String email, @PathParam("passwd") String passwd) {
        try {
            LOGGER.log(Level.INFO, "Signing in Administrator: {0}", email);
            return ejb.signIn(email, passwd);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error during sign-in process: {0}", e.getMessage());
            throw new WebApplicationException("Sign-in failed", 401);
        }
    }
}
