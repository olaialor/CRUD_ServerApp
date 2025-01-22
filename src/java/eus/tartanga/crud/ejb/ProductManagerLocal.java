/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Product;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Elbire
 */
@Local
public interface ProductManagerLocal {

    public void createProduct(Product product) throws CreateException;

    public void updateProduct(Product product) throws UpdateException;

    public void deleteProduct(Integer productId) throws DeleteException;

    public Product findProductById(Integer productId) throws ReadException;

    public List<Product> findAllProducts() throws ReadException;

    public List<Product> searchProductsByTerm(String searchTerm) throws ReadException;

    public List<Product> findProductsInStock() throws ReadException;

    public List<Product> findProductsBetweenDates(String startDate, String endDate) throws ReadException;
}
