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
import javax.ws.rs.InternalServerErrorException;
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
            LOGGER.log(Level.INFO, "Creating client with ID: {0}", client.getEmail());
            ejb.createClient(client);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error creating client: " + e.getMessage());
        }
    }

    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateClient(@PathParam("email") String email, FanetixClient client)  {
        try {
            LOGGER.log(Level.INFO, "Updating client with ID: {0}", email);
            client.setEmail(email);
            ejb.updateClient(client);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error updating client: " + e.getMessage());
        }
    }

    @DELETE
    @Path("{email}")
    public void removeClient(@PathParam("email") String email)  {
         try {
            LOGGER.log(Level.INFO, "Deleting client with email: {0}", email);
            FanetixClient client = ejb.findClient(email);
            if (client != null) {
                ejb.removeClient(client);
            } else {
                throw new DeleteException("Client with email " + email + " not found");
            }
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error deleting client: " + e.getMessage());
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error reading client data: {0}", e.getMessage());
            throw new InternalServerErrorException("Error retrieving client: " + e.getMessage());
        }
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient findClient(@PathParam("email") String email)  {
        try {
            LOGGER.log(Level.INFO, "Reading data for client with email: {0}", email);
            return ejb.findClient(email);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding client: " + e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FanetixClient> findAllClients() throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Reading data for all clients");
            return ejb.findAllClients();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving all clients: " + e.getMessage());
        }
    }
    
    @GET
    @Path("signIn/{email}/{passwd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FanetixClient signIn(@PathParam("email") String email, @PathParam("passwd") String passwd)  {
        try {
            LOGGER.log(Level.INFO, "Signing in client with email: {0}", email);
            return ejb.signIn(email, passwd);
        } catch (ReadException e) {
            LOGGER.severe("Error during sign-in process for email " + email + ": " + e.getMessage());
            throw new InternalServerErrorException("Sign-in failed: " + e.getMessage());
        }
    }
}
