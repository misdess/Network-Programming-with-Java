package rmi_marketplace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.StringTokenizer;

public class client extends UnicastRemoteObject implements callBack {

    private static final String USAGE = "java bankrmi.Client <bank_url>";
    private static final String DEFAULT_BANK_NAME = "Dembel";
    customer account;
    marketPlace marketPlaceobj;
    String clientname;
    String passWord;

    static enum CommandName {

        register, login, unregister, sell, buy, wish, inspect, quit, help, list;
    };

    public client(String name, String password) throws RemoteException {
        super();
        this.clientname = name;
        this.passWord=password;
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            marketPlaceobj = (marketPlace) Naming.lookup(DEFAULT_BANK_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to marketplace: " + DEFAULT_BANK_NAME);
//        run();
    }
    public client(String name) throws RemoteException{
    this(name, "");}

    @Override
    public void wishItemFound(String customerName, String itemName, float itemPrice) throws RemoteException {
        System.out.println("Hello!! " + customerName + ", " + itemName + " is found for " + itemPrice);

    }
    @Override
    public void lowBalance(String name)  throws RemoteException{
        System.out.println("Hello "+name +": Your balance is low.");
    }

    @Override
    public void itemSold(String name) throws RemoteException {
        System.out.println("Hello "+name +": Your Item is sold ");
    }

    @Override
    public String getName() throws RemoteException {

        return clientname;
    }

    public String getPassWord() throws RemoteException {

        return passWord;
    }
    public void run() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(clientname + "@" + DEFAULT_BANK_NAME + ">");
            try {
                String userInput = consoleIn.readLine();
                execute(parse(userInput));
            } catch (RejectedException re) {
                System.out.println(re);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Command parse(String userInput) {
        if (userInput == null) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(userInput);
        if (tokenizer.countTokens() == 0) {
            return null;
        }

        CommandName commandName = null;
        String userName = null;
        String itemName = null;
        String password=null;
        float amount = 0;
        int userInputTokenNo = 1;

        while (tokenizer.hasMoreTokens()) {
            switch (userInputTokenNo) {
                case 1:
                    try {
                        String commandNameString = tokenizer.nextToken();
                        commandName = CommandName.valueOf(CommandName.class, commandNameString);
                    } catch (IllegalArgumentException commandDoesNotExist) {
                        System.out.println("Illegal command");
                        return null;
                    }
                    break;
                case 2:
                    userName = tokenizer.nextToken();
                    
                    break;
                case 3:
                    try {
                        itemName = tokenizer.nextToken();
                        password=itemName;
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal item");
                        return null;
                    }
                    break;
                case 4:
                    try {
                        amount = Float.parseFloat(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal amount");
                        return null;
                    }
                    break;
                default:
                    System.out.println("Illegal command");
                    return null;
            }
            userInputTokenNo++;
        }
        return new Command(commandName, userName, itemName, amount);
    }

    void execute(Command command) throws RemoteException, RejectedException {
        if (command == null) {
            return;
        }

        switch (command.getCommandName()) {
            case list:
                try {
                    for (String member : marketPlaceobj.listMembers()) {
                        System.out.println(member);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                return;
            case inspect:
                try {
                    for (String item1 : marketPlaceobj.inspect()) {
                        System.out.println(item1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                return;
            case quit:
                System.exit(0);
            case help:
                for (CommandName commandName : CommandName.values()) {
                    System.out.println(commandName);
                }
                return;

        }

        // all further commands require a name to be specified
        String userName = command.getUserName();
        String passWord=command.getPassWord();
        if (userName == null) {
            userName = clientname;
        }

        if (userName == null) {
            System.out.println("name is not specified");
            return;
        }

        switch (command.getCommandName()) {
            
            case unregister:
                clientname = null;
                marketPlaceobj.unregister(new client(userName));
                return;
        }

        // all further commands require a Account reference
        customer acc = marketPlaceobj.register(new client(userName));
        if (acc == null) {
            System.out.println("No account for " + userName);
            return;
        } else {
            account = acc;
            clientname = userName;
        }

        switch (command.getCommandName()) {
            case register:
                clientname = userName;
                marketPlaceobj.register(new client(userName, passWord));
                return;
            case login:
                System.out.println(account);
                break;
            case sell:
                account.sellItem(command.getItemName(), command.getAmount());
                break;
            case buy:
                account.buyItem(command.getItemName(), command.getAmount());
                break;
            case wish:
                account.addItemWish(command.getItemName(), command.getAmount());
                break;
            default:
                System.out.println("Illegal command");
        }
    }

    private class Command {

        private String userName;
        private String psword;
        private float amount;
        private CommandName commandName;
        private String itemName;

        private String getUserName() {
            return userName;
        }

        private float getAmount() {
            return amount;
        }

        private CommandName getCommandName() {
            return commandName;
        }

        private String getItemName() {
            return itemName;
        }
        private String getPassWord() {
            return psword;
        }

        private Command(client.CommandName commandName, String userName, String itemName, float amount) {
            this.commandName = commandName;
            this.userName = userName;
            this.itemName = itemName;
            this.amount = amount;
        }
    }

    public static void main(String[] args) throws RemoteException {
        if ((args.length > 1) || (args.length > 0 && args[0].equals("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }
        new client("null").run();
    }
}
