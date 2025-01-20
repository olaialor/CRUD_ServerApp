package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.FanetixClient;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 * EJB for managing FanetixClient entities. Provides basic CRUD operations for
 * creating, reading, updating, and deleting clients, as well as a sign-in
 * feature. This class handles persistence operations using the EntityManager
 * and logs the process at various stages for debugging and operational
 * purposes.
 * <p>
 * This EJB is stateless and provides an abstraction layer for interacting with
 * the database for FanetixClient entity management.
 * </p>
 *
 * @author Meylin,Olaia,Irati,Elbire
 */
@Stateless
public class EJBFanetixClientManager implements FanetixClientManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(EJBFanetixClientManager.class.getName());

    /**
     * Creates a new FanetixClient in the database.
     * <p>
     * This method persists the provided FanetixClient object to the database.
     * In case of an error, a {@link CreateException} is thrown.
     * </p>
     *
     * @param client The FanetixClient to be created.
     * @throws CreateException If an error occurs during the creation process.
     */
    @Override
    public void createClient(FanetixClient client) throws CreateException {
        try {
            em.persist(client);
            LOGGER.log(Level.INFO, "Client created: {0}", client);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error creating client: {0}", e.getMessage());
            throw new CreateException("Error creating client: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error creating client: {0}", e.getMessage());
            throw new CreateException("Unexpected error while creating client." + e.getMessage());
        }
    }

    /**
     * Updates an existing FanetixClient in the database.
     * <p>
     * This method merges the provided FanetixClient object, updating the
     * database record. In case of an error, an {@link UpdateException} is
     * thrown.
     * </p>
     *
     * @param client The FanetixClient to be updated.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateClient(FanetixClient client) throws UpdateException {
        try {
            em.merge(client);
            LOGGER.log(Level.INFO, "Client updated: {0}", client);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error updating client: {0}", e.getMessage());
            throw new UpdateException("Error updating client: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error updating client: {0}", e.getMessage());
            throw new UpdateException("Unexpected error while updating client." + e.getMessage());
        }
    }

    /**
     * Removes a FanetixClient from the database.
     * <p>
     * This method removes the specified FanetixClient object from the database.
     * In case of an error, a {@link DeleteException} is thrown.
     * </p>
     *
     * @param client The FanetixClient to be removed.
     * @throws DeleteException If an error occurs during the removal process.
     */
    @Override
    public void removeClient(FanetixClient client) throws DeleteException {
        try {
            em.remove(em.contains(client) ? client : em.merge(client));
            LOGGER.log(Level.INFO, "Client removed: {0}", client);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error removing client: {0}", e.getMessage());
            throw new DeleteException("Error removing client: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error removing client: {0}", e.getMessage());
            throw new DeleteException("Unexpected error while removing client." + e.getMessage());
        }
    }

    /**
     * Finds a FanetixClient by their email.
     * <p>
     * This method queries the database for a client using the provided email.
     * In case the client is not found, it returns null. In case of errors, a
     * {@link ReadException} is thrown.
     * </p>
     *
     * @param email The email of the client to be found.
     * @return The FanetixClient object, or null if not found.
     * @throws ReadException If an error occurs during the retrieval process.
     */
    @Override
    public FanetixClient findClient(String email) throws ReadException {
        try {
            FanetixClient client = em.find(FanetixClient.class, email);
            LOGGER.log(Level.INFO, "Client found: {0}", client);
            return client;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error finding client with email {0}: {1}", new Object[]{email, e.getMessage()});
            throw new ReadException("Error finding client: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error finding client with email {0}: {1}", new Object[]{email, e.getMessage()});
            throw new ReadException("Unexpected error while finding client." + e.getMessage());
        }
    }

    /**
     * Retrieves all FanetixClient entities from the database.
     * <p>
     * This method queries the database and returns a list of all clients. In
     * case of errors, a {@link ReadException} is thrown.
     * </p>
     *
     * @return A list of all FanetixClient entities.
     * @throws ReadException If an error occurs during the retrieval process.
     */
    @Override
    public List<FanetixClient> findAllClients() throws ReadException {
        try {
            List<FanetixClient> clients = em.createNamedQuery("findAllClients", FanetixClient.class).getResultList();
            LOGGER.info("All clients retrieved successfully.");
            return clients;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error retrieving all clients: {0}", e.getMessage());
            throw new ReadException("Error retrieving all clients: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error retrieving all clients: {0}", e.getMessage());
            throw new ReadException("Unexpected error while retrieving all clients." + e.getMessage());
        }
    }

    /**
     * Signs in a client using their email and password.
     * <p>
     * This method queries the database for a client matching the provided email
     * and password. If no result is found, it returns null. In case of errors,
     * a {@link ReadException} is thrown.
     * </p>
     *
     * @param email The email of the client.
     * @param passwd The password of the client.
     * @return The signed-in FanetixClient, or null if no client matches the
     * credentials.
     * @throws ReadException If an error occurs during the sign-in process.
     */
    @Override
    public FanetixClient signIn(String email, String passwd) throws ReadException {
        try {
            FanetixClient fanetixClient = em.createNamedQuery("clientSignIn", FanetixClient.class)
                    .setParameter("email", email)
                    .setParameter("passwd", passwd)
                    .getSingleResult();
            LOGGER.log(Level.INFO, "Client signed in: {0}", fanetixClient);
            return fanetixClient;
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "No client found for email: {0}", email);
            return null; // Return null if no result is found.
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error signing in client: {0}", e.getMessage());
            throw new ReadException("Error signing in client: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error signing in client: {0}", e.getMessage());
            throw new ReadException("Unexpected error while signing in client." + e.getMessage());
        }
    }
}
