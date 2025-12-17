package com.example.flora;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    ImageView imageView;
    TextView tvName;
    TextView tvPrice;
    TextView tvDescription;
    TextView tvMeaning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        // Initialize views
        imageView = findViewById(R.id.img_flower_detail);
        tvName = findViewById(R.id.tv_name_detail);
        tvPrice = findViewById(R.id.tv_price_detail);
        tvDescription = findViewById(R.id.tv_description);
        tvMeaning = findViewById(R.id.tv_meaning);


        // Get data from intent
        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price", 0);
        int imageRes = getIntent().getIntExtra("imageRes", 0);

        Button btnCheckout = findViewById(R.id.btn_checkout);

        btnCheckout.setOnClickListener(v -> {
            Home.Flower currentFlower = new Home.Flower(name, price, imageRes);

            Home.addItemToCart(currentFlower);
            android.widget.Toast.makeText(Details.this, name + " added to cart", android.widget.Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Details.this, CheckOut.class);
            startActivity(intent);
        });

        // Set data to views
        tvName.setText(name);
        tvPrice.setText("$" + price);
        imageView.setImageResource(imageRes);

        // Set description and meaning based on flower
        tvDescription.setText(getFlowerDescription(name));
        tvMeaning.setText(getFlowerMeaning(name));


    }

    private String getFlowerDescription(String flowerName) {
        switch (flowerName) {
            case "Flower 1":
                return "Beautiful roses that symbolize love and passion. Perfect for special occasions and romantic gestures. These fresh blooms will brighten any room with their elegant appearance and sweet fragrance.";
            case "Flower 2":
                return "Elegant tulips known for their graceful appearance and vibrant colors. Ideal for spring celebrations and adding a splash of color to your home. These flowers are admired worldwide for their simple yet stunning beauty.";
            case "Flower 3":
                return "Cheerful sunflowers that bring warmth and happiness wherever they're placed. Perfect for brightening someone's day with their bold, sunny appearance. These stunning flowers are known for following the sun.";
            case "Flower 4":
                return "Delicate orchids representing luxury and beauty. Perfect for sophisticated settings and special gifts. These exotic blooms are long-lasting and add an elegant touch to any space.";
            default:
                return "A beautiful flower arrangement carefully selected for you. Fresh, vibrant, and perfect for any occasion.";
        }
    }

    private String getFlowerMeaning(String flowerName) {
        switch (flowerName) {
            case "Flower 1":
                return "Roses represent deep love, romance, and admiration. They are a timeless symbol of affection and passion, making them perfect for expressing heartfelt emotions.";
            case "Flower 2":
                return "Tulips symbolize perfect love and spring renewal. They represent elegance, grace, and the promise of new beginnings.";
            case "Flower 3":
                return "Sunflowers mean adoration, loyalty, and longevity. They represent warmth, happiness, and the ability to bring joy to others.";
            case "Flower 4":
                return "Orchids symbolize luxury, beauty, and strength. They represent rare and delicate beauty, refinement, and thoughtfulness.";
            default:
                return "Flowers represent beauty, love, and the joy of nature. They bring happiness and positive energy to any environment.";
        }
    }
}
