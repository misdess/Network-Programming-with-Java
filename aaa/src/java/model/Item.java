package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Mis Dess
 */
@Entity
@Table(name = "Item")
public class Item implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String name;
    @Column(name="Quantity")
    private int numberOfItems;
    private float itemPrice;
    @Column(length=100)
    private String itemDescription;

    /**
     *
     */
    public Item() {
    }

    /**
     *
     * @param name
     * @param number
     * @param price
     * @param desc
     */
    public Item(String name, int number, float price, String desc) {
        this.numberOfItems = number;
        this.name = name;
        this.itemPrice = price;
        this.itemDescription=desc;
    }
    
    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     *
     * @param numberOfItems
     */
    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     *
     * @return
     */
    public float getItemPrice() {
        return itemPrice;
    }

    /**
     *
     * @param itemPrice
     */
    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     *
     * @return
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     *
     * @param itemDescription
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  name;
    }

}
