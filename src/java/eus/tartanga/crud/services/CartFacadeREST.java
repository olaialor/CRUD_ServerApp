/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.entities.CartId;
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
 * @author Meylin
 */
@Stateless
@Path("eus.tartanga.crud.entities.cart")
public class CartFacadeREST extends AbstractFacade<Cart> {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    public CartFacadeREST() {
        super(Cart.class);
    }

    //Crear pasing the object
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cart entity) {
        super.create(entity);
    }

    //Update pasing the object
    @PUT
    @Path("{email}/{productId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("email") String email, @PathParam("productId") Integer id, Cart entity) {
        super.edit(entity);
    }

    //Delete by id
    @DELETE
    @Path("{email}/{productId}")
    public void remove(@PathParam("email") String email, @PathParam("productId") Integer id) {
        CartId idCart = new CartId(id, email);
        super.remove(super.find(idCart));
    }

    //Search by id
    @GET
    @Path("{email}/{productId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cart find(@PathParam("email") String email, @PathParam("productId") Integer id) {
        CartId idCart = new CartId(id, email);
        return super.find(idCart);
    }

    //Get all the products
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAll() {
        return super.findAll();
    }

    //Get all products that have been bought
    @GET
    @Path("bought")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAllProductsBought() {
        return getEntityManager()
                .createNamedQuery("findAllProductsBought", Cart.class)
                .getResultList();
    }

    @GET
    @Path("notbought")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAllProductsNotBought() {
        return getEntityManager()
                .createNamedQuery("findAllProductsNotBought", Cart.class)
                .getResultList();
    }

    @GET
    @Path("byArtist/{artistName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAllByArtist(@PathParam("artistName") String artistName) {
        return getEntityManager()
                .createNamedQuery("findAllByArtist", Cart.class)
                .setParameter("artistName", artistName)
                .getResultList();
    }

    /**
     * Método para buscar conciertos entre dos fechas específicas.
     *
     * @param startDate Fecha de inicio (YYYY-MM-DD).
     * @param endDate Fecha de fin (YYYY-MM-DD).
     * @return Lista de conciertos entre las fechas dadas.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        return em.createNamedQuery("CartFindBetweenDates", Cart.class)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .setParameter("endDate", java.sql.Date.valueOf(endDate))
                .getResultList();
    }

    //Count the products that are in the cart
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
