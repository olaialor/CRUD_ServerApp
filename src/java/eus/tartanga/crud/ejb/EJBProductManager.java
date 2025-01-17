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
    public void createProduct(Product product) throws CreateException {
        try {
            em.persist(product);
        } catch (Exception e) {
             throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateProduct(Product product) throws UpdateException{
        try {
            em.merge(product);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Integer productId) throws DeleteException{
        try {
            em.remove(productId);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
   public Product findProductById(Integer productId) throws ReadException {
        Product product;
        try{
            product=em.find(Product.class, productId);
        }catch(Exception e){
             throw new ReadException(e.getMessage());
        }
        return product;
    }

    @Override
  public List<Product> findAllProducts() throws ReadException {
        List<Product> products;
           try{
        products=em.createNamedQuery("findAllProducts").getResultList();
        }catch(Exception e){
             throw new ReadException(e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> searchProductsByTerm(String searchTerm) throws ReadException{
        try {
            return em.createNamedQuery("ProductFindBySearchTerm", Product.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
   public List<Product> findProductsInStock() throws ReadException {
        try {
            return em.createNamedQuery("ProductStock", Product.class)
                .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List<Product> findProductsBetweenDates(String startDate, String endDate) throws ReadException{
        try {
            return em.createNamedQuery("ProductFindBetweenDates", Product.class)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .setParameter("endDate", java.sql.Date.valueOf(endDate))
                .getResultList();
        } catch (Exception e) {
           throw new ReadException(e.getMessage());
        }
    }

}
