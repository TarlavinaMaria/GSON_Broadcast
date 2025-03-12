package com.example.gson_broadcast;

import androidx.annotation.NonNull;

public class Contact {
    // Класс для хранения контактов
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + phoneNumber;
    }
}