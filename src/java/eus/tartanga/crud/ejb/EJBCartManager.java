package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.entities.CartId;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless session bean for managing shopping cart operations. This class
 * provides methods to perform CRUD operations on Cart entities.
 *
 * @author Meylin
 */
@Stateless
public class EJBCartManager implements CartManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(EJBCartManager.class.getName());

    /**
     * Adds a new product to the cart.
     *
     * @param cart the Cart entity to add.
     * @throws CreateException if there is an error during the creation process.
     */
    @Override
    public void addToCart(Cart cart) throws CreateException {
        try {
            em.persist(cart);
            LOGGER.log(Level.INFO, "Product added to cart: {0}", cart);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error persisting cart: " + e.getMessage(), e);
            throw new CreateException("Error while adding product to cart: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error while adding product to cart: {0}", e.getMessage());
            throw new CreateException("Unexpected error while adding product to cart: " + e.getMessage());
        }
    }

    /**
     * Adds a new product to the cart.
     *
     * @param cart the Cart entity to add.
     * @throws UpdateException if there is an error during the update process.
     */
    @Override
    public void updateCart(Cart cart) throws UpdateException {
        try {
            if (!em.contains(cart)) {
                em.merge(cart);
            }
            em.flush();
            LOGGER.log(Level.INFO, "Cart updated: {0}", cart);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error updating cart: " + e.getMessage(), e);
            throw new UpdateException("Error while updating the cart: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error while updating the cart: {0}", e.getMessage());
            throw new UpdateException("Unexpected error while updating the cart: " + e.getMessage());
        }
    }

    /**
     * Removes a product from the cart.
     *
     * @param cart the Cart entity to remove.
     * @throws DeleteException if there is an error during the deletion process.
     */
    @Override
    public void removeCart(Cart cart) throws DeleteException {
        try {
            if (!em.contains(cart)) {
                cart = em.merge(cart);
            }
            em.remove(cart);
            em.flush();
            LOGGER.log(Level.INFO, "Cart removed: {0}", cart);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error removing cart: " + e.getMessage(), e);
            throw new DeleteException("Error while removing the cart: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error while removing the cart: {0}", e.getMessage());
            throw new DeleteException("Unexpected error while removing the cart: " + e.getMessage());
        }
    }

    /**
     * Finds a specific cart by its composite key.
     *
     * @param email the email of the user.
     * @param productId the ID of the product.
     * @return the Cart entity if found.
     * @throws ReadException if there is an error during the read process.
     */
    @Override
    public Cart findCart(String email, Integer productId) throws ReadException {
        // Creates the composite ID
        CartId cartId = new CartId(productId, email);
        try {
            // Searches for the Cart entity with its composite primary key
            Cart cart = em.find(Cart.class, cartId);
            LOGGER.log(Level.INFO, "Cart found: {0}", cart);
            return cart;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while finding the cart: " + e.getMessage(), e);
            throw new ReadException("Error while finding the cart: " + e.getMessage());
        }
    }
    @Override
    public Cart findCartByEmail(String email) throws ReadException {
        // Creates the composite ID
        
        try {
            // Searches for the Cart entity with its composite primary key
            Cart cart = em.find(Cart.class, email);
            LOGGER.log(Level.INFO, "Cart found: {0}", cart);
            return cart;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while finding the cart: " + e.getMessage(), e);
            throw new ReadException("Error while finding the cart: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products in the cart.
     *
     * @return a list of all Cart entities.
     * @throws ReadException if there is an error during the read process.
     */
    @Override
    public List<Cart> findAllCartProducts() throws ReadException {
        try {
            List<Cart> carts = em.createNamedQuery("findAllCartProducts", Cart.class).getResultList();
            LOGGER.info("Found all cart products");
            return carts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while reading all cart products: " + e.getMessage(), e);
            throw new ReadException("Error while reading all cart products: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products that have been bought.
     *
     * @return a list of bought Cart entities.
     * @throws ReadException if there is an error during the read process.
     */
    @Override
    public List<Cart> findAllBoughtProducts() throws ReadException {
        try {
            List<Cart> carts = em.createNamedQuery("findAllProductsBought", Cart.class).getResultList();
            LOGGER.info("Found all bought products");
            return carts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while reading all bought products: " + e.getMessage(), e);
            throw new ReadException("Error while reading all bought products: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products that have not been bought.
     *
     * @return a list of not bought Cart entities.
     * @throws ReadException if there is an error during the read process.
     */
    @Override
    public List<Cart> findAllNotBoughtProducts() throws ReadException {
        try {
            List<Cart> carts = em.createNamedQuery("findAllProductsNotBought", Cart.class).getResultList();
            LOGGER.info("Found all not bought products");
            return carts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while reading all not bought products: " + e.getMessage(), e);
            throw new ReadException("Error while reading all not bought products: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products by a specific artist.
     *
     * @param artistName the name of the artist.
     * @return a list of Cart entities matching the artist.
     * @throws ReadException if there is an error during the read process.
     */
    @Override
    public List<Cart> findByArtist(String artistName) throws ReadException {

        try {
            List<Cart> carts = em.createNamedQuery("findAllByArtist", Cart.class)
                    .setParameter("artistName", artistName) // Correctly set the parameter
                    .getResultList();
            LOGGER.log(Level.INFO, "Found products by artist: {0}", artistName);
            return carts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while finding products by artist: " + e.getMessage(), e);
            throw new ReadException("Error while finding products by artist: " + e.getMessage());
        }

    }

    /**
     * Retrieves all products within a specified date range.
     *
     * @param startDate the start date in the format "yyyy-MM-dd".
     * @param endDate the end date in the format "yyyy-MM-dd".
     * @return a list of Cart entities within the date range.
     * @throws ReadException if there is an error during the read process.
     */
    @Override
    public List<Cart> findBetweenDate(String startDate, String endDate) throws ReadException {
        try {
            List<Cart> carts = em.createNamedQuery("CartFindBetweenDates", Cart.class)
                    .setParameter("startDate", java.sql.Date.valueOf(startDate))
                    .setParameter("endDate", java.sql.Date.valueOf(endDate))
                    .getResultList();
            LOGGER.log(Level.INFO, "Found products between dates: {0} and {1}", new Object[]{startDate, endDate});
            return carts;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error while reading products between dates: " + e.getMessage(), e);
            throw new ReadException("Error while reading products between dates: " + e.getMessage());
        }
    }

}
