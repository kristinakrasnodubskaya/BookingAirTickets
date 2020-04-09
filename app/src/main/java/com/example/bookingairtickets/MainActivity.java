package com.example.bookingairtickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    Resources r;
    String[] countries;

    String countDEP = "";
    String countARR = "";

    String dateDEP;
    String dateARR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r = getResources();
        countries = r.getStringArray(R.array.countries);

        Spinner dep = (Spinner) findViewById(R.id.DEP);
        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(adapter1);
        dep.setSelection(0);


        ArrayList<String> count = new ArrayList<>();
        count.addAll(Arrays.asList(Arrays.copyOfRange(countries, 1, countries.length - 1)));
        count.add(0, "назначение");

        Spinner arr = (Spinner) findViewById(R.id.ARR);
        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, count);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr.setAdapter(adapter);
        arr.setSelection(0);


        dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    countDEP = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        arr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    countARR = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void onClickBooking(View view) {
        boolean correct = true;
        int day1 = 0, day2 = 0, mon1 = 0, mon2 = 0, year1 = 0, year2 = 0;

        //работа с датами вылета и прилета
        EditText dt1 = findViewById(R.id.dateDEP);
        dateDEP = dt1.getText().toString();
        EditText dt2 = findViewById(R.id.dateARR);
        dateARR = dt2.getText().toString();

        if (dateDEP.equals("") || dateARR.equals("")) {
            Toast.makeText(getBaseContext(), "Не задана дата вылета/прилета", Toast.LENGTH_SHORT).show();
            correct = false;
        } else {
            String[] datesDEP = dateDEP.split("[-://]");
            if (datesDEP.length != 3) {
                Toast.makeText(getBaseContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show();
                correct = false;
            } else {
                day1 = Integer.parseInt(datesDEP[0]);
                mon1 = Integer.parseInt(datesDEP[1]);
                year1 = Integer.parseInt(datesDEP[2]);
            }

            String[] datesARR = dateARR.split("[-://]");
            if (datesARR.length != 3) {
                Toast.makeText(getBaseContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show();
                correct = false;
            } else {
                day2 = Integer.parseInt(datesARR[0]);
                mon2 = Integer.parseInt(datesARR[1]);
                year2 = Integer.parseInt(datesARR[2]);
            }

            if (day1 < 1 || day1 > 31 || mon1 < 1 || mon1 > 12 || day2 < 1 || day2 > 31 || mon2 < 1 || mon2 > 12) {
                Toast.makeText(getBaseContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show();
                correct = false;
            }

            int e = 0;
            if (year1 == year2) {
                if (mon1 == mon2) {
                    if (day1 > day2) {
                        e = 1;
                    }
                } else if (mon1 > mon2) {
                    e = 1;
                }
            } else if (year1 > year2) {
                e = 1;
            }
            if (e > 0) {
                Toast.makeText(getBaseContext(), "Дата вылета позже даты прилета", Toast.LENGTH_SHORT).show();
                correct = false;
            }

            dateDEP = day1 + "/" + mon1 + "/" + year1;
            dateARR = day2 + "/" + mon2 + "/" + year2;
        }
        //проверка на совпадение страны отправления и страны назначения
        if (countDEP == "") {
            Toast.makeText(getBaseContext(), "Выберите страну отправления", Toast.LENGTH_SHORT).show();
            correct = false;
        }
        if (countARR.equals("")) {
            Toast.makeText(getBaseContext(), "Выберите страну назначения", Toast.LENGTH_SHORT).show();
            correct = false;
        }
        if (countDEP.equals(countARR)) {
            Toast.makeText(getBaseContext(), "Выберите международный рейс", Toast.LENGTH_SHORT).show();
            correct = false;
        }

        EditText ad = findViewById(R.id.AD);
        EditText ch = findViewById(R.id.CH);
        EditText inf = findViewById(R.id.IN);

        if (correct) {
            Intent intent = new Intent(this, Ticket.class);
            intent.putExtra("count1", countDEP);
            intent.putExtra("count2", countARR);
            intent.putExtra("date1", dateDEP);
            intent.putExtra("date2", dateARR);
            intent.putExtra("ad", ad.getText().toString());
            intent.putExtra("ch", ch.getText().toString());
            intent.putExtra("inf", inf.getText().toString());

            startActivity(intent);
        }
    }
}
