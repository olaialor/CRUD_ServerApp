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
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Elbire
 */
@Path("eus.tartanga.crud.entities.product")
public class ProductFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBProductManager")
    private ProductManagerLocal ejb;
    private Logger LOGGER = Logger.getLogger(ProductFacadeREST.class.getName());

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
     * Método para buscar conciertos según un término de búsqueda.
     *
     * @param searchTerm El término de búsqueda (nombre, ciudad o ubicación).
     * @return Lista de conciertos que coincidan con el término.
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
     * Método para buscar productos cuyo stock sea mayor a 0.
     *
     * @return Lista de conciertos futuros.
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
        try {
            LOGGER.log(Level.INFO, "Reading data for products between dates: {0} and {1}", new Object[]{startDate, endDate});
            return ejb.findProductsBetweenDates(startDate, endDate);
        } catch (ReadException e) {
            LOGGER.severe("Error retrieving products between dates: " + e.getMessage());
            throw new InternalServerErrorException("Error retrieving products between dates: " + e.getMessage());
        }
    }

}
