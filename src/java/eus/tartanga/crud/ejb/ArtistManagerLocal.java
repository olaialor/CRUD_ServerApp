package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import eus.tartanga.crud.entities.Artist;
import java.util.Date;
import java.util.List;

/**
 *
 * @author olaia
 */
public interface ArtistManagerLocal {
     public void createArtist(Artist artist);
     public void updateArtist(Artist artist);
     public void removeArtist(Artist artist);
     public List<Artist> findAllArtist();
     public List<Artist> findArtistById(Integer artistId);
     public List<Artist> findArtistByName(String name);
     public List<Artist> findArtistByCompany(String company);
     public List<Artist> findArtistByDate(Date startDate, Date endDate);
      

}
