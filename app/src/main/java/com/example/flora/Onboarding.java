package com.example.flora;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
// لم يتم استخدام EdgeToEdge.enable(this) الموجود في الكود الأصلي المرسل، واعتمدنا على طريقة ViewCompat.setOnApplyWindowInsetsListener

public class Onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1️⃣ Load the layout
        setContentView(R.layout.activity_onboarding);

        // 2️⃣ Handle system insets (Edge-to-edge setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3️⃣ Now find your button and set the click listener
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            // الانتقال إلى شاشة Home مباشرة
            startActivity(new Intent(Onboarding.this, Home.class));
            // إنهاء شاشة Onboarding لمنع العودة إليها بالزر الخلفي
            finish();
        });
    }
}

