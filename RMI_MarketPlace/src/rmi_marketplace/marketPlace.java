package rmi_marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface marketPlace extends Remote {

    public Map<String, customerImpl> allCustomers() throws RemoteException;

    public customer register(callBack name) throws RemoteException;

    public boolean unregister(callBack name) throws RemoteException;
    public customer login(String name) throws RemoteException;

   // public void notifyItemSold(String owner, item item1) throws RemoteException;
    
    public String[] listMembers()throws RemoteException;
    
    public String[] inspect() throws RemoteException ;

}
