/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Cart;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Meylin
 */
@Local
public interface CartManagerLocal {

    public void addToCart(Cart cart) throws CreateException;

    public void updateCart(Cart cart) throws UpdateException;

    public void removeCart(Cart cart)throws DeleteException;
    
    public Cart findCart(String email, Integer productId);

    public List<Cart> findAllCartProducts()throws ReadException;

    public List<Cart> findAllBoughtProducts() throws ReadException;

    public List<Cart> findAllNotBoughtProducts()throws ReadException;

    public List<Cart> findByArtist(String artistName)throws ReadException;

    public List<Cart> findBetweenDate(String startDate, String endDate)throws ReadException;
}
