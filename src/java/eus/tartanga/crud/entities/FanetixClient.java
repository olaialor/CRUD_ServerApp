/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.entities;

import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Meylin and Irati
 */
@NamedQueries({
    @NamedQuery(
            name = "findAllClients",
            query = "SELECT c FROM FanetixClient c"
    )
    ,
    @NamedQuery(
            name = "clientSignIn",
            query = "SELECT c FROM FanetixClient c WHERE c.email = :email AND c.passwd = :passwd"
    )
})

@Entity
@Table(name = "fanetix_client", schema = "Fanetix")
@XmlRootElement
public class FanetixClient extends FanetixUser {

    private static final long serialVersionUID = 1L;
    private String fullName;
    private String street;
    private Integer zip;
    private String city;
    private Integer mobile;
    @OneToMany(cascade = ALL, mappedBy = "client")
    private List<Cart> products;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    @XmlTransient
    public List<Cart> getProducts() {
        return products;
    }

    public void setProducts(List<Cart> products) {
        this.products = products;
    }

}
