/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.ejb.CartManagerLocal;
import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Meylin
 */
@Stateless
public class EJBCartManager implements CartManagerLocal {

    @PersistenceContext(unitName = "HibernateQueriesPU")
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cart> findBetweenDate(Date initialDate, Date finalDate) throws ReadException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
