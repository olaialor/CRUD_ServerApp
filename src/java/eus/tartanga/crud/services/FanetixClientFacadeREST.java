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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST facade for managing FanetixClient entities. This class provides methods
 * for creating, updating, deleting, retrieving, and authenticating clients via
 * REST API.
 *
 * @author Irati
 */
@Path("eus.tartanga.crud.entities.fanetixclient")
public class FanetixClientFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBFanetixClientManager")
    private FanetixClientManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(FanetixClientFacadeREST.class.getName());

    /**
     * Creates a new client.
     *
     * @param client The FanetixClient entity to create.
     * @throws InternalServerErrorException If an error occurs during client
     * creation.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createClient(FanetixClient client) {
        try {
            LOGGER.log(Level.INFO, "Creating client with ID: {0}", client.getEmail());
            ejb.createClient(client);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error creating client: " + e.getMessage());
        }
    }

    /**
     * Updates the details of an existing client.
     *
     * @param email The email of the client to update.
     * @param client The FanetixClient entity with updated details.
     * @throws InternalServerErrorException If an error occurs during client
     * update.
     */
    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateClient(@PathParam("email") String email, FanetixClient client) {
        try {
            LOGGER.log(Level.INFO, "Updating client with ID: {0}", email);
            client.setEmail(email);
            ejb.updateClient(client);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error updating client: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing client by email.
     *
     * @param email The email of the client to delete.
     * @throws InternalServerErrorException If an error occurs during client
     * deletion.
     */
    @DELETE
    @Path("{email}")
    public void removeClient(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Deleting client with email: {0}", email);
            ejb.removeClient(ejb.findClient(email));
            LOGGER.log(Level.INFO, "Client {0} deleted successfully", email);
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error deleting client: " + e.getMessage());
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error reading client data: {0}", e.getMessage());
            throw new InternalServerErrorException("Error retrieving client: " + e.getMessage());
        }
    }

    /**
     * Retrieves a client by email.
     *
     * @param email The email of the client to retrieve.
     * @return The FanetixClient entity.
     * @throws InternalServerErrorException If an error occurs during client
     * retrieval.
     */
    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient findClient(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Reading data for client with email: {0}", email);
            return ejb.findClient(email);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding client: " + e.getMessage());
        }
    }

    /**
     * Retrieves all clients.
     *
     * @return A list of all FanetixClient entities.
     * @throws InternalServerErrorException If an error occurs during retrieval.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FanetixClient> findAllClients() {
        try {
            LOGGER.log(Level.INFO, "Reading data for all clients");
            return ejb.findAllClients();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving all clients: " + e.getMessage());
        }
    }

    /**
     * Authenticates a client by email and password.
     *
     * @param email The email of the client.
     * @param passwd The password of the client.
     * @return The authenticated FanetixClient entity.
     * @throws InternalServerErrorException If an error occurs during the
     * sign-in process.
     */
    @GET
    @Path("signIn/{email}/{passwd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient signIn(@PathParam("email") String email, @PathParam("passwd") String passwd) {
        try {
            LOGGER.log(Level.INFO, "Signing in client with email: {0}", email);
            return ejb.signIn(email, passwd);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error during sign-in process for email {0}: {1}", new Object[]{email, e.getMessage()});
            throw new InternalServerErrorException("Sign-in failed: " + e.getMessage());
        }
    }
}
