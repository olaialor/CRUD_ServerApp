package eus.tartanga.crud.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Embeddable class representing the composite primary key for the Cart entity.
 * <p>
 * The CartId class is used to uniquely identify a cart entry by combining the
 * product ID and the client's email address as a composite key. This class is
 * embedded in the Cart entity and is responsible for creating a unique
 * identifier for each cart entry.
 * </p>
 *
 * @author Meylin
 */
@Embeddable
public class CartId implements Serializable {

    private Integer productId;
    private String email;

    /**
     * Default constructor for the CartId class.
     */
    public CartId() {

    }

    /**
     * Constructor for the CartId class with the specified product ID and email.
     *
     * @param productId the product ID to set
     * @param email the email address of the client to set
     */
    public CartId(Integer productId, String email) {
        this.productId = productId;
        this.email = email;
    }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId the product ID to set
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Gets the email address of the client.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the client.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Computes the hash code for this CartId object.
     * <p>
     * The hash code is generated based on the product ID.
     * </p>
     *
     * @return the hash code for this CartId
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this CartId object with another object for equality.
     * <p>
     * This comparison is based on the product ID. If the product ID of both
     * CartId objects is the same, they are considered equal.
     * </p>
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CartId)) {
            return false;
        }
        CartId other = (CartId) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this CartId object.
     * <p>
     * The string representation includes the product ID.
     * </p>
     *
     * @return a string representation of this CartId
     */
    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.CartId[ id=" + productId + " ]";
    }
}
