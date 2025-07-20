package com.example.recycler_view;

public class Contact_class {
     int id;
    String name;
    String phone;
    public int image;

    public Contact_class(int id, String name, String phone, int image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    // Constructor for new contacts (no ID yet)
    public Contact_class(String name, String phone, int image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
