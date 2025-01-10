/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Product;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Elbire
 */
@Stateless
public class EJBProductManager implements ProductManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    @Override
    public void createProduct(Product product) {
        try {
            em.persist(product);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            em.merge(product);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        try {
            em.remove(productId);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public Product findProductById(Integer productId) {
        Product product;
        try{
            product=em.find(Product.class, productId);
        }catch(Exception e){
            throw new UnsupportedOperationException("Not supported yet.");
        }
        return product;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products;
        products=em.createNamedQuery("findAllProducts").getResultList();
        return products;
    }

    @Override
    public List<Product> searchProductsByTerm(String searchTerm) {
        try {
            return em.createNamedQuery("ProductFindBySearchTerm", Product.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public List<Product> findProductsInStock() {
        try {
            return em.createNamedQuery("ProductStock", Product.class)
                .getResultList();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public List<Product> findProductsBetweenDates(String startDate, String endDate) {
        try {
            return em.createNamedQuery("ProductFindBetweenDates", Product.class)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .setParameter("endDate", java.sql.Date.valueOf(endDate))
                .getResultList();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
