package com.example.flora;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private static List<Home.Flower> items = new ArrayList<>();

    private ShoppingCart() {}

    public static void addItem(Home.Flower item) {
        items.add(item);
    }

    public static List<Home.Flower> getItems() {
        return items;
    }

    public static void clearCart() {
        items.clear();
    }

    public static int getTotalPrice() {
        int total = 0;
        for (Home.Flower item : items) {
            total += item.price;
        }
        return total;
    }

    public static boolean isEmpty() {
        return items.isEmpty();
    }

}
