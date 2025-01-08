package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartanga.crud.ejb.ArtistManagerLocal;
import eus.tartanga.crud.entities.Artist;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author olaia
 */
public class EJBArtistManager implements ArtistManagerLocal {

    @PersistenceContext(unitName = "HibernateQueriesPU")
    private EntityManager em;

    @Override
    public void createArtist(Artist artist) {
        try {
            em.persist(artist);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void updateArtist(Artist artist) {
        try {
            em.merge(artist);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void removeArtist(Artist artist) {
        try {
            em.remove(artist);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Artist> findAllArtist() {
        List<Artist> artist;
        try {
            artist = em.createNamedQuery("findAllArtist").getResultList();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return artist;
    }

    @Override
    public List<Artist> findArtistById(Integer artistId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Artist> findArtistByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Artist> findArtistByCompany(String company) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Artist> findArtistByDate(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
