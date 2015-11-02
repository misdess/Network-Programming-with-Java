package rmi_marketplace;

import java.util.*;

public class item {

    final private String name;
    final private float price;
    final private String owner;

    public item() {
        name = null;
        price = 0;
        owner = null;
    }

    public item(String na, float p, String own) {
        name = na;
        price = p;
        owner = own;
    }

    public String getItemName() {
        return name;
    }

    public float getItemPrice() {
        return price;
    }

    public String getItemOwner() {
        return owner;
    }

    public static void main(String[] args) {
        List<item> itemList = new ArrayList<>();
        item item1 = new item("pen", 10, "Misganu");
        itemList.add(item1);
        item item2 = new item("pencil", 20, "melu");
        itemList.add(item2);
        item item3 = new item("book", 30, "tajebe");
        itemList.add(item3);
        item item4 = new item("chair", 40, "hagos");
        itemList.add(item4);
        item item5 = new item("chair", 50, "kidane");
        itemList.add(item5);
//iterator i=itemList.iterator();
        for (int i = 0; i < itemList.size(); i++) {

            System.out.println(itemList.get(itemList.size() - 1 - i).getItemName());

        }
    }
}
