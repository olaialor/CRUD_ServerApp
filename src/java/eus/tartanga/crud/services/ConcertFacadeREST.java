/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.ConcertManagerLocal;
import eus.tartanga.crud.ejb.EJBConcertManager;
import eus.tartanga.crud.entities.Concert;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Irati
 */
@Path("eus.tartanga.crud.entities.concert")
public class ConcertFacadeREST {

    @EJB(name="eus.tartanga.crud.ejb.EJBConcertManager")
    private ConcertManagerLocal ejb;

    private Logger LOGGER = Logger.getLogger(ConcertFacadeREST.class.getName());

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createConcert(Concert concert) {
        try {
            LOGGER.log(Level.INFO, "Creating Concert{0}", concert.getConcertId());
            ejb.createConcert(concert);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateConcert(@PathParam("id") Integer id, Concert concert) {
        try {
            LOGGER.log(Level.INFO, "Updating Concert {0}", concert.getConcertId());
            ejb.updateConcert(concert);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void removeConcert(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Deleting Concert {0}", id);
            ejb.removeConcert(ejb.findConcert(id));
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Concert findConcert(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Reading data for concert {0}", id);
            return ejb.findConcert(id);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
        return ejb.findConcert(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findAllConcerts() {
        LOGGER.log(Level.INFO, "Reading data for all concerts {0}");
        return ejb.findAllConcerts();

    }

    /**
     *
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> searchByTerm(@PathParam("searchTerm") String searchTerm) {
        try {
            return ejb.searchByTerm(searchTerm);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
        return ejb.searchByTerm(searchTerm);
    }

    /**
     */
    @GET
    @Path("comingSoon")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findComingSoon() {
        try {
            return ejb.findComingSoon();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
        return ejb.findComingSoon();
    }

    /**
     *
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        try {
            return ejb.findBetweenDates(startDate, endDate);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // throw new InternalServerErrorException(e.getMessage());
        }
        return ejb.findBetweenDates(startDate, endDate);
    }
}
