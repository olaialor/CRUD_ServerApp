/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Product;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Elbire
 */
@Local
public interface ProductManagerLocal {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Integer productId);
    Product findProductById(Integer productId);
    List<Product> findAllProducts();
    List<Product> searchProductsByTerm(String searchTerm);
    List<Product> findProductsInStock();
    List<Product> findProductsBetweenDates(String startDate, String endDate);
}
