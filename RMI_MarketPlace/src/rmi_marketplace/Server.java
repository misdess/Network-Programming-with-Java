package rmi_marketplace;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static final String USAGE = "java marketPlacermi.Server <marketPlace_rmi_url>";
    private static final String marketPlace = "Dembel";

    public Server(String marketPlaceName) {
        try {
            marketPlaceImpl bankobj = new marketPlaceImpl(marketPlaceName);
            // Register the newly created object at rmiregistry.
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(marketPlaceName, bankobj);
            System.out.println(bankobj + " is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String marketPlaceName;
        if (args.length > 0) {
            marketPlaceName = args[0];
        } else {
            marketPlaceName = marketPlace;
        }

        new Server(marketPlaceName);
    }
    
    
    
    
}
