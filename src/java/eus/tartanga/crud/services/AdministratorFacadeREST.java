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
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import javax.ejb.EJB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * RESTful Web Service for managing Administrator entities. Provides endpoints
 * to create, update, delete, find, and list administrators. Also includes an
 * endpoint for administrator sign-in.
 *
 * @author Irati, Meylin
 */
@Path("eus.tartanga.crud.entities.administrator")
public class AdministratorFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBAdministratorManager")
    private AdministratorManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(AdministratorFacadeREST.class.getName());

    /**
     * Creates a new Administrator.
     *
     * @param administrator The Administrator object to be created.
     * @throws WebApplicationException If an error occurs during creation.
     */
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

    /**
     * Updates an existing Administrator based on the provided email.
     *
     * @param administrator The Administrator object with updated details.
     * @throws WebApplicationException If an error occurs during the update.
     */
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

    /**
     * Deletes an Administrator by email.
     *
     * @param email The email of the Administrator to be deleted.
     * @throws WebApplicationException If an error occurs during deletion or if
     * the Administrator is not found.
     */
    @DELETE
    @Path("{email}")
    public void remove(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Attempting to delete Administrator: {0}", email);
            ejb.remove(ejb.find(email));
            LOGGER.log(Level.INFO, "Administrator {0} deleted successfully", email);

        } catch (DeleteException e) {
            LOGGER.log(Level.SEVERE, "Error deleting administrator: {0}", e.getMessage());
            throw new WebApplicationException("Error deleting administrator", 500);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving administrator during deletion: {0}", e.getMessage());
            throw new WebApplicationException("Administrator not found", 404);
        }
    }

    /**
     * Finds an Administrator by email.
     *
     * @param email The email of the Administrator to find.
     * @return The Administrator with the specified email.
     * @throws WebApplicationException If an error occurs during retrieval or if
     * the Administrator is not found.
     */
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

    /**
     * Fetches all administrators.
     *
     * @return A list of all Administrators.
     * @throws WebApplicationException If an error occurs during retrieval.
     */
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

    /**
     * Signs in an Administrator using email and password.
     *
     * @param email The email of the Administrator.
     * @param passwd The password of the Administrator.
     * @return The signed-in Administrator.
     * @throws WebApplicationException If the sign-in fails (e.g., invalid
     * credentials).
     */
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
