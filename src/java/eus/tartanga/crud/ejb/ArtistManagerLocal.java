package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartanga.crud.entities.Artist;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author olaia
 */
@Local
public interface ArtistManagerLocal {

    public void createArtist(Artist artist) throws CreateException;

    public void updateArtist(Artist artist) throws UpdateException;

    public void removeArtist(Artist artist)throws DeleteException;

    public Artist findArtist(Integer artistId)throws ReadException;

    public List<Artist> findAllArtist()throws ReadException;

    public List<Artist> findArtistById(Integer artistId)throws ReadException;

    public List<Artist> ArtistFindBySearchTerm(String searchTerm)throws ReadException;

    public List<Artist> ArtistFindBetweenDates(String startDate, String endDate)throws ReadException;

}
