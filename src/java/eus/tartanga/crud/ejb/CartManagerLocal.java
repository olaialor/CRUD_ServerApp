package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * Local interface for managing cart operations. Provides methods to handle CRUD
 * operations and specific queries related to cart items.
 *
 * @author Meylin
 */
@Local
public interface CartManagerLocal {

    /**
     * Adds a product to the cart.
     *
     * @param cart The Cart entity containing the product and user information.
     * @throws CreateException If an error occurs while adding the product to
     * the cart.
     */
    public void addToCart(Cart cart) throws CreateException;

    /**
     * Updates the details of a cart item.
     *
     * @param cart The Cart entity with updated information.
     * @throws UpdateException If an error occurs while updating the cart item.
     */
    public void updateCart(Cart cart) throws UpdateException;

    /**
     * Removes a product from the cart.
     *
     * @param cart The Cart entity representing the product to be removed.
     * @throws DeleteException If an error occurs while removing the product
     * from the cart.
     */
    public void removeCart(Cart cart) throws DeleteException;

    /**
     * Finds a specific cart item by user email and product ID.
     *
     * @param email The email of the user who owns the cart.
     * @param productId The ID of the product in the cart.
     * @return The Cart entity matching the given email and product ID.
     * @throws ReadException If an error occurs while retrieving the cart
     * products.
     *
     */
    public Cart findCart(String email, Long productId) throws ReadException;

    /**
     * Retrieves all products currently in the cart.
     *
     * @return A list of all Cart entities.
     * @throws ReadException If an error occurs while retrieving the cart
     * products.
     */
    public List<Cart> findAllCartProducts() throws ReadException;

    /**
     * Retrieves all products that have been marked as bought.
     *
     * @return A list of Cart entities representing bought products.
     * @throws ReadException If an error occurs while retrieving the bought
     * products.
     */
    public List<Cart> findAllBoughtProducts() throws ReadException;

    /**
     * Retrieves all products that have not been marked as bought.
     *
     * @return A list of Cart entities representing unbought products.
     * @throws ReadException If an error occurs while retrieving the unbought
     * products.
     */
    public List<Cart> findAllNotBoughtProducts() throws ReadException;

    /**
     * Retrieves all cart items related to a specific artist.
     *
     * @param artistName The name of the artist associated with the cart items.
     * @return A list of Cart entities linked to the specified artist.
     * @throws ReadException If an error occurs while retrieving the cart items
     * by artist.
     */
    public List<Cart> findByArtist(String artistName) throws ReadException;

    /**
     * Retrieves all cart items added between two specific dates.
     *
     * @param startDate The start date of the range (inclusive).
     * @param endDate The end date of the range (inclusive).
     * @return A list of Cart entities within the specified date range.
     * @throws ReadException If an error occurs while retrieving the cart items
     * by date range.
     */
    public List<Cart> findBetweenDate(String startDate, String endDate) throws ReadException;
    
    public Cart findCartByEmail(String email) throws ReadException;
}
