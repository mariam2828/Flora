package com.example.flora;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Home extends AppCompatActivity {
    public static List<Flower> cartItems = new ArrayList<>();

    public static void addItemToCart(Flower item) {
        cartItems.add(item);
    }

    public static int getCartTotal() {
        int total = 0;
        for (Flower item : cartItems) {
            total += item.price;
        }
        return total;
    }

    public static void clearCart() {
        cartItems.clear();
    }
    // --------------------------------------------

    RecyclerView recycler;
    FloraAdapter adapter;
    List<Flower> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));

        data = new ArrayList<>();
        int[] images = {R.drawable.flower1, R.drawable.flower2, R.drawable.flower3, R.drawable.flower4};
        Random rnd = new Random();

        for (int i = 0; i < images.length; i++) {
            int price = 50 + rnd.nextInt(300);
            data.add(new Flower("Flower " + (i + 1), price, images[i]));
        }

        adapter = new FloraAdapter(data, this);
        recycler.setAdapter(adapter);
    }

    // --------------------------------------------
    // INNER CLASS 1 — Flower Model
    // --------------------------------------------
    static class Flower {
        public String name;
        public int price;
        public int imageRes;

        public Flower(String name, int price, int imageRes) {
            this.name = name;
            this.price = price;
            this.imageRes = imageRes;
        }
    }

    // --------------------------------------------
    // INNER CLASS 2 — Adapter
    // --------------------------------------------
    class FloraAdapter extends RecyclerView.Adapter<FloraAdapter.Holder> {

        List<Flower> list;
        Context ctx;

        public FloraAdapter(List<Flower> list, Context ctx) {
            this.list = list;
            this.ctx = ctx;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flora, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Flower f = list.get(position);

            holder.name.setText(f.name);
            holder.price.setText("$" + f.price);
            holder.image.setImageResource(f.imageRes);

            holder.add.setOnClickListener(v -> {

                Home.addItemToCart(f);

                android.widget.Toast.makeText(ctx, f.name + " added to cart", android.widget.Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ctx, CheckOut.class);
                ctx.startActivity(intent);
            });

            // When card is clicked  go to Details page
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(ctx, Details.class);
                intent.putExtra("name", f.name);
                intent.putExtra("price", f.price);
                intent.putExtra("imageRes", f.imageRes);
                ctx.startActivity(intent);
            });

            // When card is clicked  go to Details page
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(ctx, Details.class);
                intent.putExtra("name", f.name);
                intent.putExtra("price", f.price);
                intent.putExtra("imageRes", f.imageRes);
                ctx.startActivity(intent);
            });

            holder.itemView.setOnLongClickListener(v -> {
                android.widget.Toast.makeText(ctx, "Tap to view details about " + f.name, android.widget.Toast.LENGTH_SHORT).show();
                return true;
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name;
            TextView price;
            ImageButton add;

           

            Holder(View v) {
                super(v);
                image = v.findViewById(R.id.img_flower);
                name = v.findViewById(R.id.tv_name);
                price = v.findViewById(R.id.tv_price);
                add = v.findViewById(R.id.btn_add);


            }
        }
    }
}
