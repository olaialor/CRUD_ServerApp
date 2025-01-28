package eus.tartanga.crud.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * Representa la clave primaria compuesta para la entidad Cart.
 */
@Embeddable
public class CartId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer productId; // ID del producto
    private String email;   // Email del cliente

    /**
     * Constructor vacío requerido por JPA.
     */
    public CartId() {
    }

    /**
     * Constructor completo.
     *
     * @param productId el ID del producto
     * @param email el email del cliente
     */
    public CartId(Integer productId, String email) {
        this.productId = productId;
        this.email = email;
    }

    // Getters y setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Implementación de equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return Objects.equals(productId, cartId.productId) &&
               Objects.equals(email, cartId.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, email);
    }

    @Override
    public String toString() {
        return "CartId{" +
                "productId=" + productId +
                ", email='" + email + '\'' +
                '}';
    }
}
