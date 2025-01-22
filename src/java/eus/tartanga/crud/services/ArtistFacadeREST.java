/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 2dam
 */
@Path("eus.tartanga.crud.entities.artist")
public class ArtistFacadeREST{

    @EJB(name="eus.tartanga.crud.ejb.EJBArtistManager")
    private ArtistManagerLocal ejb;
    
    private Logger LOGGER=Logger.getLogger(ArtistFacadeREST.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createArtist(Artist artist) throws CreateException {
        try{
            LOGGER.log(Level.INFO,"Creating Artist{0}",artist.getArtistId());
            ejb.createArtist(artist);
        }catch(CreateException e){
            LOGGER.severe(e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateArtist(Artist artist) throws UpdateException {
        try{
            LOGGER.log(Level.INFO,"Updating Artist {0}",artist.getArtistId());
            ejb.updateArtist(artist);
        }catch(UpdateException e){
            LOGGER.severe(e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void removeArtist(@PathParam("id")Integer id) throws DeleteException, ReadException {
        try{
            LOGGER.log(Level.INFO,"Deleting Artist {0}",id);
            ejb.removeArtist(ejb.findArtist(id));
        }catch(DeleteException e){
            LOGGER.severe(e.getMessage());
            throw new DeleteException(e.getMessage());
        } catch (ReadException ex) {
            Logger.getLogger(ArtistFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


   @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Artist findArtist(@PathParam("id") Integer id) throws ReadException {
        try{
            LOGGER.log(Level.INFO,"Reading data for artist {0}",id);
            return ejb.findArtist(id);
        }catch(ReadException e){
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

   @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Artist> findAllArtist() throws ReadException {
       
        try {
            LOGGER.log(Level.INFO,"Reading data for all artist {0}");
            return ejb.findAllArtist();
        } catch (ReadException ex) {
            Logger.getLogger(ArtistFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ejb.findAllArtist();

    }
    
     /**
     * Método para buscar artistas según un término de búsqueda.
     * @param searchTerm El término de búsqueda (nombre o compañía.).
     * @return Lista de artistas que coincidan con el término.
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> searchByTerm(@PathParam("searchTerm") String searchTerm) throws ReadException {
          try{
            return ejb.ArtistFindBySearchTerm(searchTerm);
        }catch(ReadException e){
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
    
     /**
     * Método para buscar debut de artistas entre dos fechas específicas.
     * @param startDate Fecha de inicio (YYYY-MM-DD).
     * @param endDate Fecha de fin (YYYY-MM-DD).
     * @return Lista de artistas entre las fechas dadas.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> ArtistFindBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) throws ReadException {
          try{
            return ejb.ArtistFindBetweenDates(startDate,endDate);
        }catch(ReadException e){
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

}
