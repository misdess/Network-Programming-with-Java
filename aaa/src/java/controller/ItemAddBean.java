package controller;

import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Item;

/**
 *
 * @author Mis Dess
 */
@Stateful
public class ItemAddBean implements ItemAdd {

    @PersistenceContext(unitName = "aaaPU")
    EntityManager em;
    Item item;

    static List<Item> results;
private String detailedItem;

    /**
     *
     * @param itemName
     * @param numberOfItems
     * @param itemPrice
     * @param desc
     * Add a genome to the webshop
     */
    @Override
    public void addItem(String itemName, int numberOfItems, float itemPrice, String desc) {
        item = new Item(itemName, numberOfItems, itemPrice, desc);
        em.persist(item);
    }

    /**
     *
     * @param itemName
     * @param numberOfItems
     * @param price
     * @param description
     * Edit the genomes in the webshop, possibly price, quantity and description
     */
    @Override
    public void editInventory(String itemName, int numberOfItems, float price, String description) {
        item = em.find(Item.class, itemName);
        if (item != null) {
            System.out.println(item.getName()+" "+item.getItemPrice()+" "+item.getNumberOfItems()+" "+item.getItemDescription());
           // em.merge(item);
            //em.remove(item);
            results.remove(item);

            numberOfItems =12;
            if (description == null) {
                description = item.getItemDescription();
            }
            if (price <= 0) {
                price = item.getItemPrice();
            }

            Query query = em.createQuery(
      "UPDATE Item  i SET i.numberOfItems =23  WHERE i.name=:p")
        .setParameter("p", itemName);
            int a=query.executeUpdate();
            item = new Item(itemName, numberOfItems, price, description);
          // persist(item);
            results.add(item);
        }
    }

    /**
     *
     * @param itemName
     * Remove a genome from the webshop
     */
    @Override
    public void removeItem(String itemName) {
        
        item = em.find(Item.class, itemName);
        em.remove(item);
        results.remove(item);
    }

    /**
     *
     * @return
     * List the genomes in the webshop
     */
    @Override
    public List<Item> listItems() {
        TypedQuery<Item> query = em.createQuery("SELECT c FROM Item c", Item.class);
        results = query.getResultList();
        return results;
    }
    
    /**
     *
     * @param genomeName
     * @return
     * Return the genome in the webshop of the given genomeName
     */
    @Override
    public Item getGenomeDetail(String genomeName ){
   item=em.find(Item.class, genomeName);
            return item;
    }
    
    @Override
     public void getGenomeDetails(String genomeName ){
   item=em.find(Item.class, genomeName);
    }
     
     
    /**
     *
     * @return
     */
    @Override
    public Item genomeDetails(){
    
    return item;}
    
    /**
     *
     * @param genomeName
     * @return
     */
    public Item getItem(String genomeName) {
        item = em.find(Item.class, genomeName);
        return item;
    }

    /**
     *
     * @return
     */
    public List<Item> getResults() {
        return results;
    }
    void persist(Item item){
    em.persist(item);} 
    
    /**
     *
     * @return
     */
    public String getDetailedItem() {
        return detailedItem;
    }

    /**
     *
     * @param detailedItem
     */
    public void setDetailedItem(String detailedItem) {
        this.detailedItem = detailedItem;
    }
}
 