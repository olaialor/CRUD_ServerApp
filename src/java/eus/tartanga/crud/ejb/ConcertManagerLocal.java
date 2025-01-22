package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Concert;
import java.util.List;
import javax.ejb.Local;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;

/**
 * Local interface for managing concert operations. Provides methods to handle
 * CRUD operations and specific queries related to concerts.
 *
 * @author Irati
 */
@Local
public interface ConcertManagerLocal {

    /**
     * Creates a new concert entry.
     *
     * @param concert The Concert entity to be created.
     * @throws CreateException If an error occurs while creating the concert.
     */
    public void createConcert(Concert concert) throws CreateException;

    /**
     * Updates the details of an existing concert.
     *
     * @param concert The Concert entity with updated information.
     * @throws UpdateException If an error occurs while updating the concert.
     */
    public void updateConcert(Concert concert) throws UpdateException;

    /**
     * Removes a concert entry.
     *
     * @param concert The Concert entity to be removed.
     * @throws DeleteException If an error occurs while removing the concert.
     */
    public void removeConcert(Concert concert) throws DeleteException;

    /**
     * Finds a specific concert by its ID.
     *
     * @param id The ID of the concert to be retrieved.
     * @return The Concert entity matching the given ID.
     * @throws ReadException If an error occurs while finding the concert.
     */
    public Concert findConcert(Integer id) throws ReadException;

    /**
     * Retrieves all concerts.
     *
     * @return A list of all Concert entities.
     * @throws ReadException If an error occurs while retrieving the concerts.
     */
    public List<Concert> findAllConcerts() throws ReadException;

    /**
     * Searches for concerts based on a search term.
     *
     * @param searchTerm The term to search for in concert data.
     * @return A list of Concert entities matching the search term.
     * @throws ReadException If an error occurs while searching for concerts.
     */
    public List<Concert> searchByTerm(String searchTerm) throws ReadException;

    /**
     * Retrieves all concerts that are coming soon.
     *
     * @return A list of Concert entities scheduled to occur in the near future.
     * @throws ReadException If an error occurs while retrieving upcoming
     * concerts.
     */
    public List<Concert> findComingSoon() throws ReadException;

    /**
     * Retrieves concerts occurring between two specific dates.
     *
     * @param startDate The start date of the range (inclusive).
     * @param endDate The end date of the range (inclusive).
     * @return A list of Concert entities within the specified date range.
     * @throws ReadException If an error occurs while retrieving concerts by
     * date range.
     */
    public List<Concert> findBetweenDates(String startDate, String endDate) throws ReadException;
}
