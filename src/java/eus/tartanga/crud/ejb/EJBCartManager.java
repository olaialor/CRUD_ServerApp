/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.entities.CartId;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Meylin
 */
@Stateless
public class EJBCartManager implements CartManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    /**
     *
     * @param cart
     * @throws CreateException
     */
    @Override
    public void addToCart(Cart cart) throws CreateException {
        try {
            em.persist(cart);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateCart(Cart cart) throws UpdateException {
        try {
            if (!em.contains(cart)) {
                em.merge(cart);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removeCart(Cart cart) throws DeleteException {
        try {
            em.remove(em.merge(cart));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public Cart findCart(String email, Integer productId) {

        // Crea el ID compuesto
        CartId cartId = new CartId(productId, email);

        // Busca la entidad Cart con su clave primaria compuesta
        Cart cart = em.find(Cart.class, cartId);

        return cart;
    }

    @Override
    public List<Cart> findAllCartProducts() throws ReadException {
        List<Cart> carts;
        try {
            carts = em.createNamedQuery("findAllCartProducts").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return carts;
    }

    @Override
    public List<Cart> findAllBoughtProducts() throws ReadException {
        List<Cart> carts;
        try {
            carts = em.createNamedQuery("findAllProductsBought").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return carts;
    }

    @Override
    public List<Cart> findAllNotBoughtProducts() throws ReadException {
        List<Cart> carts;
        try {
            carts = em.createNamedQuery("findAllProductsNotBought").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return carts;
    }

    @Override
    public List<Cart> findByArtist(String artistName) throws ReadException {

        return em.createNamedQuery("findAllByArtist", Cart.class)
                .setParameter("artistName", artistName) // Configuración correcta del parámetro
                .getResultList();

    }

    @Override
    public List<Cart> findBetweenDate(String startDate, String endDate) throws ReadException {
        List<Cart> carts;
        try {
            carts = em.createNamedQuery("CartFindBetweenDates").
                    setParameter("startDate", java.sql.Date.valueOf(startDate)).
                    setParameter("endDate", java.sql.Date.valueOf(endDate)).
                    getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return carts;
    }

}


