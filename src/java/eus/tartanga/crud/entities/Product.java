package eus.tartanga.crud.entities;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Represents a product in the Fanetix system. This class stores details of a
 * product, including its title, description, artist, release date, price,
 * stock, and image. Products are associated with a cart through the `Cart`
 * entity.
 *
 * It also includes named queries for fetching all products, finding products by
 * search term, checking available stock, and fetching products released within
 * a date range.
 *
 * @author Elbire
 */
@NamedQueries({
    @NamedQuery(
            name = "findAllProducts",
            query = "SELECT p FROM Product p"
    )
    ,
        @NamedQuery(
            name = "ProductStock",
            query = "SELECT p FROM Product p WHERE p.stock >=1"
    )
    ,
        @NamedQuery(
            name = "ProductFindBySearchTerm",
            query = "SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.artist.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
    )
    ,
        @NamedQuery(
            name = "ProductFindBetweenDates",
            query = "SELECT p FROM Product p WHERE p.releaseDate BETWEEN :startDate AND :endDate ORDER BY p.releaseDate ASC"
    )

})
@Entity
@Table(name = "Product", schema = "Fanetix")
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    @OneToMany(cascade = ALL, mappedBy = "product")
    private List<Cart> client;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    @ManyToOne
    private Artist artist;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @NotNull
    private float price;
    @NotNull
    private int stock;
    private byte[] image;

    /**
     * Gets the product ID.
     *
     * @return the product ID.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId the product ID to set.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Gets the list of clients associated with the product.
     *
     * @return the list of clients.
     */
    @XmlTransient
    public List<Cart> getClient() {
        return client;
    }

    /**
     * Sets the list of clients associated with the product.
     *
     * @param client the list of clients to set.
     */
    public void setClient(List<Cart> client) {
        this.client = client;
    }

    /**
     * Gets the title of the product.
     *
     * @return the title of the product.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the product.
     *
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the product.
     *
     * @return the description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the artist associated with the product.
     *
     * @return the artist of the product.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Sets the artist associated with the product.
     *
     * @param artist the artist to set.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Gets the release date of the product.
     *
     * @return the release date of the product.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the product.
     *
     * @param releaseDate the release date to set.
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gets the stock quantity of the product.
     *
     * @return the stock quantity.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the product.
     *
     * @param stock the stock quantity to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the image of the product.
     *
     * @return the image as a byte array.
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the image of the product.
     *
     * @param image the image to set as a byte array.
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Computes the hash code for the Product based on the product ID.
     *
     * @return the hash code for the Product.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    /**
     * Checks whether two Product objects are equal based on their product ID.
     *
     * @param object the object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productId fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the Product, including its product ID.
     *
     * @return a string representation of the Product.
     */
    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.Product[ id=" + productId + " ]";
    }

}
