package rmi_marketplace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class marketPlaceImpl extends UnicastRemoteObject implements marketPlace {

    private String customerName;
    
    public static final Map<String, customerImpl> members = new HashMap<>();
    public static final List<callBack> allClients = new ArrayList<>();

    public marketPlaceImpl(String name) throws RemoteException, ClassNotFoundException, SQLException {
        super();
        customerName=name;
    }
    
    public Map<String, customerImpl> allCustomers() {
        return members;
    }

    public String[] listMembers() throws RemoteException {
        return members.keySet().toArray(new String[1]);

    }

    public String[] inspect() throws RemoteException {//returns all items of the marketplace
        String[] allItems = new String[300];
        int index = 0;
        String[] str = members.keySet().toArray(new String[1]);//find the key(in this case name) of the customers
        System.out.println(str.length);
        if (str.length != 0) {
            customerImpl me = null;
            for (int i = 0; i < str.length; i++) {//check if the requested item is in the marketplace
                me = members.get(str[i]);
                Iterator<item> listIterator = me.getItems().iterator();//all items of a member
                while (listIterator.hasNext()) {
                    allItems[index] = listIterator.next().getItemName();
                    index++;
                }
            }
            String[] listOfItems = new String[index];
//            listOfItems[0] = null;
            for (int i = 0; i < index; i++) {
                listOfItems[i] = allItems[i];
            }
            return listOfItems;
        }
        return null;
    }

    @Override
    public customer register(callBack na) throws RemoteException {

        customerName = na.getName();
        customerImpl member = (customerImpl) members.get(customerName);
        if (member != null)
            ;//throws new Exception;
        else {
            member = new customerImpl(customerName);
            members.put(customerName, member);
        }
        allClients.add(na);
        return member;
    }

    @Override
    public boolean unregister(callBack na) throws RemoteException {
        boolean memberRemoved = false;

        customerImpl member = (customerImpl) members.get(na.getName());
        if (member != null) {
            members.remove(na.getName());
            memberRemoved = true;
        }
        allClients.remove(na);
        return memberRemoved;
    }

    public customer login(String name) throws RemoteException {
        return members.get(name);
    }
}
