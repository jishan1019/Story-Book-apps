package com.example.banglastoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap <String, String> hashMap;
    Button privacyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        privacyBtn = findViewById(R.id.privacyBtn);

        hashMap = new HashMap<>();
        hashMap.put("categoryTitle", "Golpo 1");
        hashMap.put("categoryName", "Vuter Golpo");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("categoryTitle", "Golpo 2");
        hashMap.put("categoryName", "Vuter Golpo");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("categoryTitle", "Golpo 3");
        hashMap.put("categoryName", "Vuter Golpo");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("categoryTitle", "Golpo 4");
        hashMap.put("categoryName", "Vuter Golpo");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("categoryTitle", "Golpo 5");
        hashMap.put("categoryName", "Vuter Golpo");
        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("categoryTitle", "Golpo 6");
        hashMap.put("categoryName", "Vuter Golpo");
        arrayList.add(hashMap);



        MyAdaptar myAdaptar = new MyAdaptar();
        gridView.setAdapter(myAdaptar);

        privacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
            }
        });

    }

    private class MyAdaptar extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.story_card, parent, false);

            CardView storyBtn = view.findViewById(R.id.storyBtn);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvName = view.findViewById(R.id.tvName);

            HashMap<String, String> hashMap = arrayList.get(position);

            String categoryTitle = hashMap.get("categoryTitle");
            String categoryName = hashMap.get("categoryName");

            tvTitle.setText(categoryTitle);
            tvName.setText(categoryName);

            storyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoryDetailsActivity.categoryId = position;
                    startActivity(new Intent(MainActivity.this, StoryDetailsActivity.class));
                }
            });

            return view;
        }
    }


}