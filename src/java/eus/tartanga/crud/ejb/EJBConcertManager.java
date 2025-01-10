/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Concert;
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
    public void createConcert(Concert concert) {
        try {
            em.persist(concert);
        } catch (Exception e) {
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void updateConcert(Concert concert) {
        try {
            em.merge(concert);
        } catch (Exception e) {
            //throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void removeConcert(Concert concert) {
        try {
            em.remove(concert);
        } catch (Exception e) {
            // throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Concert findConcert(Integer id) {
        Concert concert = null;
        try {
            concert = em.find(Concert.class, id);
        } catch (Exception e) {
            //throw new InternalServerErrorException(e.getMessage());
        }
        return concert;
    }

    @Override
    public List<Concert> findAllConcerts() {
        List<Concert> concerts;
        concerts = em.createNamedQuery("findAllConcerts").getResultList();
        return concerts;
    }

    @Override
    public List<Concert> searchByTerm(String searchTerm) {
        return em.createNamedQuery("ConcertFindBySearchTerm", Concert.class)
                .setParameter("searchTerm", searchTerm)
                .getResultList();
    }

    @Override
    public List<Concert> findComingSoon() {
        return em.createNamedQuery("ConcertComingSoon", Concert.class)
                .getResultList();
    }

    @Override
    public List<Concert> findBetweenDates(String startDate, String endDate) { //Revisar si puede ser string o tiene que ser localDate
        return em.createNamedQuery("ConcertFindBetweenDates", Concert.class)
                .setParameter("startDate", java.sql.Date.valueOf(startDate))
                .setParameter("endDate", java.sql.Date.valueOf(endDate))
                .getResultList();
    }
}
