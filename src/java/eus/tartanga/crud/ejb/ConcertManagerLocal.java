/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.Concert;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Irati
 */
@Local
public interface ConcertManagerLocal {

    public void createConcert(Concert concert);

    public void updateConcert(Concert concert);

    public void removeConcert(Concert concert);

    public Concert findConcert(Integer id);

    public List<Concert> findAllConcerts();

    public List<Concert> searchByTerm(String searchTerm);

    public List<Concert> findComingSoon();

    public List<Concert> findBetweenDates(String startDate, String endDate);
}
