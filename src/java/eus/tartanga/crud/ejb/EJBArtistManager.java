package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartanga.crud.ejb.ArtistManagerLocal;
import eus.tartanga.crud.entities.Artist;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author olaia
 */
@Stateless
public class EJBArtistManager implements ArtistManagerLocal {

    @PersistenceContext(unitName = "CRUDWeb_AplicationPU")
    private EntityManager em;

    @Override
    public void createArtist(Artist artist) throws CreateException {
        try {
            em.persist(artist);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateArtist(Artist artist)throws UpdateException {
        try {
            em.merge(artist);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removeArtist(Artist artist) throws DeleteException{
        try {
            em.remove(artist);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public Artist findArtist(Integer artistId) throws ReadException{
        Artist artist = null;
        try {
            artist = em.find(Artist.class, artistId);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return artist;
    }

    @Override
    public List<Artist> findAllArtist()throws ReadException {
        List<Artist> artist;
        try {
            artist = em.createNamedQuery("findAllArtist").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return artist;
    }

    @Override
    public List<Artist> findArtistById(Integer artistId)throws ReadException {
        List<Artist> artists;
        try {
            artists = em.createNamedQuery("findArtistById")
                    .setParameter("id", artistId)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return artists;
    }

    @Override
    public List<Artist> ArtistFindBySearchTerm(String searchTerm) throws ReadException{
        return em.createNamedQuery("ArtistFindBySearchTerm", Artist.class).setParameter("searchTerm", searchTerm)
                .getResultList();
    }

    @Override
    public List<Artist> ArtistFindBetweenDates(String startDate, String endDate)throws ReadException {
        List<Artist> artists;
        try {
            artists = em.createNamedQuery("ArtistFindBetweenDates")
                    .setParameter("startDate", java.sql.Date.valueOf(startDate))
                    .setParameter("endDate", java.sql.Date.valueOf(endDate))
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return artists;
    }

}
