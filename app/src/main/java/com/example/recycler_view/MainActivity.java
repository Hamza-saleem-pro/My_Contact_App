package com.example.recycler_view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton flt_button;
    RecyclerView recyclerView;
    SearchView search_view;
    ArrayList<Contact_class> fullList;
    ArrayList<Contact_class> filteredList;
    Adapter_class adapter;
    DB_Helper dbHelper;

    EditText et_name, et_contact;
    Button btn_add;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        search_view = findViewById(R.id.srch_view);
        recyclerView = findViewById(R.id.recycler_view);
        flt_button = findViewById(R.id.flt_button);

        dbHelper = new DB_Helper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (dbHelper.getAllContacts().isEmpty()) {
            // Insert dummy data if no contacts exist
            dbHelper.insertContact("Ahmed", "03001234567", R.drawable.img1);
            dbHelper.insertContact("Fatima", "03007654321", R.drawable.img2);
            dbHelper.insertContact("Ali", "03123456789", R.drawable.img3);
            dbHelper.insertContact("Zainab", "03211223344", R.drawable.img1);
            dbHelper.insertContact("Usman", "03005554433", R.drawable.img2);
            dbHelper.insertContact("Ayesha", "03451239876", R.drawable.img3);
            dbHelper.insertContact("Hassan", "03331112233", R.drawable.img1);
            dbHelper.insertContact("Khadija", "03007778899", R.drawable.img2);
            dbHelper.insertContact("Bilal", "03112223344", R.drawable.img3);
            dbHelper.insertContact("Maryam", "03213456789", R.drawable.img1);
        }

        fullList = dbHelper.getAllContacts();
        filteredList = new ArrayList<>(fullList);

        adapter = new Adapter_class(this, filteredList);
        recyclerView.setAdapter(adapter);

        flt_button.setOnClickListener(view -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_contact);

            et_name = dialog.findViewById(R.id.et_name);
            et_contact = dialog.findViewById(R.id.et_contact);
            btn_add = dialog.findViewById(R.id.btn_add);

            btn_add.setOnClickListener(view1 -> {
                String name = et_name.getText().toString();
                String contact = et_contact.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Contact Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contact.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean inserted = dbHelper.insertContact(name, contact, R.drawable.img1);
                if (inserted) {
                    fullList.clear();
                    fullList.addAll(dbHelper.getAllContacts());
                    filterList(search_view.getQuery().toString());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Contact Saved", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save contact", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        });

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }

    private void filterList(String newText) {
        filteredList.clear();
        if (newText.isEmpty()) {
            filteredList.addAll(fullList);
        } else {
            for (Contact_class item : fullList) {
                if (item.getName().toLowerCase().startsWith(newText.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
