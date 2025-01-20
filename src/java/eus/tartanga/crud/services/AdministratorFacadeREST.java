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
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public Response create(Administrator administrator){
        try {
            LOGGER.log(Level.INFO, "Creating Administrator{0}", administrator.getEmail());
            ejb.create(administrator);
            return Response.status(Response.Status.CREATED).entity(administrator).build();
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create administrator: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response update(Administrator administrator) {
        try {
            LOGGER.log(Level.INFO, "Updating Administrator: {0}", administrator.getEmail());
            ejb.update(administrator);
            return Response.status(Response.Status.OK).entity(administrator).build();
        } catch (UpdateException e) {
            LOGGER.severe("Error updating administrator: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update administrator: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{email}")
    public Response remove(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Deleting Administrator: {0}", email);
            Administrator admin = ejb.find(email);
            if (admin == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Administrator not found").build();
            }
            ejb.remove(admin);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (DeleteException e) {
            LOGGER.severe("Error deleting administrator: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete administrator: " + e.getMessage()).build();
        } catch (ReadException ex) {
            LOGGER.severe("Administrator not found during deletion: " + ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity("Administrator not found").build();
        }
    }

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_XML)
    public Response find(@PathParam("email") String email) {
        try {
            Administrator admin = ejb.find(email);
            if (admin == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Administrator not found").build();
            }
            return Response.status(Response.Status.OK).entity(admin).build();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error finding administrator: {0}", ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error finding administrator").build();
        }
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

    @GET
    @Path("signIn/{email}/{passwd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Administrator signIn(@PathParam("email") String email, @PathParam("passwd") String passwd) throws ReadException {
        try {
            return ejb.signIn(email, passwd);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error during sign-in process: {0}", e.getMessage());
            throw new ReadException("Sign-in failed: " + e.getMessage());
        }
    }
}
