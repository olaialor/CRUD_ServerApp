package eus.tartanga.crud.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class representing an Artist in the system. This class is mapped to
 * the `artist` table in the database and contains information about the
 * artist's debut, name, company, last album, associated concerts, and products.
 *
 * <p>
 * This class includes the following named queries:</p>
 * <ul>
 * <li><b>ArtistFindBySearchTerm</b>: Finds artists based on a search term in
 * their name or company.</li>
 * <li><b>ArtistFindBetweenDates</b>: Finds artists based on their debut date
 * between a start and end date.</li>
 * <li><b>findAllArtist</b>: Retrieves all artists.</li>
 * </ul>
 *
 * <p>
 * It also includes a many-to-many relationship with the Concert entity, and a
 * one-to-many relationship with the Product entity.</p>
 *
 * @author Olaia
 */
@NamedQueries({
    @NamedQuery(
            name = "ArtistFindBySearchTerm",
            query = "SELECT a FROM Artist a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(a.company) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
    )
    ,
    @NamedQuery(
            name = "ArtistFindBetweenDates",
            query = "SELECT a FROM Artist a WHERE a.debut BETWEEN :startDate AND :endDate ORDER BY a.debut ASC"
    )
    ,
    @NamedQuery(
            name = "findAllArtist",
            query = "SELECT a FROM Artist a"
    )
})
@Entity
@Table(name = "artist", schema = "Fanetix")
@XmlRootElement
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the artist.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer artistId;

    /**
     * The image of the artist, stored as a byte array.
     */
    @Lob
    private byte[] image;

    /**
     * The debut date of the artist.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date debut;

    /**
     * The name of the company that manages the artist.
     */
    private String company;

    /**
     * The name of the artist.
     */
    @NotNull
    private String name;

    /**
     * The last album released by the artist.
     */
    private String lastAlbum;

    /**
     * A list of concerts associated with the artist.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "artist_concert", schema = "Fanetix",
            joinColumns = @JoinColumn(name = "artist_artistId", referencedColumnName = "artistId"),
            inverseJoinColumns = @JoinColumn(name = "concert_concertId", referencedColumnName = "concertId"))
    private List<Concert> concertList;

    /**
     * A set of products associated with the artist.
     */
    @OneToMany
    private Set<Product> getProducts;

    /**
     * Returns the set of products associated with the artist.
     *
     * @return the set of products associated with the artist
     */
    @XmlTransient
    public Set<Product> getGetProducts() {
        return getProducts;
    }

    /**
     * Sets the set of products associated with the artist.
     *
     * @param getProducts the set of products to set
     */
    public void setGetProducts(Set<Product> getProducts) {
        this.getProducts = getProducts;
    }

    /**
     * Returns the unique identifier of the artist.
     *
     * @return the artistId
     */
    public Integer getArtistId() {
        return artistId;
    }

    /**
     * Sets the unique identifier of the artist.
     *
     * @param artistId the artistId to set
     */
    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    /**
     * Returns the image of the artist.
     *
     * @return the image of the artist
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the image of the artist.
     *
     * @param image the image to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Returns the debut date of the artist.
     *
     * @return the debut date
     */
    public Date getDebut() {
        return debut;
    }

    /**
     * Sets the debut date of the artist.
     *
     * @param debut the debut date to set
     */
    public void setDebut(Date debut) {
        this.debut = debut;
    }

    /**
     * Returns the name of the company that manages the artist.
     *
     * @return the company name
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the name of the company that manages the artist.
     *
     * @param company the company name to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Returns the name of the artist.
     *
     * @return the artist's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the artist.
     *
     * @param name the artist's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the last album released by the artist.
     *
     * @return the last album
     */
    public String getLastAlbum() {
        return lastAlbum;
    }

    /**
     * Sets the last album released by the artist.
     *
     * @param lastAlbum the last album to set
     */
    public void setLastAlbum(String lastAlbum) {
        this.lastAlbum = lastAlbum;
    }

    /**
     * Returns the list of concerts associated with the artist.
     *
     * @return the concert list
     */
    @XmlTransient
    public List<Concert> getConcertList() {
        return concertList;
    }

    /**
     * Sets the list of concerts associated with the artist.
     *
     * @param concertList the concert list to set
     */
    public void setConcertList(List<Concert> concertList) {
        this.concertList = concertList;
    }

    /**
     * Returns the hash code for the artist.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artistId != null ? artistId.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this artist to another object.
     *
     * @param object the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        return this.artistId != null && this.artistId.equals(other.artistId);
    }

    /**
     * Returns a string representation of the artist.
     *
     * @return a string representation of the artist
     */
    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.Artist[ id=" + artistId + " ]";
    }
}
