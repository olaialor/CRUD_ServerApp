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
 *
 * @author Meylin
 */
@Path("eus.tartanga.crud.entities.cart")
public class CartFacadeREST {

    @EJB(name = "eus.tartanga.crud.ejb.EJBCartManager")
    private CartManagerLocal ejb;
    private Logger LOGGER = Logger.getLogger(CartFacadeREST.class.getName());

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

    @PUT
    @Path("{email}/{productId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCart(@PathParam("email") String email, @PathParam("productId") Integer productId, Cart cart) {
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

    @DELETE
    @Path("{email}/{productId}")
    public void removeCart(@PathParam("email") String email, @PathParam("productId") Integer productId) {
        try {
            CartId cartId = new CartId(productId, email);
            LOGGER.log(Level.INFO, "Deleting cart with ID: {0}", cartId);
            Cart cart = new Cart();
            cart.setId(cartId);
            ejb.removeCart(cart);
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error deleting cart: " + e.getMessage());
        }
    }

    @GET
    @Path("{email}/{productId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cart findCart(@PathParam("email") String email, @PathParam("productId") Integer productId) {
        try {
            LOGGER.log(Level.INFO, "Finding cart with email: {0} and productId: {1}", new Object[]{email, productId});
            return ejb.findCart(email, productId);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException("Error finding cart: " + e.getMessage());
        }
    }

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
