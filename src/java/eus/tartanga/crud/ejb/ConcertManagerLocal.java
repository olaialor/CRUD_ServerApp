/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Concert;
import java.util.List;
import javax.ejb.Local;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;

/**
 *
 * @author Irati
 */
@Local
public interface ConcertManagerLocal {

    public void createConcert(Concert concert) throws CreateException;

    public void updateConcert(Concert concert)throws UpdateException;

    public void removeConcert(Concert concert)throws DeleteException;

    public Concert findConcert(Integer id)throws ReadException;

    public List<Concert> findAllConcerts()throws ReadException;

    public List<Concert> searchByTerm(String searchTerm)throws ReadException;

    public List<Concert> findComingSoon()throws ReadException;

    public List<Concert> findBetweenDates(String startDate, String endDate)throws ReadException;
}
