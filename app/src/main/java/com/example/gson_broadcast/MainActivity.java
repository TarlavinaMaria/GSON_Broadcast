package com.example.gson_broadcast;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     EditText etName, etPhone;
     Button btnAdd, btnSave, btnLoad;
     ListView listView;
     List<Contact> contacts = new ArrayList<>();
     ArrayAdapter<Contact> adapter;
     static final String FILE_NAME = "contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> addContact());

        btnSave.setOnClickListener(v -> saveContacts());

        btnLoad.setOnClickListener(v -> loadContacts());
    }

    private void addContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        contacts.add(new Contact(name, phone));
        adapter.notifyDataSetChanged();
        etName.getText().clear();
        etPhone.getText().clear();
    }

    private void saveContacts() {
        Gson gson = new Gson();
        String json = gson.toJson(contacts);

        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
            Toast.makeText(this, "Контакты сохранены", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadContacts() {
        Gson gson = new Gson();

        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String json = new String(buffer);

            Type type = new TypeToken<List<Contact>>() {}.getType();
            contacts.clear();
            contacts.addAll(gson.fromJson(json, type));
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Контакты загружены", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Нет сохраненных данных", Toast.LENGTH_SHORT).show();
        }
    }
}