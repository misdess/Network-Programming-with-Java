package view;

import controller.ItemAdd;
import controller.ItemAddBean;
import model.Item;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;

/**
 *
 * @author Mis Dess
 */
@ConversationScoped
@ManagedBean(name = "itemAddManagedBean")

public class ItemAddManagedBean implements Serializable {

    private static final long serialVersionUID = 16247164405L;

    @EJB
    ItemAdd itemaddbean;
    private List<Item> results;

    private String itemName;
    private int numberOfItems;
    private float itemPrice;
    private String itemDescription;

    private String message;

    private String operation;
    private String addItem;
    private String EditInventory;
    private String removeItem;
    private String listItem;
    private String deactivateCustomers;
    private String listCustomers;

    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    /**
     *Add genome to the shoping cart 
     */
    public void addItem() {
        if (itemDescription.length() > 100) {
            itemDescription = itemDescription.substring(0, 100);
        }
        itemaddbean.addItem( itemName,numberOfItems, itemPrice, itemDescription);
    }
    /**
     * Edit a genome in the webshop
     */
    public void editInventory() {
        if (itemDescription.length() > 100) {
            itemDescription = itemDescription.substring(0, 100);
        }
        if(numberOfItems==0 && itemPrice==0 && itemDescription==null)
            message="Your update criteria is zero";
        else
            itemaddbean.editInventory( itemName, numberOfItems, itemPrice, itemDescription);
    }
    
    /**
     *
     * @return
     * Get the genome details
     */
    public String genomeDetail(){
    
     itemaddbean.getGenomeDetail(itemName);
     return "success";
    }
    
    /**
     *Remove a genome from the webshop
     */
    public void removeItem() {
        itemaddbean.removeItem(itemName);
    }

    /**
     *this method finds out what the admin has selects as next operation
     */
    public void findOperation() {
        switch (operation) {
            case "aItem":
                addItem = "requested";
                System.out.println("control is setting operation type: " + addItem);
                break;
            case "eItem":
                EditInventory = "requested";
                System.out.println("control is setting operation type: " + addItem);
                break;
            case "rItem":
                removeItem = "requested";
                break;
            case "lItem":
                listItem = "requested";
                results = itemaddbean.listItems();
                break;
            case "dCustomer":
                deactivateCustomers = "requested";
                break;
            case "lCustomer":
                listCustomers = "requested";
                break;
        }
    }

    /**
     *returns the Items in the webshop
     * @return
     */
    public List<Item> getResults() {
        return results;
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
    public String getItemName() {
        return itemName;
    }

    /**
     *
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }
    
    /**
     *
     * @return
     */
    public ItemAdd getItemaddbean() {
        return itemaddbean;
    }

    /**
     *
     * @param itemaddbean
     */
    public void setItemaddbean(ItemAddBean itemaddbean) {
        this.itemaddbean = itemaddbean;
    }

    /**
     *
     * @return
     */
    public String getOperation() {
        return operation;
    }

    /**
     *
     * @param operation
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     *
     * @return
     */
    public String getAddItem() {
        return addItem;
    }

    /**
     *
     * @param addItem
     */
    public void setAddItem(String addItem) {
        this.addItem = addItem;
    }

    /**
     *
     * @return
     */
    public String getEditInventory() {
        return EditInventory;
    }

    /**
     *
     * @param EditInventory
     */
    public void setEditInventory(String EditInventory) {
        this.EditInventory = EditInventory;
    }

    /**
     *
     * @return
     */
    public String getRemoveItem() {
        return removeItem;
    }

    /**
     *
     * @param removeItem
     */
    public void setRemoveItem(String removeItem) {
        this.removeItem = removeItem;
    }

    /**
     *
     * @return
     */
    public String getListItem() {
        return listItem;
    }

    /**
     *
     * @param listItem
     */
    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    /**
     *
     * @return
     */
    public String getDeactivateCustomer() {
        return deactivateCustomers;
    }

    /**
     *
     * @param deactivateCustomer
     */
    public void setDeactivateCustomer(String deactivateCustomer) {
        this.deactivateCustomers = deactivateCustomer;
    }

    /**
     *
     * @return
     */
    public String getListCustomer() {
        return listCustomers;
    }

    /**
     *
     * @param listCustomer
     */
    public void setListCustomer(String listCustomer) {
        this.listCustomers = listCustomer;
    }

}
