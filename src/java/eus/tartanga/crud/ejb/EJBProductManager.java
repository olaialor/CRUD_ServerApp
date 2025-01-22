package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Product;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 * Stateless EJB responsible for managing CRUD operations for {@link Product} entities.
 * This class implements the {@link ProductManagerLocal} interface and provides 
 * basic functionality to create, update, delete, and retrieve product entities.
 * 
 * <p>It uses the {@link EntityManager} to interact with the database and log 
 * important actions using the {@link Logger}.</p>
 *
 * @author Elbire
 */
@Stateless
public class EJBProductManager implements ProductManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(EJBProductManager.class.getName());

    /**
     * Creates a new {@link Product} entity in the database.
     * 
     * @param product The {@link Product} entity to be created.
     * @throws CreateException If an error occurs during the creation of the product.
     */
    @Override
    public void createProduct(Product product) throws CreateException {
        try {
            em.persist(product);
            LOGGER.log(Level.INFO, "Product created successfully: {0}", product);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error creating product: {0}", product + e.getMessage());
            throw new CreateException("Error creating product: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error creating product: {0}", product + e.getMessage());
            throw new CreateException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Updates an existing {@link Product} entity in the database.
     * 
     * @param product The {@link Product} entity to be updated.
     * @throws UpdateException If an error occurs during the update of the product.
     */
    @Override
    public void updateProduct(Product product) throws UpdateException {
        try {
            em.merge(product);
            LOGGER.log(Level.INFO, "Product updated successfully: {0}", product);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error updating product: {0}", product + e.getMessage());
            throw new UpdateException("Error updating product: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error updating product: {0}", product + e.getMessage());
            throw new UpdateException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Deletes a {@link Product} entity from the database by its ID.
     * 
     * @param productId The ID of the {@link Product} entity to be deleted.
     * @throws DeleteException If an error occurs during the deletion of the product.
     */
    @Override
    public void deleteProduct(Integer productId) throws DeleteException {
        try {
            Product product = em.find(Product.class, productId);
            if (product != null) {
                em.remove(product);
                LOGGER.log(Level.INFO, "Product deleted successfully: {0}", product);
            } else {
                LOGGER.log(Level.WARNING, "Product not found for deletion with ID: {0}", productId);
                throw new DeleteException("Product not found with ID: " + productId);
            }
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error deleting product with ID: {0}", productId + e.getMessage());
            throw new DeleteException("Error deleting product: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error deleting product with ID: {0}", productId + e.getMessage());
            throw new DeleteException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Finds a {@link Product} entity by its ID.
     * 
     * @param productId The ID of the {@link Product} entity to be retrieved.
     * @return The {@link Product} entity, or {@code null} if no product is found.
     * @throws ReadException If an error occurs while retrieving the product.
     */
    @Override
    public Product findProductById(Integer productId) throws ReadException {
        try {
            Product product = em.find(Product.class, productId);
            if (product != null) {
                LOGGER.log(Level.INFO, "Product found by ID: {0}", productId);
            } else {
                LOGGER.log(Level.WARNING, "No product found with ID: {0}", productId);
            }
            return product;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error finding product by ID: {0}", productId + e.getMessage());
            throw new ReadException("Error finding product by ID: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error finding product by ID: {0}", productId + e.getMessage());
            throw new ReadException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all {@link Product} entities from the database.
     * 
     * @return A list of all {@link Product} entities.
     * @throws ReadException If an error occurs while retrieving the products.
     */
    @Override
    public List<Product> findAllProducts() throws ReadException {
        try {
            List<Product> products = em.createNamedQuery("findAllProducts", Product.class).getResultList();
            LOGGER.log(Level.INFO, "All products retrieved successfully, count: {0}", products.size());
            return products;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all products", e);
            throw new ReadException("Error retrieving all products: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error retrieving all products", e);
            throw new ReadException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Searches for {@link Product} entities based on a search term.
     * 
     * @param searchTerm The term used to search for products.
     * @return A list of {@link Product} entities that match the search term.
     * @throws ReadException If an error occurs while searching for products.
     */
    @Override
    public List<Product> searchProductsByTerm(String searchTerm) throws ReadException {
        try {
            List<Product> products = em.createNamedQuery("ProductFindBySearchTerm", Product.class)
                    .setParameter("searchTerm", searchTerm)
                    .getResultList();
            LOGGER.log(Level.INFO, "Products found by search term '{0}', count: {1}", new Object[]{searchTerm, products.size()});
            return products;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error searching products by term: {0}", searchTerm + e.getMessage());
            throw new ReadException("Error searching products by term: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error searching products by term: {0}", searchTerm + e.getMessage());
            throw new ReadException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all {@link Product} entities that are currently in stock.
     * 
     * @return A list of {@link Product} entities in stock.
     * @throws ReadException If an error occurs while retrieving the products in stock.
     */
    @Override
    public List<Product> findProductsInStock() throws ReadException {
        try {
            List<Product> products = em.createNamedQuery("ProductStock", Product.class).getResultList();
            LOGGER.log(Level.INFO, "Products in stock retrieved successfully, count: {0}", products.size());
            return products;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving products in stock", e);
            throw new ReadException("Error retrieving products in stock: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error retrieving products in stock", e);
            throw new ReadException("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Finds {@link Product} entities created between two specified dates.
     * 
     * @param startDate The start date for the search.
     * @param endDate The end date for the search.
     * @return A list of {@link Product} entities created between the specified dates.
     * @throws ReadException If an error occurs while retrieving the products.
     */
    @Override
    public List<Product> findProductsBetweenDates(String startDate, String endDate) throws ReadException {
        try {
            List<Product> products = em.createNamedQuery("ProductFindBetweenDates", Product.class)
                    .setParameter("startDate", java.sql.Date.valueOf(startDate))
                    .setParameter("endDate", java.sql.Date.valueOf(endDate))
                    .getResultList();
            LOGGER.log(Level.INFO, "Products found between dates {0} and {1}, count: {2}",
                    new Object[]{startDate, endDate, products.size()});
            return products;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving products between dates {0} and {1}", new Object[]{startDate, endDate, e});
            throw new ReadException("Error retrieving products between dates: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error retrieving products between dates {0} and {1}", new Object[]{startDate, endDate, e});
            throw new ReadException("Unexpected error: " + e.getMessage());
        }
    }
}
