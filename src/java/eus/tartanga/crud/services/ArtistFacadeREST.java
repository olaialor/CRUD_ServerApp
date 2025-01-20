package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.ArtistManagerLocal;
import eus.tartanga.crud.entities.Artist;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * RESTful Web Service for managing Artist entities. Provides CRUD operations
 * for artists: create, update, delete, and retrieve. Also allows searching
 * artists by search term and by debut dates.
 *
 * @author Olaia
 */
@Path("eus.tartanga.crud.entities.artist")
public class ArtistFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBArtistManager")
    private ArtistManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(ArtistFacadeREST.class.getName());

    /**
     * Creates a new artist.
     *
     * @param artist The artist entity to be created.
     * @throws WebApplicationException if an error occurs during the creation
     * process.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createArtist(Artist artist) {
        try {
            LOGGER.log(Level.INFO, "Creating Artist{0}", artist.getArtistId());
            ejb.createArtist(artist);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Error creating artist: {0}", e.getMessage());
            throw new WebApplicationException("Error creating artist", 400);
        }
    }

    /**
     * Updates an existing artist.
     *
     * @param artist The artist entity to be updated.
     * @throws WebApplicationException if an error occurs during the update
     * process.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateArtist(Artist artist) {
        try {
            LOGGER.log(Level.INFO, "Updating Artist {0}", artist.getArtistId());
            ejb.updateArtist(artist);
            LOGGER.log(Level.INFO, "Artist {0} updated successfully", artist.getArtistId());
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error updating artist: {0}", e.getMessage());
            throw new WebApplicationException("Error updating artist", 400);
        }
    }

    /**
     * Deletes an artist by their ID.
     *
     * @param id The ID of the artist to be deleted.
     * @throws WebApplicationException if an error occurs during the deletion
     * process.
     */
    @DELETE
    @Path("{id}")
    public void removeArtist(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Deleting Artist {0}", id);
            ejb.removeArtist(ejb.findArtist(id));
        } catch (DeleteException e) {
            LOGGER.log(Level.SEVERE, "Error deleting artist: {0}", e.getMessage());
            throw new WebApplicationException("Error deleting artist", 500);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error finding artist during deletion: {0}", e.getMessage());
            throw new WebApplicationException("Error finding artist", 404);
        }
    }

    /**
     * Finds an artist by their ID.
     *
     * @param id The ID of the artist to be found.
     * @return The artist entity, or throws a WebApplicationException if not
     * found.
     * @throws WebApplicationException if an error occurs during the search
     * process.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Artist findArtist(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Reading data for Artist {0}", id);
            Artist artist = ejb.findArtist(id);
            if (artist == null) {
                throw new WebApplicationException("Artist not found", 404);
            }
            return artist;
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error reading artist: {0}", e.getMessage());
            throw new WebApplicationException("Error reading artist", 500);
        }
    }

    /**
     * Retrieves a list of all artists.
     *
     * @return A list of all artist entities.
     * @throws WebApplicationException if an error occurs during the fetch
     * process.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Artist> findAllArtist() {

        try {
            LOGGER.log(Level.INFO, "Reading data for all artists");
            return ejb.findAllArtist();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error reading all artists: {0}", e.getMessage());
            throw new WebApplicationException("Error reading all artists", 500);
        }
    }

    /**
     * Searches for artists by a search term (e.g., name or company).
     *
     * @param searchTerm The search term to use for filtering artists.
     * @return A list of artists matching the search term.
     * @throws WebApplicationException if an error occurs during the search
     * process.
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> searchByTerm(@PathParam("searchTerm") String searchTerm) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Searching artists with term {0}", searchTerm);
            return ejb.ArtistFindBySearchTerm(searchTerm);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error searching artists: {0}", e.getMessage());
            throw new WebApplicationException("Error searching artists", 500);
        }
    }

    /**
     * Finds artists who debuted between two specific dates.
     *
     * @param startDate The start date in YYYY-MM-DD format.
     * @param endDate The end date in YYYY-MM-DD format.
     * @return A list of artists who debuted within the date range.
     * @throws WebApplicationException if an error occurs during the search
     * process.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> ArtistFindBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Finding artists between dates {0} and {1}", new Object[]{startDate, endDate});
            return ejb.ArtistFindBetweenDates(startDate, endDate);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error finding artists between dates: {0}", e.getMessage());
            throw new WebApplicationException("Error finding artists between dates", 500);
        }
    }

}
