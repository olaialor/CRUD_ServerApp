package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Artist;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EJB responsible for managing CRUD operations for the Artist entity. Provides
 * methods to create, update, delete, and find artists, as well as specific
 * searches with search terms and date ranges.
 *
 * @author olaia
 */
@Stateless
public class EJBArtistManager implements ArtistManagerLocal {

    private static final Logger logger = Logger.getLogger(EJBArtistManager.class.getName());

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    /**
     * Creates a new artist in the database.
     *
     * @param artist The Artist object to be created.
     * @throws CreateException If an error occurs while trying to create the
     * artist.
     */
    @Override
    public void createArtist(Artist artist) throws CreateException {
        try {
            em.persist(artist);
            logger.log(Level.INFO, "Artist created successfully: {0}", artist);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating artist: " + artist, e);
            throw new CreateException("Error creating artist: " + e.getMessage());
            
        }
    }

    /**
     * Updates an existing artist in the database.
     *
     * @param artist The Artist object with updated information.
     * @throws UpdateException If an error occurs while trying to update the
     * artist.
     */
    @Override
    public void updateArtist(Artist artist) throws UpdateException {
        try {
            em.merge(artist);
            logger.log(Level.INFO, "Artist updated successfully: {0}", artist);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating artist: " + artist, e);
            throw new UpdateException("Error updating artist: " + e.getMessage());
        }
    }

    /**
     * Deletes an artist from the database.
     *
     * @param artist The Artist object to be deleted.
     * @throws DeleteException If an error occurs while trying to delete the
     * artist.
     */
    @Override
    public void removeArtist(Artist artist) throws DeleteException {
        try {
            em.remove(artist);
            logger.log(Level.INFO, "Artist removed successfully: {0}", artist);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error removing artist: " + artist, e);
            throw new DeleteException("Error removing artist: " + e.getMessage());
        }
    }

    /**
     * Finds an artist in the database by their ID.
     *
     * @param artistId The ID of the artist to find.
     * @return The Artist object corresponding to the ID.
     * @throws ReadException If an error occurs while trying to find the artist.
     */
    @Override
    public Artist findArtist(Integer artistId) throws ReadException {
        Artist artist = null;
        try {
            artist = em.find(Artist.class, artistId);
            logger.log(Level.INFO, "Artist found: {0}", artist);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding artist with ID: " + artistId, e);
            throw new ReadException("Error finding artist with ID: " + artistId + e.getMessage());
        }
        return artist;
    }

    /**
     * Retrieves a list of all artists in the database.
     *
     * @return List of Artist objects.
     * @throws ReadException If an error occurs while trying to retrieve the
     * artists.
     */
    @Override
    public List<Artist> findAllArtist() throws ReadException {
        List<Artist> artist;
        try {
            artist = em.createNamedQuery("findAllArtist").getResultList();
            logger.info("All artists retrieved successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving all artists", e);
            throw new ReadException("Error retrieving all artists" + e.getMessage());
        }
        return artist;
    }

    /**
     * Finds artists in the database by their ID.
     *
     * @param artistId The ID of the artist to find.
     * @return List of Artist objects matching the ID.
     * @throws ReadException If an error occurs while trying to find the
     * artists.
     */
    @Override
    public List<Artist> findArtistById(Integer artistId) throws ReadException {
        List<Artist> artists;
        try {
            artists = em.createNamedQuery("findArtistById")
                    .setParameter("id", artistId)
                    .getResultList();
            logger.log(Level.INFO, "Artists with ID {0} retrieved successfully", artistId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding artists with ID: " + artistId, e);
            throw new ReadException("Error finding artists with ID: " + artistId + e.getMessage());
        }
        return artists;
    }

    /**
     * Finds artists in the database that match a search term.
     *
     * @param searchTerm The search term used to filter artists.
     * @return List of Artist objects that match the search term.
     * @throws ReadException If an error occurs while trying to find the
     * artists.
     */
    @Override
    public List<Artist> ArtistFindBySearchTerm(String searchTerm) throws ReadException {
        List<Artist> artists;
        try {
            artists = em.createNamedQuery("ArtistFindBySearchTerm", Artist.class)
                    .setParameter("searchTerm", searchTerm)
                    .getResultList();
            logger.log(Level.INFO, "Artists found by search term: {0}", searchTerm);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding artists by search term: " + searchTerm, e);
            throw new ReadException("Error finding artists by search term: " + searchTerm + e.getMessage());
        }
        return artists;
    }

    /**
     * Finds artists in the database that were created between two dates.
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return List of Artist objects created between the specified dates.
     * @throws ReadException If an error occurs while trying to find the
     * artists.
     */
    @Override
    public List<Artist> ArtistFindBetweenDates(String startDate, String endDate) throws ReadException {
        List<Artist> artists;
        try {
            artists = em.createNamedQuery("ArtistFindBetweenDates")
                    .setParameter("startDate", java.sql.Date.valueOf(startDate))
                    .setParameter("endDate", java.sql.Date.valueOf(endDate))
                    .getResultList();
            logger.log(Level.INFO, "Artists found between dates: {0} and {1}", new Object[]{startDate, endDate});
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding artists between dates: " + startDate + " and " + endDate, e);
            throw new ReadException("Error finding artists between dates" + e.getMessage());
        }
        return artists;
    }

}
