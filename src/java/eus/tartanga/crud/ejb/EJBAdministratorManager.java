package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Administrator;
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
import javax.persistence.TypedQuery;

/**
 * Stateless EJB for managing Administrator entities.
 * <p>
 * This class provides methods to perform CRUD operations and authentication for
 * Administrator entities. It interacts with the database through JPA.
 * </p>
 *
 * @author Irati,Meylin,Olaia,Elbire
 */
@Stateless
public class EJBAdministratorManager implements AdministratorManagerLocal {

    /**
     * Entity Manager for interacting with the persistence context.
     */
    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;
    /**
     * Logger for logging messages and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(EJBAdministratorManager.class.getName());

    /**
     * Creates a new Administrator entity in the database.
     *
     * @param administrator The Administrator entity to be created.
     * @throws CreateException If there is an error while persisting the entity.
     */
    //El administrador no puede hacer signUp, por ende no pueden crearse administradores
    //no deberiamos cargarnos este? ATT:Meylin 
    @Override
    public void create(Administrator administrator) throws CreateException {
        try {
            LOGGER.log(Level.INFO, "Creating Administrator with email: {0}", administrator.getEmail());
            em.persist(administrator);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating Administrator: {0}", e.getMessage());
            throw new CreateException("Unable to create Administrator: " + e.getMessage());
        }
    }

    /**
     * Updates an existing Administrator entity in the database.
     *
     * @param administrator The Administrator entity to be updated.
     * @throws UpdateException If there is an error while merging the entity.
     */
    @Override
    public void update(Administrator administrator) throws UpdateException {
        try {
            LOGGER.log(Level.INFO, "Updating Administrator with email: {0}", administrator.getEmail());
            em.merge(administrator);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating Administrator: {0}", e.getMessage());
            throw new UpdateException("Unable to update Administrator: " + e.getMessage());
        }
    }

    /**
     * Deletes an Administrator entity from the database.
     *
     * @param administrator The Administrator entity to be deleted.
     * @throws DeleteException If there is an error while removing the entity.
     */
    //BTW tampoco tenemos un caso en el que un usuario pueda borrar su cuenta
    @Override
    public void remove(Administrator administrator) throws DeleteException {
        try {
            LOGGER.log(Level.INFO, "Deleting Administrator with email: {0}", administrator.getEmail());
            Administrator managedAdmin = em.contains(administrator) ? administrator : em.merge(administrator);
            em.remove(managedAdmin);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting Administrator: {0}", e.getMessage());
            throw new DeleteException("Unable to delete Administrator: " + e.getMessage());
        }
    }

    /**
     * Finds an Administrator entity by its email.
     *
     * @param email The email of the Administrator to find.
     * @return The Administrator entity, or null if not found.
     * @throws ReadException If there is an error during the retrieval.
     */
    @Override
    public Administrator find(String email) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Finding Administrator with email: {0}", email);
            return em.find(Administrator.class, email);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding Administrator: {0}", e.getMessage());
            throw new ReadException("Unable to find Administrator: " + e.getMessage());
        }
    }

    /**
     * Retrieves all Administrator entities from the database.
     *
     * @return A list of Administrator entities.
     * @throws ReadException If there is an error during the retrieval.
     */
    @Override
    public List<Administrator> findAll() throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Finding all Administrators");
            TypedQuery<Administrator> query = em.createQuery("SELECT a FROM Administrator a", Administrator.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all Administrators: {0}", e.getMessage());
            throw new ReadException("Unable to retrieve Administrators: " + e.getMessage());
        }
    }

    /**
     * Authenticates an Administrator using their email and password.
     *
     * @param email The email of the Administrator.
     * @param passwd The password of the Administrator.
     * @return The authenticated Administrator entity.
     * @throws ReadException If authentication fails or an error occurs during
     * retrieval.
     */
    @Override
    public Administrator signIn(String email, String passwd) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Attempting sign-in for email: {0}", email);
            Administrator admin = em.createNamedQuery("adminSignIn", Administrator.class)
                    .setParameter("email", email)
                    .setParameter("passwd", passwd)
                    .getSingleResult();
            LOGGER.log(Level.INFO, "Sign-in successful for email: {0}", email);
            return admin;
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "Sign-in failed for email: {0} - Invalid email or password.", email);
            throw new ReadException("Invalid email or password. " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred during sign-in for email: {0} - {1}",
                    new Object[]{email, e.getMessage()});
            throw new ReadException("An error occurred during sign-in. Please try again later. " + e.getMessage());
        }
    }

}
