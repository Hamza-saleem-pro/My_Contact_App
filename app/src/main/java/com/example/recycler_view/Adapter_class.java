package com.example.recycler_view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_class extends RecyclerView.Adapter<My_view_holder> {
    Context context;
    ArrayList<Contact_class> arrayList;
    DB_Helper dbHelper;

    public Adapter_class(Context context, ArrayList<Contact_class> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        dbHelper = new DB_Helper(context);
    }

    @NonNull
    @Override
    public My_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new My_view_holder(LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull My_view_holder holder, @SuppressLint("RecyclerView") int position) {
        Contact_class contact = arrayList.get(position);

        holder.image.setImageResource(contact.image);
        holder.name.setText(contact.name);
        holder.contact.setText(contact.phone);

        holder.linear.setOnClickListener(view -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.add_contact);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            TextView tv_heading;
            EditText et_name, et_contact;
            Button btn_add;

            et_name = dialog.findViewById(R.id.et_name);
            et_contact = dialog.findViewById(R.id.et_contact);
            btn_add = dialog.findViewById(R.id.btn_add);
            tv_heading = dialog.findViewById(R.id.tv_heading);

            tv_heading.setText("Update Contact");
            btn_add.setText("Update");

            et_name.setText(contact.getName());
            et_contact.setText(contact.getPhone());

            btn_add.setOnClickListener(v -> {
                String name = et_name.getText().toString();
                String contactNum = et_contact.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(context, "Please Enter Contact Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contactNum.isEmpty()) {
                    Toast.makeText(context, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update in DB
                boolean updated = dbHelper.updateContact(contact.getId(), name, contactNum, R.drawable.img1);
                if (updated) {
                    arrayList.set(position, new Contact_class(contact.getId(), name, contactNum, R.drawable.img1));
                    notifyItemChanged(position);
                    Toast.makeText(context, "Contact updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            });

            dialog.show();
        });

        holder.linear.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete this contact?")
                    .setIcon(R.drawable.outline_delete_24)
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        boolean deleted = dbHelper.deleteContact(contact.getId());
                        if (deleted) {
                            arrayList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Contact deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Deletion failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
