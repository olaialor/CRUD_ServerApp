package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.FanetixClient;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * Local interface for managing Fanetix clients.
 * Provides methods to handle CRUD operations and client authentication.
 * 
 * @author Irati
 */
@Local
public interface FanetixClientManagerLocal {

    /**
     * Creates a new client entry.
     * 
     * @param client The FanetixClient entity to be created.
     * @throws CreateException If an error occurs while creating the client.
     */
    public void createClient(FanetixClient client) throws CreateException;

    /**
     * Updates the details of an existing client.
     * 
     * @param client The FanetixClient entity with updated information.
     * @throws UpdateException If an error occurs while updating the client.
     */
    public void updateClient(FanetixClient client) throws UpdateException;

    /**
     * Removes a client entry.
     * 
     * @param client The FanetixClient entity to be removed.
     * @throws DeleteException If an error occurs while removing the client.
     */
    public void removeClient(FanetixClient client) throws DeleteException;

    /**
     * Finds a specific client by their email.
     * 
     * @param email The email of the client to be retrieved.
     * @return The FanetixClient entity matching the given email.
     * @throws ReadException If an error occurs while finding the client.
     */
    public FanetixClient findClient(String email) throws ReadException;

    /**
     * Retrieves all clients.
     * 
     * @return A list of all FanetixClient entities.
     * @throws ReadException If an error occurs while retrieving the clients.
     */
    public List<FanetixClient> findAllClients() throws ReadException;

    /**
     * Authenticates a client using their email and password.
     * 
     * @param email The email of the client attempting to sign in.
     * @param passwd The password of the client.
     * @return The FanetixClient entity if authentication is successful.
     * @throws ReadException If an error occurs during authentication.
     */
    public FanetixClient signIn(String email, String passwd) throws ReadException;

}
