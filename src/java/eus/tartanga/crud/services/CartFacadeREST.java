package eus.tartanga.crud.services;

import eus.tartanga.crud.ejb.CartManagerLocal;
import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.entities.CartId;
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
 * REST facade for managing shopping cart data. This class provides endpoints
 * for adding, updating, removing, and retrieving cart items for users. It also
 * includes methods for searching for cart items by artist and filtering based
 * on purchase status and date range.
 * <p>
 * Each operation is logged for tracing and debugging purposes, and appropriate
 * exceptions are thrown in case of errors during the cart management process.
 * </p>
 *
 * @author Meylin
 */
@Path("eus.tartanga.crud.entities.cart")
public class CartFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBCartManager")
    private CartManagerLocal ejb;
    private Logger LOGGER = Logger.getLogger(CartFacadeREST.class.getName());

    /**
     * Adds a new cart item to the user's cart.
     *
     * @param cart The cart item to be added.
     * @throws InternalServerErrorException if an error occurs while creating
     * the cart.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void addToCart(Cart cart) {
        try {
            LOGGER.log(Level.INFO, "Creating cart with ID: {0}", cart.getId());
            ejb.addToCart(cart);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error creating cart: " + e.getMessage());
        }
    }

    /**
     * Updates an existing cart item in the user's cart.
     *
     * @param email The email of the user whose cart is being updated.
     * @param productId The ID of the product being updated in the cart.
     * @param cart The updated cart item.
     * @throws InternalServerErrorException if an error occurs while updating
     * the cart.
     */
    @PUT
    @Path("{email}/{productId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCart(@PathParam("email") String email, @PathParam("productId") Long productId, Cart cart) {
        try {
            CartId cartId = new CartId(productId, email);
            LOGGER.log(Level.INFO, "Updating cart with ID: {0}", cartId);
            cart.setId(cartId);
            ejb.updateCart(cart);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error updating cart: " + e.getMessage());
        }
    }

    /**
     * Removes a cart item from the user's cart.
     *
     * @param email The email of the user whose cart item is being removed.
     * @param productId The ID of the product being removed from the cart.
     * @throws InternalServerErrorException if an error occurs while removing
     * the cart item.
     */
    @DELETE
    @Path("{email}/{productId}")
    public void removeCart(@PathParam("email") String email, @PathParam("productId") Long productId) {
        CartId cartId = new CartId(productId, email);
        try {
            LOGGER.log(Level.INFO, "Deleting cart with ID: {0}", cartId);
            ejb.removeCart(ejb.findCart(email, productId));
        } catch (DeleteException | ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error deleting cart: " + e.getMessage());
        }
    }

    /**
     * Retrieves a specific cart item based on the user's email and product ID.
     *
     * @param email The email of the user whose cart item is being retrieved.
     * @param productId The ID of the product in the cart.
     * @return The cart item.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the cart item.
     */
    @GET
    @Path("{email}/{productId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cart findCart(@PathParam("email") String email, @PathParam("productId") Long productId) {
        try {
            LOGGER.log(Level.INFO, "Finding cart with email: {0} and productId: {1}", new Object[]{email, productId});
            return ejb.findCart(email, productId);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding cart: " + e.getMessage());
        }
    }
    @GET
    @Path("byEmail/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cart findCartByEmail(@PathParam("email") String email) {
        try {
            LOGGER.log(Level.INFO, "Finding cart with email: {0}", new Object[]{email});
            return ejb.findCartByEmail(email);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding cart: " + e.getMessage());
        }
    }

    /**
     * Retrieves all cart products for all users.
     *
     * @return A list of all cart products.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the cart products.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAllCartProducts() {
        try {
            LOGGER.log(Level.INFO, "Finding all cart products");
            return ejb.findAllCartProducts();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving all cart products: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products that have been bought.
     *
     * @return A list of all bought products in the cart.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the bought products.
     */
    @GET
    @Path("bought")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAllBoughtProducts() {
        try {
            LOGGER.log(Level.INFO, "Finding all bought products");
            return ejb.findAllBoughtProducts();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving bought products: " + e.getMessage());
        }
    }

    /**
     * Retrieves all products that have not been bought.
     *
     * @return A list of all not bought products in the cart.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the not bought products.
     */
    @GET
    @Path("notbought")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findAllNotBoughtProducts() {
        try {
            LOGGER.log(Level.INFO, "Finding all not bought products");
            return ejb.findAllNotBoughtProducts();
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving not bought products: " + e.getMessage());
        }
    }

    /**
     * Retrieves all cart items for a specific artist.
     *
     * @param artistName The name of the artist whose products are being
     * retrieved.
     * @return A list of cart items for the specified artist.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the artist's products.
     */
    @GET
    @Path("byArtist/{artistName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findByArtist(@PathParam("artistName") String artistName) {
        try {
            LOGGER.log(Level.INFO, "Finding all products by artist: {0}", artistName);
            return ejb.findByArtist(artistName);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving products by artist: " + e.getMessage());
        }
    }

    /**
     * Retrieves all cart items that were added between two specified dates.
     *
     * @param startDate The start date in "YYYY-MM-DD" format.
     * @param endDate The end date in "YYYY-MM-DD" format.
     * @return A list of cart items between the specified dates.
     * @throws InternalServerErrorException if an error occurs while retrieving
     * the cart items.
     */
    @GET
    @Path("betweenDates/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cart> findBetweenDate(@PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate) {
        try {
            LOGGER.log(Level.INFO, "Finding products between dates: {0} and {1}", new Object[]{startDate, endDate});
            return ejb.findBetweenDate(startDate, endDate);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error retrieving products between dates: " + e.getMessage());
        }
    }
}
