package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.ProductManagerLocal;
import eus.tartanga.crud.entities.Product;
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
 * RESTful service for managing products in the system. This service provides
 * methods to create, update, delete, retrieve individual or all products,
 * search products by a search term, find products in stock, and retrieve
 * products within a date range.
 *
 * It communicates with the underlying product management service via an
 * injected EJB. The responses are returned as either XML or JSON, depending on
 * the client request.
 *
 * @author Elbire
 */
@Path("eus.tartanga.crud.entities.product")
public class ProductFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBProductManager")
    private ProductManagerLocal ejb;
    private Logger LOGGER = Logger.getLogger(ProductFacadeREST.class.getName());

    /**
     * Creates a new product in the system.
     *
     * @param product The product entity to be created.
     * @throws InternalServerErrorException if an error occurs while creating
     * the product.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Product product) {
        try {
            LOGGER.log(Level.INFO, "Creating product with ID: {0}", product.getProductId());
            ejb.createProduct(product);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error creating product: " + e.getMessage());
        }
    }

    /**
     * Updates an existing product in the system.
     *
     * @param id The ID of the product to be updated.
     * @param product The updated product entity.
     * @throws InternalServerErrorException if an error occurs while updating
     * the product.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Product product) {
        try {
            LOGGER.log(Level.INFO, "Updating product with ID: {0}", id);
            product.setProductId(id);
            ejb.updateProduct(product);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error updating product: " + e.getMessage());
        }
    }

    /**
     * Deletes a product from the system.
     *
     * @param id The ID of the product to be deleted.
     * @throws InternalServerErrorException if an error occurs while deleting
     * the product.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Deleting product with ID: {0}", id);
            Product product = ejb.findProductById(id);
            if (product != null) {
                ejb.deleteProduct(id);
            } else {
                throw new DeleteException("Product with ID " + id + " not found");
            }
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error deleting product: " + e.getMessage());
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error reading product data: {0}", e.getMessage());
            throw new InternalServerErrorException("Error retrieving product: " + e.getMessage());
        }
    }

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to be retrieved.
     * @return The product corresponding to the given ID.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the product.
     */

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product find(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Reading data for product with ID: {0}", id);
            return ejb.findProductById(id);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding product: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products in the system.
     *
     * @return A list of all products.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the products.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findAll() throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Reading data for all products");
            return ejb.findAllProducts();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving all products: " + e.getMessage());
        }
    }

    /**
     * Searches for products by a specific search term (e.g., name, city, or
     * location).
     *
     * @param searchTerm The search term to find matching products.
     * @return A list of products that match the search term.
     * @throws InternalServerErrorException if an error occurs while searching
     * for products.
     */
    @GET
    @Path("search/{searchTerm}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> searchByTerm(@PathParam("searchTerm") String searchTerm) {
        try {
            LOGGER.log(Level.INFO, "Searching for products by term: {0}", searchTerm);
            return ejb.searchProductsByTerm(searchTerm);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error searching products by term: {0}", e.getMessage());
            throw new InternalServerErrorException("Error searching products: " + e.getMessage());
        }
    }

    /**
     * Retrieves products that are currently in stock (i.e., have a stock
     * greater than 0).
     *
     * @return A list of products in stock.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * products in stock.
     */
    @GET
    @Path("stock")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findStock() {
        try {
            LOGGER.log(Level.INFO, "Reading data for products in stock");
            return ejb.findProductsInStock();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving products in stock: {0}", e.getMessage());
            throw new InternalServerErrorException("Error retrieving products in stock: " + e.getMessage());
        }
    }

    /**
     * Retrieves products with a release date between two specified dates.
     *
     * @param startDate The start date (YYYY-MM-DD).
     * @param endDate The end date (YYYY-MM-DD).
     * @return A list of products with release dates between the specified
     * range.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the products between dates.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> findBetweenDates(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        try {
            LOGGER.log(Level.INFO, "Reading data for products between dates: {0} and {1}", new Object[]{startDate, endDate});
            return ejb.findProductsBetweenDates(startDate, endDate);
        } catch (ReadException e) {
            LOGGER.severe("Error retrieving products between dates: " + e.getMessage());
            throw new InternalServerErrorException("Error retrieving products between dates: " + e.getMessage());
        }
    }

}
