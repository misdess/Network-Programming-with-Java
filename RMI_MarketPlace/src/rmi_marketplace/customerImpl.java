package rmi_marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi_marketplace.Bank.*;


public class customerImpl extends UnicastRemoteObject implements customer {

    private String customerName;
    private List<item> itemList = new ArrayList<>();
    private List<item> itemWished = new ArrayList<>();

    public customerImpl(String name) throws RemoteException {
        super();
        customerName = name;
    }

    public List<item> getItems() {
        return itemList;
    }

    @Override
    public synchronized boolean buyItem(String itemName, float itemPrice) throws RemoteException, RejectedException {
        float price = -1;
        String owner = null;
        item itemBuy = null;
        Map<String, customerImpl> allCustomers = marketPlaceImpl.members;//find all customers of the marketplace
        String[] str = allCustomers.keySet().toArray(new String[1]);//find the key(in this case name) of the customers

        customerImpl me = null;
        boolean itemFound = false;
        for (int i = 0; i < str.length; i++) {//check if the requested item is in the marketplace
            me = allCustomers.get(str[i]);
            Iterator<item> listIterator = me.itemList.iterator();

            while (listIterator.hasNext()) {//check if a customer provides an item for sell
                item itemwish = listIterator.next();
                if (itemwish.getItemName().equals(itemName) && itemwish.getItemPrice() <= itemPrice) {
                    itemBuy = itemwish;
                    price = itemBuy.getItemPrice();
                    owner = itemBuy.getItemOwner();
                    itemFound = true;
                    break;
                }
            }
            if (itemFound) {
                break;
            }
        }
        if (itemBuy == null) {
            System.out.println(itemName + " not found!");
            return false;
        }
        Client client1 = new Client();//access the bank account of the buyer
        Account account = client1.bankobj.getAccount(customerName);

        if (account == null) {
            System.out.println(customerName + " has no bank account!");
            return false;
        }
        float bankBalance = account.getBalance();//buyer balance in his bank account

        if (bankBalance < price) {
           for (callBack m : marketPlaceImpl.allClients) {
            if (m.getName().equals(owner)) {
                m.lowBalance(customerName);
                break;
            }
        }
            return false;
        }

        try {
            account.withdraw(price);
            account = client1.bankobj.getAccount(owner);
            account.deposit(price);
        } catch (Exception ex) {
            Logger.getLogger(customerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("You bought " + itemBuy.getItemName() + " for " + itemBuy.getItemPrice());

        me.itemList.remove(itemBuy);

        /*
        
         itemList.add(new item(itemBuy.getItemName(), price+100, customerName));//if the buyer wants to sell it at higher value
         */
        //send an item sold notification to owner.
        for (callBack m : marketPlaceImpl.allClients) {
            if (m.getName().equals(owner)) {
                m.itemSold(owner);
                break;
            }
        }

        return true;
    }
//debugged and worked
    @Override
    public void sellItem(String itemName, float itemPrice) throws RemoteException {
        item item1 = new item(itemName, itemPrice, customerName);
        if (item1 != null) {
            itemList.add(item1);
        }
        checkWish(item1);
    }
//debugged and worked
    @Override
    public void addItemWish(String itemName, float itemPrice) throws RemoteException {
        item item1 = new item(itemName, itemPrice, customerName);

        Map<String, customerImpl> allCustomers = marketPlaceImpl.members;//find all customers of the marketplace
        String[] str = allCustomers.keySet().toArray(new String[1]);//find the key(in this case name) of the customers
        customerImpl me = null;
        for (int i = 0; i < str.length; i++) {//check if the requested item is in the marketplace
            me = allCustomers.get(str[i]);
            Iterator<item> listIterator = me.itemList.iterator();

            while (listIterator.hasNext()) {
                item itemwish = listIterator.next();
                if ((itemwish.getItemName().compareToIgnoreCase(itemName) == 0) && itemwish.getItemPrice() <= itemPrice) {
                    System.out.println("There is an item with the specified price");
                }
            }
        }
        itemWished.add(item1);
    }
//debugged and worked
    public void checkWish(item item1) throws RemoteException {
        Map<String, customerImpl> allCustomers = marketPlaceImpl.members;//find all customers of the marketplace
        String[] str = allCustomers.keySet().toArray(new String[1]);//find the key(in this case name) of the customers
        item itemFound = null;
        customerImpl me = null;
        for (int i = 0; i < str.length; i++) {//check if the new item has been wished in the marketplace
            me = allCustomers.get(str[i]);
            Iterator<item> listIterator = me.itemWished.iterator();

            while (listIterator.hasNext()) {//check if a customer provides an item for sell
                item itemwish = listIterator.next();
                if (itemwish.getItemName().equals(item1.getItemName()) && itemwish.getItemPrice() <= item1.getItemPrice()) {
                    itemFound = itemwish;
                }
            }
        }
        if (itemFound != null) {
             for (callBack m : marketPlaceImpl.allClients) {
            if (m.getName().equals(itemFound.getItemOwner())) {
                m.wishItemFound(itemFound.getItemOwner(), itemFound.getItemName(), itemFound.getItemPrice());
                break;
            }
        }
        }
    }

}
