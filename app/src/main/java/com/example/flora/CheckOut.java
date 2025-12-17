package com.example.flora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOut extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvTotalPrice;
    Button btnConfirm;

    // كلاس داخلي لتمثيل العنصر المجمع (الزهرة والكمية)
    // هذا الكلاس ضروري لحمل البيانات المجمعة
    public static class CartItemData {
        Home.Flower flower;
        int quantity;
        public CartItemData(Home.Flower f, int q) {
            this.flower = f;
            this.quantity = q;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        recyclerView = findViewById(R.id.recycler_cart_items);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnConfirm = findViewById(R.id.btn_confirm);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateCartDisplay();

        btnConfirm.setOnClickListener(v -> {
            if (Home.cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is empty. Add flowers first!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Order Confirmed! Total: $" + Home.getCartTotal(), Toast.LENGTH_LONG).show();
                Home.clearCart();
                finish();
            }
        });
    }

    // دالة تقوم بتجميع العناصر المتشابهة في قائمة جديدة
    private List<CartItemData> groupCartItems(List<Home.Flower> cartList) {

        // نستخدم HashMap مؤقت لتخزين العناصر الفريدة وكمياتها
        Map<String, CartItemData> groupedMap = new HashMap<>();

        for (Home.Flower item : cartList) {
            // نستخدم تركيبة من خصائص الزهرة كمفتاح (بدلاً من equals/hashCode المعقد)
            String key = item.name + item.price + item.imageRes;

            if (groupedMap.containsKey(key)) {
                // إذا كان المفتاح موجوداً، نزيد الكمية
                groupedMap.get(key).quantity++;
            } else {
                // إذا كان المفتاح جديداً، نضيف العنصر بكمية 1
                groupedMap.put(key, new CartItemData(item, 1));
            }
        }

        // نُرجع قائمة بالقيم (Values) من الـ Map، وهي العناصر المجمعة
        return new ArrayList<>(groupedMap.values());
    }


    private void updateCartDisplay() {
        // نستخدم الدالة الجديدة groupCartItems
        List<CartItemData> groupedList = groupCartItems(Home.cartItems);

        CartAdapter adapter = new CartAdapter(groupedList);
        recyclerView.setAdapter(adapter);

        tvTotalPrice.setText("$" + Home.getCartTotal());
    }


    // --------------------------------------------
    // INNER CLASS — Adapter لعرض سلة التسوق (يستخدم CartItemData)
    // --------------------------------------------
    class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

        private List<CartItemData> cartList;

        public CartAdapter(List<CartItemData> cartList) {
            this.cartList = cartList;
        }

        @Override
        public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cart, parent, false);
            return new CartHolder(v);
        }

        @Override
        public void onBindViewHolder(CartHolder holder, int position) {
            CartItemData data = cartList.get(position);
            Home.Flower item = data.flower;
            int quantity = data.quantity;

            holder.tvName.setText(item.name);
            holder.imgItem.setImageResource(item.imageRes);

            // عرض الكمية
            holder.tvQuantity.setText("Qty: x" + quantity);

            // عرض السعر الإجمالي لهذا العنصر (السعر * الكمية)
            int itemTotalPrice = item.price * quantity;
            holder.tvPrice.setText("$" + itemTotalPrice);
        }

        @Override
        public int getItemCount() {
            return cartList.size();
        }
//nadoda
        class CartHolder extends RecyclerView.ViewHolder {
            ImageView imgItem;
            TextView tvName;
            TextView tvPrice;
            TextView tvQuantity;

            CartHolder(View v) {
                super(v);
                imgItem = v.findViewById(R.id.img_cart_item);
                tvName = v.findViewById(R.id.tv_cart_name);
                tvPrice = v.findViewById(R.id.tv_cart_price);
                tvQuantity = v.findViewById(R.id.tv_cart_quantity);
            }
        }
    }
}
