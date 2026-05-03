package com.semo.cisproject.campushub.util;

import com.semo.cisproject.campushub.model.Category;
import com.semo.cisproject.campushub.model.Product;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public List<Product> getNewList() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("1", "MacBook Pro M2", "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=800&q=80", "1299.00", "Electronics", "10%"));
        list.add(new Product("2", "Sony WH-1000XM5", "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?auto=format&fit=crop&w=800&q=80", "348.00", "Audio", "5%"));
        list.add(new Product("3", "Hydro Flask 32oz", "https://images.unsplash.com/photo-1602143407151-7111542de6e8?auto=format&fit=crop&w=800&q=80", "44.95", "Accessories", "0%"));
        list.add(new Product("4", "Moleskine Notebook", "https://images.unsplash.com/photo-1531346878377-a5be20888e57?auto=format&fit=crop&w=800&q=80", "22.50", "Stationery", "0%"));
        list.add(new Product("5", "Logitech MX Master 3S", "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?auto=format&fit=crop&w=800&q=80", "99.00", "Electronics", "0%"));
        list.add(new Product("6", "Jansport Backpack", "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?auto=format&fit=crop&w=800&q=80", "55.00", "Apparel", "15%"));
        list.add(new Product("8", "Anker Power Bank", "https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5?auto=format&fit=crop&w=800&q=80", "39.99", "Electronics", "0%"));
        return list;
    }

    public List<Product> getPopularList() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("9", "Campus Coffee", "https://images.unsplash.com/photo-1509042239860-f550ce710b93?auto=format&fit=crop&w=800&q=80", "4.50", "Food", "50%"));
        list.add(new Product("10", "Student Planner 2026", "https://images.unsplash.com/photo-1506784983877-45594efa4cbe?auto=format&fit=crop&w=800&q=80", "15.00", "Stationery", "0%"));
        return list;
    }

    public List<Product> getOfferList() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("11", "University Hoodie", "https://images.unsplash.com/photo-1556821840-3a63f95609a7?auto=format&fit=crop&w=800&q=80", "35.00", "Apparel", "20%"));
        // UPDATED MICROWAVE LINK BELOW
        list.add(new Product("12", "Dorm Microwave", "https://images.unsplash.com/photo-1585232004423-244e0e6904e3?auto=format&fit=crop&w=800&q=80", "65.00", "Appliances", "10%"));
        list.add(new Product("13", "Desk Lamp", "https://images.unsplash.com/photo-1534073828943-f801091bb18c?auto=format&fit=crop&w=800&q=80", "24.00", "Furniture", "15%"));
        return list;
    }

    public List<Category> getCategoryList() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("1", "Electronics", "https://images.unsplash.com/photo-1498049794561-7780e7231661?auto=format&fit=crop&w=800&q=80"));
        list.add(new Category("2", "Apparel", "https://images.unsplash.com/photo-1523381210434-271e8be1f52b?auto=format&fit=crop&w=800&q=80"));
        list.add(new Category("3", "Stationery", "https://images.unsplash.com/photo-1518655048521-f130df041f66?auto=format&fit=crop&w=800&q=80"));
        list.add(new Category("4", "Food", "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=800&q=80"));
        return list;
    }
}