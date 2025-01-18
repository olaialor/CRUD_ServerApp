package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Administrator;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 * Local interface for managing Administrator entities in the system. Provides
 * CRUD operations and a sign-in method for administrators.
 *
 * <p>
 * This interface defines the business methods available for managing
 * administrators. Implementations should handle persistence and exception
 * management appropriately.</p>
 *
 * @author Olaia
 */
@Local
public interface AdministratorManagerLocal {

    /**
     * Creates a new Administrator entity.
     *
     * @param administrator the Administrator entity to create.
     * @throws CreateException if there is an error during creation.
     */
    public void create(Administrator administrator) throws CreateException;

    /**
     * Updates an existing Administrator entity.
     *
     * @param administrator the Administrator entity with updated information.
     * @throws UpdateException if there is an error during the update.
     */
    public void update(Administrator administrator) throws UpdateException;

    /**
     * Removes an existing Administrator entity.
     *
     * @param administrator the Administrator entity to remove.
     * @throws DeleteException if there is an error during the deletion.
     */
    public void remove(Administrator administrator) throws DeleteException;

    /**
     * Finds an Administrator entity by email.
     *
     * @param email the email of the Administrator to find.
     * @return the Administrator entity found.
     * @throws ReadException if the Administrator is not found or if there is an
     * error during the search.
     */
    public Administrator find(String email) throws ReadException;

    /**
     * Retrieves all Administrator entities.
     *
     * @return a list of all Administrator entities.
     * @throws ReadException if there is an error during retrieval.
     */
    public List<Administrator> findAll() throws ReadException;

    /**
     * Signs in an Administrator using email and password.
     *
     * @param email the email of the Administrator.
     * @param passwd the password of the Administrator.
     * @return the Administrator entity if sign-in is successful.
     * @throws ReadException if the email or password is invalid, or if there is
     * an error during the operation.
     */
    public Administrator signIn(String email, String passwd) throws ReadException;
}