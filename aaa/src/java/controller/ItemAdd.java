package controller;

import java.util.List;
import javax.ejb.Local;
import model.Item;

@Local
public interface ItemAdd {

    public void addItem(String itemName, int numberOfItems, float itemPrice, String desc);

    public void editInventory(String itemName, int numberOfItems, float itemPrice, String desc);

    public void removeItem(String itemName);
    public void getGenomeDetails(String genomeName );
     public Item getGenomeDetail(String genomeName );
     public Item genomeDetails();

    public List<Item> listItems();

}
