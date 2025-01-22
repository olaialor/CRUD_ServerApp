package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Product;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * Local interface for managing products. Provides methods to handle CRUD
 * operations and product searches.
 *
 * @author Elbire
 */
@Local
public interface ProductManagerLocal {

    /**
     * Creates a new product entry.
     *
     * @param product The Product entity to be created.
     * @throws CreateException If an error occurs while creating the product.
     */
    public void createProduct(Product product) throws CreateException;

    /**
     * Updates the details of an existing product.
     *
     * @param product The Product entity with updated information.
     * @throws UpdateException If an error occurs while updating the product.
     */
    public void updateProduct(Product product) throws UpdateException;

    /**
     * Deletes a product entry by its ID.
     *
     * @param productId The ID of the product to be deleted.
     * @throws DeleteException If an error occurs while deleting the product.
     */
    public void deleteProduct(Integer productId) throws DeleteException;

    /**
     * Finds a specific product by its ID.
     *
     * @param productId The ID of the product to be retrieved.
     * @return The Product entity matching the given ID.
     * @throws ReadException If an error occurs while finding the product.
     */
    public Product findProductById(Integer productId) throws ReadException;

    /**
     * Retrieves all products.
     *
     * @return A list of all Product entities.
     * @throws ReadException If an error occurs while retrieving the products.
     */
    public List<Product> findAllProducts() throws ReadException;

    /**
     * Searches for products by a search term.
     *
     * @param searchTerm The term used to search for products.
     * @return A list of Product entities matching the search term.
     * @throws ReadException If an error occurs during the search.
     */
    public List<Product> searchProductsByTerm(String searchTerm) throws ReadException;

    /**
     * Finds all products that are currently in stock.
     *
     * @return A list of Product entities that are in stock.
     * @throws ReadException If an error occurs while retrieving the products.
     */
    public List<Product> findProductsInStock() throws ReadException;

    /**
     * Finds products added between specific dates.
     *
     * @param startDate The start date for the search.
     * @param endDate The end date for the search.
     * @return A list of Product entities added between the specified dates.
     * @throws ReadException If an error occurs during the search.
     */
    public List<Product> findProductsBetweenDates(String startDate, String endDate) throws ReadException;
}
