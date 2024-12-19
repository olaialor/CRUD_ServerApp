/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Olaia
 */
@Entity
@Table(name = "artist", schema = "Fanetix")
@XmlRootElement
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer artistId;

    private byte[] image;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date debut;

    private String company;

    @NotNull
    private String name;

    private String lastAlbum;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "artist_concert", schema = "Fanetix",
            joinColumns = @JoinColumn(name = "artist_artistId", referencedColumnName = "artistId"),
            inverseJoinColumns = @JoinColumn(name = "concert_concertId", referencedColumnName = "concertId"))
    private List<Concert> concertList;

    @OneToMany
    private Set<Product> getProducts;

    @XmlTransient
    public Set<Product> getGetProducts() {
        return getProducts;
    }

    public void setGetProducts(Set<Product> getProducts) {
        this.getProducts = getProducts;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastAlbum() {
        return lastAlbum;
    }

    public void setLastAlbum(String lastAlbum) {
        this.lastAlbum = lastAlbum;
    }

    @XmlTransient
    public List<Concert> getConcertList() {
        return concertList;
    }

    public void setConcertList(List<Concert> concertList) {
        this.concertList = concertList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artistId != null ? artistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set

        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.artistId == null && other.artistId != null) || (this.artistId != null && !this.artistId.equals(other.artistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.Artist[ id=" + artistId + " ]";
    }

}
