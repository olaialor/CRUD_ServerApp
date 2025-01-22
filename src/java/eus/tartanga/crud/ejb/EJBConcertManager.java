/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Concert;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Irati
 */
@Stateless
public class EJBConcertManager implements ConcertManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    @Override
    public void createConcert(Concert concert) throws CreateException {
        try {
            em.persist(concert);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateConcert(Concert concert) throws UpdateException {
        try {
            em.merge(concert);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removeConcert(Concert concert) throws DeleteException {
        try {
            em.remove(concert);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public Concert findConcert(Integer id) throws ReadException {
        Concert concert = null;
        try {
            concert = em.find(Concert.class, id);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return concert;
    }

    @Override
    public List<Concert> findAllConcerts() throws ReadException {
        List<Concert> concerts;
        try {

            concerts = em.createNamedQuery("findAllConcerts").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return concerts;
    }

    @Override
    public List<Concert> searchByTerm(String searchTerm) throws ReadException {

        return em.createNamedQuery("ConcertFindBySearchTerm", Concert.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
    }

    @Override
    public List<Concert> findComingSoon() throws ReadException {
        try {
            return em.createNamedQuery("ConcertComingSoon", Concert.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List<Concert> findBetweenDates(String startDate, String endDate) throws ReadException { //Revisar si puede ser string o tiene que ser localDate
        try {
            return em.createNamedQuery("ConcertFindBetweenDates", Concert.class)
                    .setParameter("startDate", java.sql.Date.valueOf(startDate))
                    .setParameter("endDate", java.sql.Date.valueOf(endDate))
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }
}
