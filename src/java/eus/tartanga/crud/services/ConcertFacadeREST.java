package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.ConcertManagerLocal;
import eus.tartanga.crud.entities.Concert;
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
@Path("eus.tartanga.crud.entities.concert")
public class ConcertFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBConcertManager")
    private ConcertManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(ConcertFacadeREST.class.getName());

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createConcert(Concert concert) {
        try {
            LOGGER.log(Level.INFO, "Creating concert with ID: {0}", concert.getConcertId());
            ejb.createConcert(concert);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error creating concert: " + e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateConcert(@PathParam("id") Integer id, Concert concert) {
        try {
            LOGGER.log(Level.INFO, "Updating concert with ID: {0}", id);
            ejb.updateConcert(concert);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error updating concert: " + e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void removeConcert(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Deleting concert with ID: {0}", id);
            Concert concert = ejb.findConcert(id);
            ejb.removeConcert(concert);
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error deleting concert: " + e.getMessage());
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding concert to delete: " + e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Concert findConcert(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Reading data for concert with ID: {0}", id);
            return ejb.findConcert(id);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error reading concert: " + e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findAllConcerts() {
        try {
            LOGGER.log(Level.INFO, "Reading all concerts");
            return ejb.findAllConcerts();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error reading all concerts: " + e.getMessage());
        }
    }

    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> searchByTerm(@PathParam("searchTerm") String searchTerm) {
        try {
            LOGGER.log(Level.INFO, "Searching concerts by term: {0}", searchTerm);
            return ejb.searchByTerm(searchTerm);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error searching concerts: " + e.getMessage());
        }
    }

    @GET
    @Path("comingSoon")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findComingSoon() {
        try {
            LOGGER.log(Level.INFO, "Finding upcoming concerts");
            return ejb.findComingSoon();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding upcoming concerts: " + e.getMessage());
        }
    }

    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        try {
            LOGGER.log(Level.INFO, "Finding concerts between dates: {0} and {1}", new Object[]{startDate, endDate});
            return ejb.findBetweenDates(startDate, endDate);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding concerts between dates: " + e.getMessage());
        }
    }
}
