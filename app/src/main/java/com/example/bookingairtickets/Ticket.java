package com.example.bookingairtickets;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Ticket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        TextView from = findViewById(R.id.from);
        from.setText("  страна - " + getIntent().getStringExtra("count1"));

        TextView to = findViewById(R.id.to);
        to.setText("  страна - " + getIntent().getStringExtra("count2"));

        TextView fromDate = findViewById(R.id.fromDate);
        fromDate.setText("  дата    - " + getIntent().getStringExtra("date1"));

        TextView toDate = findViewById(R.id.toDate);
        toDate.setText("  дата    - " + getIntent().getStringExtra("date2"));

        TextView adult = findViewById(R.id.adult);
        adult.setText("Взрослые - " + getIntent().getStringExtra("ad"));

        TextView child = findViewById(R.id.child);
        child.setText("Дети до 12 лет - " + getIntent().getStringExtra("ch"));

        TextView infant = findViewById(R.id.infant);
        infant.setText("Дети до 2 лет - " + getIntent().getStringExtra("inf"));
    }
}
