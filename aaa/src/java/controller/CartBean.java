package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Item;

/**
 *
 * @author Mis Dess
 */
@Stateful
public class CartBean {

    @PersistenceContext(unitName = "aaaPU")
    private EntityManager em;

    private static List<Item> cartContent = new ArrayList<>();
    private String genomeName;
    private static float totalPayment = 0;

    /**
     *
     * @param genomeName
     * @param amount
     * @return
     * add a genome to the shopping cart
     */
    public String addGenome(String genomeName, int amount) {
        String result=null;
        Item item = em.find(Item.class, genomeName);
        Item item1 = null;
        if (amount <= 0 || amount > item.getNumberOfItems()) {
            result = genomeName + "  not added to shopping cart. Amount should be between 1 and " + item.getNumberOfItems();
        }
        else{
            item1 = new Item(genomeName, amount, item.getItemPrice(), item.getItemDescription());
            if(cartContent.contains(item1)){
             result = genomeName +" is on cart. Remove from cart and add it again";
            }
            else{   
            cartContent.add(item1);
            totalPayment += item.getItemPrice() * amount;
            item.setNumberOfItems(item.getNumberOfItems() - amount);
            result = genomeName + " added to shopping cart";
            }
        }  
        return result;
    }

    /**
     *
     * @param genomeName1
     * Remove the genome from the shopping cart
     */
    public void removeGenome(String genomeName1) {
      Item item =null;// em.find(Item.class, genomeName1);
        int a = 0,b;
        for (Item cartContent1 : cartContent) {
            if (cartContent1.getName().equals(genomeName1)) {
                item=cartContent1;
                a = cartContent1.getNumberOfItems();
            }
        }
        for (Item result : ItemAddBean.results) {
            if (result.getName().equals(genomeName1)) {
                b = result.getNumberOfItems();
                result.setNumberOfItems(a+b);
                System.out.println("cart empty");
                break;
            }
        }
        cartContent.remove(item);
        totalPayment-=item.getNumberOfItems()*item.getItemPrice();
    }

    /**
     *
     * @return
     * make payment. this method only nullifies the shopping cart and is assumed as the payment is made
     */
    public String toPayment() {
        totalPayment = 0;
        cartContent.stream().forEach((item) -> {
            Item item1 = em.find(Item.class, item.getName());
            /*em.merge(item1);
            em.remove(item1);
            em.persist(item1);*/

        });

        cartContent.clear();
        return "success";
    }

    /**
     *
     * @return
     * This method the genomes in the webshop
     */
    public List<Item> listGenome() {
        return cartContent;
    }

//getters and setters

    /**
     *
     * @param cartContent
     */
        public void setCartContent(List<Item> cartContent) {
        CartBean.cartContent = cartContent;
    }

    /**
     *
     * @return
     */
    public List<Item> getCartContent() {
        return cartContent;
    }

    /**
     *
     * @return
     */
    public String getGenomeName() {
        return genomeName;
    }

    /**
     *
     * @param genomeName
     */
    public void setGenomeName(String genomeName) {
        this.genomeName = genomeName;
    }

    /**
     *
     * @return
     */
    public float getTotalPayment() {
        return totalPayment;
    }

    /**
     *
     * @param totalPayment
     */
    public void setTotalPayment(float totalPayment) {
        CartBean.totalPayment = totalPayment;
    }
}
