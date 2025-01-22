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
 * Entity class representing a client in the Fanetix system. This class extends
 * from FanetixUser and contains client-specific details such as full name,
 * address, and mobile number. It also maintains a one-to-many relationship with
 * the Cart entity, which represents the products in the client's shopping cart.
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

    /**
     * Gets the full name of the client.
     *
     * @return the full name of the client.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the client.
     *
     * @param fullName the full name to set.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the street address of the client.
     *
     * @return the street address of the client.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address of the client.
     *
     * @param street the street address to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the zip code of the client.
     *
     * @return the zip code of the client.
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Sets the zip code of the client.
     *
     * @param zip the zip code to set.
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Gets the city of the client.
     *
     * @return the city of the client.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the client.
     *
     * @param city the city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the mobile number of the client.
     *
     * @return the mobile number of the client.
     */
    public Integer getMobile() {
        return mobile;
    }

    /**
     * Sets the mobile number of the client.
     *
     * @param mobile the mobile number to set.
     */
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets the list of products in the client's cart. This relationship is
     * mapped to the Cart entity.
     *
     * @return the list of products in the cart.
     */
    @XmlTransient
    public List<Cart> getProducts() {
        return products;
    }

    /**
     * Sets the list of products in the client's cart.
     *
     * @param products the list of products to set.
     */
    public void setProducts(List<Cart> products) {
        this.products = products;
    }
}
