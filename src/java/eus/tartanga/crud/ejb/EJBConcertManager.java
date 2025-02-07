package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Concert;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless session bean that manages operations on Concert entities. This bean
 * provides methods for creating, updating, deleting, and searching concerts. It
 * interacts with the EntityManager to perform database operations and handles
 * exceptions related to CRUD operations.
 *
 * @author Irati
 */
@Stateless
public class EJBConcertManager implements ConcertManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(EJBConcertManager.class.getName());

    /**
     * Creates a new concert in the database.
     *
     * @param concert the concert to be created
     * @throws CreateException if an error occurs while creating the concert
     */
    @Override
    public void createConcert(Concert concert) throws CreateException {
        try {
            em.persist(concert);
            LOGGER.log(Level.INFO, "Concert created: {0}", concert);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error creating concert: " + e.getMessage(), e);
            throw new CreateException("Error while creating the concert: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error creating concert: " + e.getMessage(), e);
            throw new CreateException("Unexpected error while creating the concert." + e.getMessage());
        }
    }

    /**
     * Updates an existing concert in the database.
     *
     * @param concert the concert to be updated
     * @throws UpdateException if an error occurs while updating the concert
     */
    @Override
    public void updateConcert(Concert concert) throws UpdateException {
        try {
            em.merge(concert);
            LOGGER.log(Level.INFO, "Concert updated: {0}", concert);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error updating concert: " + e.getMessage(), e);
            throw new UpdateException("Error while updating the concert: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error updating concert: " + e.getMessage(), e);
            throw new UpdateException("Unexpected error while updating the concert." + e.getMessage());
        }
    }

    /**
     * Removes a concert from the database.
     *
     * @param concertId the concert to be removed
     * @throws DeleteException if an error occurs while removing the concert
     */
    @Override
    public void removeConcert(Integer concertId) throws DeleteException {
        try {
            Concert concert = em.find(Concert.class, concertId);
            if (concert != null) {
                em.remove(em.contains(concert) ? concert : em.merge(concert));
            }
            LOGGER.log(Level.INFO, "Concert removed: {0}", concert);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error removing concert: " + e.getMessage(), e);
            throw new DeleteException("Error while removing the concert: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error removing concert: " + e.getMessage(), e);
            throw new DeleteException("Unexpected error while removing the concert." + e.getMessage());
        }
    }

    /**
     * Finds a concert by its ID.
     *
     * @param id the ID of the concert to be found
     * @return the concert with the given ID, or null if not found
     * @throws ReadException if an error occurs while retrieving the concert
     */
    @Override
    public Concert findConcert(Integer id) throws ReadException {
        try {
            Concert concert = em.find(Concert.class, id);
            LOGGER.log(Level.INFO, "Concert found: {0}", concert);
            return concert;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error finding concert with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new ReadException("Error while finding the concert with ID " + id + ": " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error finding concert with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new ReadException("Unexpected error while finding the concert." + e.getMessage());
        }
    }

    /**
     * Retrieves all concerts from the database.
     *
     * @return a list of all concerts
     * @throws ReadException if an error occurs while retrieving the concerts
     */
    @Override
    public List<Concert> findAllConcerts() throws ReadException {
        try {
            List<Concert> concerts = em.createNamedQuery("findAllConcerts", Concert.class).getResultList();
            LOGGER.info("All concerts retrieved successfully");
            return concerts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error retrieving all concerts: " + e.getMessage(), e);
            throw new ReadException("Error while retrieving all concerts: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error retrieving all concerts: " + e.getMessage(), e);
            throw new ReadException("Unexpected error while retrieving all concerts." + e.getMessage());
        }
    }

    /**
     * Searches for concerts by a given search term.
     *
     * @param searchTerm the search term used to find concerts
     * @return a list of concerts that match the search term
     * @throws ReadException if an error occurs while searching for concerts
     */
    @Override
    public List<Concert> searchByTerm(String searchTerm) throws ReadException {
        try {
            List<Concert> concerts = em.createNamedQuery("ConcertFindBySearchTerm", Concert.class)
                    .setParameter("searchTerm", "%" + searchTerm + "%")
                    .getResultList();
            LOGGER.log(Level.INFO, "Concerts found by search term \"{0}\": {1}", new Object[]{searchTerm, concerts});
            return concerts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error searching concerts by term \"{0}\": {1}", new Object[]{searchTerm, e.getMessage()});
            throw new ReadException("Error while searching concerts by term: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error searching concerts by term \"{0}\": {1}", new Object[]{searchTerm, e.getMessage()});
            throw new ReadException("Unexpected error while searching concerts by term." + e.getMessage());
        }
    }

    /**
     * Retrieves concerts that are coming soon.
     *
     * @return a list of concerts that are scheduled in the near future
     * @throws ReadException if an error occurs while retrieving the concerts
     */
    @Override
    public List<Concert> findComingSoon() throws ReadException {
        try {
            List<Concert> concerts = em.createNamedQuery("ConcertComingSoon", Concert.class).getResultList();
            LOGGER.info("Found coming soon concerts");
            return concerts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Persistence error retrieving coming soon concerts: " + e.getMessage(), e);
            throw new ReadException("Error while retrieving coming soon concerts: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error retrieving coming soon concerts: " + e.getMessage(), e);
            throw new ReadException("Unexpected error while retrieving coming soon concerts." + e.getMessage());
        }
    }

    /**
     * Finds concerts within a specific date range.
     *
     * @param startDate the start date of the range (in string format)
     * @param endDate the end date of the range (in string format)
     * @return a list of concerts within the specified date range
     * @throws ReadException if an error occurs while retrieving the concerts
     */
    @Override
    public List<Concert> findBetweenDates(String startDate, String endDate) throws ReadException { //Revisar si puede ser string o tiene que ser localDate
        try {
            List<Concert> concerts = em.createNamedQuery("ConcertFindBetweenDates", Concert.class)
                    .setParameter("startDate", java.sql.Date.valueOf(startDate))
                    .setParameter("endDate", java.sql.Date.valueOf(endDate))
                    .getResultList();
            LOGGER.log(Level.INFO, "Found concerts between dates {0} and {1}: {2}", new Object[]{startDate, endDate, concerts});
            return concerts;
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error finding concerts between dates {0} and {1}: {2}", new Object[]{startDate, endDate, e.getMessage()});
            throw new ReadException("Error while finding concerts between dates: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error finding concerts between dates {0} and {1}: {2}", new Object[]{startDate, endDate, e.getMessage()});
            throw new ReadException("Unexpected error while finding concerts between dates." + e.getMessage());
        }
    }
}
