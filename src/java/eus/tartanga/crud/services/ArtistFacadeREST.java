/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.entities.Artist;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Stateless
@Path("eus.tartanga.crud.entities.artist")
public class ArtistFacadeREST extends AbstractFacade<Artist> {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    public ArtistFacadeREST() {
        super(Artist.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Artist entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Artist entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Artist find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
     /**
     * Método para buscar artistas según un término de búsqueda.
     * @param searchTerm El término de búsqueda (nombre o compañía.).
     * @return Lista de artistas que coincidan con el término.
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artist> searchByTerm(@PathParam("searchTerm") String searchTerm) {
        return em.createNamedQuery("ArtistFindBySearchTerm", Artist.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
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
    public List<Artist> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        return em.createNamedQuery("ArtistFindBetweenDates", Artist.class)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .setParameter("endDate", java.sql.Date.valueOf(endDate))
                .getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
