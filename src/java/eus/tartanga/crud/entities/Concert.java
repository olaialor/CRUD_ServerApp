package eus.tartanga.crud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing a concert in the system.
 * <p>
 * The Concert class is used to model the details of a concert, including its
 * name, location, date, time, and the list of artists performing at the
 * concert. This entity is mapped to the "concert" table in the database.
 * </p>
 *
 * <p>
 * The class supports named queries for fetching concerts, including upcoming
 * concerts, concerts by a search term, and concerts within a specific date
 * range.
 * </p>
 *
 * @author Irati
 */
@NamedQueries({
    @NamedQuery(
            name = "ConcertComingSoon",
            query = "SELECT c FROM Concert c WHERE c.concertDate >= CURRENT_DATE ORDER BY c.concertDate ASC"
    )
    ,
    
    @NamedQuery(
            name = "ConcertFindBySearchTerm",
            query = "SELECT c FROM Concert c JOIN c.artistList a WHERE "
            + "LOWER(c.concertName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
            + "OR LOWER(c.city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
            + "OR LOWER(c.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
            + "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
    )

    ,
        
        @NamedQuery(
            name = "ConcertFindBetweenDates",
            query = "SELECT c FROM Concert c WHERE c.concertDate BETWEEN :startDate AND :endDate ORDER BY c.concertDate ASC"
    )
    ,
        
        @NamedQuery(name = "findAllConcerts", query = "SELECT c FROM Concert c"
    )
})
@Entity
@Table(name = "concert", schema = "Fanetix")
@XmlRootElement
public class Concert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer concertId;
    @Lob
    private byte[] billboard;

    @NotNull(message = "concertName.required")
    private String concertName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (
            name = "artist_concert", schema = "Fanetix",
            joinColumns = @JoinColumn(name = "concert_concertId", referencedColumnName = "concertId"),
            inverseJoinColumns = @JoinColumn(name = "artist_artistId", referencedColumnName = "artistId")
    )
    private List<Artist> artistList;

    private String location;

    private String city;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date concertDate;

    @Temporal(TemporalType.TIME)
    private Date concertTime;

    /**
     * Gets the unique identifier for the concert.
     *
     * @return the concert ID
     */
    public Integer getConcertId() {
        return concertId;
    }

    /**
     * Sets the unique identifier for the concert.
     *
     * @param concertId the concert ID to set
     */
    public void setConcertId(Integer concertId) {
        this.concertId = concertId;
    }

    /**
     * Gets the billboard image for the concert.
     *
     * @return the billboard image
     */
    public byte[] getBillboard() {
        return billboard;
    }

    /**
     * Sets the billboard image for the concert.
     *
     * @param billboard the billboard image to set
     */
    public void setBillboard(byte[] billboard) {
        this.billboard = billboard;
    }

    /**
     * Gets the name of the concert.
     *
     * @return the concert name
     */
    public String getConcertName() {
        return concertName;
    }

    /**
     * Sets the name of the concert.
     *
     * @param concertName the concert name to set
     */
    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    /**
     * Gets the list of artists performing at the concert.
     *
     * @return the list of artists
     */
    public List<Artist> getArtistList() {
        return artistList;
    }

    /**
     * Sets the list of artists performing at the concert.
     *
     * @param artistList the list of artists to set
     */
    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    /**
     * Gets the location of the concert.
     *
     * @return the concert location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the concert.
     *
     * @param location the concert location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the city where the concert is taking place.
     *
     * @return the concert city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the concert is taking place.
     *
     * @param city the concert city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the date of the concert.
     *
     * @return the concert date
     */
    public Date getConcertDate() {
        return concertDate;
    }

    /**
     * Sets the date of the concert.
     *
     * @param concertDate the concert date to set
     */
    public void setConcertDate(Date concertDate) {
        this.concertDate = concertDate;
    }

    /**
     * Gets the time of the concert.
     *
     * @return the concert time
     */
    public Date getConcertTime() {
        return concertTime;
    }

    /**
     * Sets the time of the concert.
     *
     * @param concertTime the concert time to set
     */
    public void setConcertTime(Date concertTime) {
        this.concertTime = concertTime;
    }

    /**
     * Computes the hash code for this Concert object.
     * <p>
     * The hash code is generated based on the concert ID.
     * </p>
     *
     * @return the hash code for this Concert
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (concertId != null ? concertId.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this Concert object with another object for equality.
     * <p>
     * This comparison is based on the concert ID. If the concert ID of both
     * Concert objects is the same, they are considered equal.
     * </p>
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Concert)) {
            return false;
        }
        Concert other = (Concert) object;
        if ((this.concertId == null && other.concertId != null) || (this.concertId != null && !this.concertId.equals(other.concertId))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this Concert object.
     * <p>
     * The string representation includes the concert ID.
     * </p>
     *
     * @return a string representation of this Concert
     */
    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.Concert[ id=" + concertId + " ]";
    }
}
