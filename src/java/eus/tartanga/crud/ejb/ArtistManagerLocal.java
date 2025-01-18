package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Artist;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * Local interface for managing Artist entities in the CRUD application. This
 * interface defines the operations for creating, updating, deleting, and
 * retrieving Artist data.
 *
 * @author olaia
 */
@Local
public interface ArtistManagerLocal {

    /**
     * Creates a new Artist entity in the system.
     *
     * @param artist the Artist entity to create.
     * @throws CreateException if there is an error during the creation process.
     */
    public void createArtist(Artist artist) throws CreateException;

    /**
     * Updates an existing Artist entity in the system.
     *
     * @param artist the Artist entity with updated data.
     * @throws UpdateException if there is an error during the update process.
     */
    public void updateArtist(Artist artist) throws UpdateException;

    /**
     * Removes an Artist entity from the system.
     *
     * @param artist the Artist entity to remove.
     * @throws DeleteException if there is an error during the deletion process.
     */
    public void removeArtist(Artist artist) throws DeleteException;

    /**
     * Retrieves an Artist entity by its unique ID.
     *
     * @param artistId the unique identifier of the Artist entity.
     * @return the Artist entity matching the specified ID.
     * @throws ReadException if there is an error during the retrieval process.
     */
    public Artist findArtist(Integer artistId) throws ReadException;

    /**
     * Retrieves a list of all Artist entities in the system.
     *
     * @return a list of all Artist entities.
     * @throws ReadException if there is an error during the retrieval process.
     */
    public List<Artist> findAllArtist() throws ReadException;

    /**
     * Retrieves a list of Artist entities matching the specified ID.
     *
     * @param artistId the unique identifier of the Artist entities to retrieve.
     * @return a list of Artist entities matching the specified ID.
     * @throws ReadException if there is an error during the retrieval process.
     */
    public List<Artist> findArtistById(Integer artistId) throws ReadException;

    /**
     * Searches for Artist entities that match the specified search term.
     *
     * @param searchTerm the term to search for in Artist entities.
     * @return a list of Artist entities that match the search term.
     * @throws ReadException if there is an error during the search process.
     */
    public List<Artist> ArtistFindBySearchTerm(String searchTerm) throws ReadException;

    /**
     * Retrieves a list of Artist entities with creation dates between the
     * specified start and end dates.
     *
     * @param startDate the start date for the range.
     * @param endDate the end date for the range.
     * @return a list of Artist entities created between the specified dates.
     * @throws ReadException if there is an error during the retrieval process.
     */
    public List<Artist> ArtistFindBetweenDates(String startDate, String endDate) throws ReadException;

}
