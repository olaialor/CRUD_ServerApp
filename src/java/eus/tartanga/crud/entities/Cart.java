package eus.tartanga.crud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class representing a Cart in the system.
 * <p>
 * This entity is responsible for storing the relationship between a product and
 * a client, along with additional details such as the quantity of the product
 * in the cart, the purchase status, and the date of the order.
 * <p>
 * The Cart entity is mapped to the "cart" table in the "Fanetix" schema and
 * includes various named queries for retrieving cart data based on certain
 * conditions.
 * <p>
 * Named Queries:
 * <ul>
 * <li>"findAllCartProducts" - Returns all cart records.</li>
 * <li>"findAllProductsBought" - Returns all cart records where the product has
 * been bought.</li>
 * <li>"findAllProductsNotBought" - Returns all cart records where the product
 * has not been bought.</li>
 * <li>"findAllByArtist" - Returns all cart records for a specific artist's
 * products.</li>
 * <li>"CartFindBetweenDates" - Returns all cart records where the order date is
 * between the specified start and end dates.</li>
 * </ul>
 *
 * @author Meylin
 */
@NamedQueries({
    @NamedQuery(name = "findAllCartProducts", query = "SELECT c FROM Cart c")
    ,    
    @NamedQuery(name = "findAllProductsBought", query = "SELECT c FROM Cart c WHERE c.bought = TRUE")
    ,
    @NamedQuery(name = "findAllProductsNotBought", query = "SELECT c FROM Cart c WHERE c.bought = FALSE")
    ,
    @NamedQuery(name = "findAllByArtist", query = "SELECT c FROM Cart c WHERE c.product.artist.name = :artistName")
    ,
    @NamedQuery(name = "CartFindBetweenDates", query = "SELECT c FROM Cart c WHERE c.orderDate BETWEEN :startDate AND :endDate ORDER BY c.orderDate ASC")
})

@Entity
@Table(name = "cart", schema = "Fanetix")
@XmlRootElement
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private CartId id;
    //@MapsId("productId")
    @ManyToOne
    @JoinColumn(name="productId",updatable=false,insertable=false)
    private Product product;
    //@MapsId("email")
    @ManyToOne
    @JoinColumn(name="email",updatable=false,insertable=false)
    private FanetixClient client;
    private Integer quantity;
    private Boolean bought;
    @Temporal(TemporalType.DATE)
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date orderDate;

    /**
     * Gets the unique identifier for the cart entry.
     *
     * @return the cart identifier
     */
    public CartId getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the cart entry.
     *
     * @param id the cart identifier to set
     */
    public void setId(CartId id) {
        this.id = id;
    }

    /**
     * Gets the product associated with the cart entry.
     *
     * @return the product in the cart
     */
    @XmlTransient
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with the cart entry.
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the client associated with the cart entry.
     *
     * @return the client in the cart
     */
    @XmlTransient
    public FanetixClient getClient() {
        return client;
    }

    /**
     * Sets the client associated with the cart entry.
     *
     * @param client the client to set
     */
    public void setClient(FanetixClient client) {
        this.client = client;
    }

    /**
     * Gets the quantity of the product in the cart.
     *
     * @return the quantity of the product
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the cart.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the purchase status of the product in the cart.
     *
     * @return true if the product has been bought, false otherwise
     */
    public Boolean getBought() {
        return bought;
    }

    /**
     * Sets the purchase status of the product in the cart.
     *
     * @param bought the purchase status to set
     */
    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    /**
     * Gets the date of the order.
     *
     * @return the order date
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date of the order.
     *
     * @param orderDate the order date to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Computes the hash code for the cart entry.
     *
     * @return the hash code for this cart entry
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this cart entry with another object for equality.
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the cart entry.
     *
     * @return a string representation of the cart entry
     */
    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.Cart[ id=" + id + " ]";
    }

}
