
package controller;

import java.util.List;
import javax.ejb.Remote;
import model.Item;

/**
 *
 * @author Mis Dess
 */
@Remote
public interface Cart {

    /**
     *
     */
    void addGenome();

    /**
     *
     * @return
     */
    List<Item> getCartContent();

    /**
     *
     * @return
     */
    String getGenomeName();

    /**
     *
     * @return
     */
    float getTotalPayment();

    /**
     *
     * @return
     */
    List<Item> listGenome();

    /**
     *
     */
    void removeGenome();

    /**
     *
     * @param cartContent
     */
    void setCartContent(List<Item> cartContent);

    /**
     *
     * @param genomeName
     */
    void setGenomeName(String genomeName);

    /**
     *
     * @param totalPayment
     */
    void setTotalPayment(float totalPayment);

    /**
     *
     * @return
     */
    String toPayment();
    
}
