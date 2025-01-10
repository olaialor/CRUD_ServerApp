/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.EJBProductManager;
import eus.tartanga.crud.ejb.ProductManagerLocal;
import eus.tartanga.crud.entities.Product;
import java.util.List;
import javax.ejb.EJB;
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
 * @author Elbire
 */
@Path("eus.tartanga.crud.entities.product")
public class ProductFacadeREST{

    @EJB(name = "eus.tartanga.crud.ejb.EJBProductManager")
    private ProductManagerLocal ejb;


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Product entity) {
        ejb.createProduct(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Product entity) {
        ejb.updateProduct(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        ejb.deleteProduct(id);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product find(@PathParam("id") Integer id) {
        return ejb.findProductById(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll() {
        return ejb.findAllProducts();
    }

    /**@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }**/

    /**
     * Método para buscar conciertos según un término de búsqueda.
     *
     * @param searchTerm El término de búsqueda (nombre, ciudad o ubicación).
     * @return Lista de conciertos que coincidan con el término.
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> searchByTerm(@PathParam("searchTerm") String searchTerm) {
        return ejb.searchProductsByTerm(searchTerm);
    }

    /**
     * Método para buscar productos cuyo stock sea mayor a 0.
     *
     * @return Lista de conciertos futuros.
     */
    @GET
    @Path("stock")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findStock() {
        return ejb.findProductsInStock();
    }

    /**
     * Método para buscar productos que tengan una release date entre dos fechas
     * específicas.
     *
     * @param startDate Fecha de inicio (YYYY-MM-DD).
     * @param endDate Fecha de fin (YYYY-MM-DD).
     * @return Lista de productos con realease date entre las fechas dadas.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        return ejb.findProductsBetweenDates(startDate, endDate);
    }

}
