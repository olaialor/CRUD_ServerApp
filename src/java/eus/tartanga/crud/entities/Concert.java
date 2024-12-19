/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Irati
 */
@Entity
@Table(name = "concert", schema = "Fanetix")
@XmlRootElement
public class Concert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer concertId;

    private byte[] billboard;

    @NotNull(message = "concertName.required")
    private String concertName;

    @ManyToMany(mappedBy = "concertList", fetch = FetchType.EAGER)
    private List<Artist> artistsList;

    private String location;

    private String city;

    @Temporal(TemporalType.DATE)
    private Date concertDate;

    @Temporal(TemporalType.TIME)
    private Date concertTime;

    public Integer getConcertId() {
        return concertId;
    }

    public void setConcertId(Integer concertId) {
        this.concertId = concertId;
    }

    public byte[] getBillboard() {
        return billboard;
    }

    public void setBillboard(byte[] billboard) {
        this.billboard = billboard;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    @XmlTransient
    public List<Artist> getArtistsList() {
        return artistsList;
    }

    public void setArtistsList(List<Artist> artistsList) {
        this.artistsList = artistsList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(Date concertDate) {
        this.concertDate = concertDate;
    }

    public Date getConcertTime() {
        return concertTime;
    }

    public void setConcertTime(Date concertTime) {
        this.concertTime = concertTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (concertId != null ? concertId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concert)) {
            return false;
        }
        Concert other = (Concert) object;
        if ((this.concertId == null && other.concertId != null) || (this.concertId != null && !this.concertId.equals(other.concertId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.Concert[ id=" + concertId + " ]";
    }
}
