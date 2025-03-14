package com.example.gson_broadcast;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<User> adapter;
    private EditText etName, etPhone;
    private List<User> users;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        listView = findViewById(R.id.listView);
        users = new ArrayList<>();

        // Настройка адаптера для ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);

        // Обработчики нажатий на кнопки
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnLoad = findViewById(R.id.btnLoad);

        btnAdd.setOnClickListener(this::addUser);
        btnSave.setOnClickListener(this::save);
        btnLoad.setOnClickListener(this::load);
    }

    // Метод для добавления контакта
    public void addUser(View view) {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        if (!name.isEmpty() && !phone.isEmpty()) {
            User user = new User(name, phone);
            users.add(user);
            adapter.notifyDataSetChanged(); // Обновляем список
            etName.getText().clear(); // Очищаем поле ввода имени
            etPhone.getText().clear(); // Очищаем поле ввода телефона
            Toast.makeText(this, "Контакт добавлен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для сохранения контактов
    public void save(View view) {
        boolean result = JSONHelper.exportToJSON(this, users);
        if (result) {
            Toast.makeText(this, "Контакты сохранены", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Не удалось сохранить контакты", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для загрузки контактов
    public void load(View view) {
        List<User> loadedUsers = JSONHelper.importFromJSON(this);
        if (loadedUsers != null) {
            users.clear();
            users.addAll(loadedUsers);
            adapter.notifyDataSetChanged(); // Обновляем список
            Toast.makeText(this, "Контакты загружены", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Не удалось загрузить контакты", Toast.LENGTH_SHORT).show();
        }
    }
}