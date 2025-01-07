/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.entities.Concert;
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
@Path("eus.tartanga.crud.entities.concert")
public class ConcertFacadeREST extends AbstractFacade<Concert> {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    public ConcertFacadeREST() {
        super(Concert.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Concert entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Concert entity) {
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
    public Concert find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    /**
     * Método para buscar conciertos según un término de búsqueda.
     * @param searchTerm El término de búsqueda (nombre, ciudad o ubicación).
     * @return Lista de conciertos que coincidan con el término.
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> searchByTerm(@PathParam("searchTerm") String searchTerm) {
        return em.createNamedQuery("ConcertFindBySearchTerm", Concert.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
    }

    /**
     * Método para buscar conciertos cuya fecha sea igual o posterior a hoy.
     * @return Lista de conciertos futuros.
     */
    @GET
    @Path("comingSoon")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findComingSoon() {
        return em.createNamedQuery("ConcertComingSoon", Concert.class)
                .getResultList();
    }

    /**
     * Método para buscar conciertos entre dos fechas específicas.
     * @param startDate Fecha de inicio (YYYY-MM-DD).
     * @param endDate Fecha de fin (YYYY-MM-DD).
     * @return Lista de conciertos entre las fechas dadas.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Concert> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        return em.createNamedQuery("ConcertFindBetweenDates", Concert.class)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .setParameter("endDate", java.sql.Date.valueOf(endDate))
                .getResultList();
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
