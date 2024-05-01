package com.eneskoseoglu.seestanbul.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.eneskoseoglu.seestanbul.Adapter.ParkAdapter;
import com.eneskoseoglu.seestanbul.Helper.DatabaseHelper;
import com.eneskoseoglu.seestanbul.Model.Park;
import com.eneskoseoglu.seestanbul.R;
import com.eneskoseoglu.seestanbul.databinding.ActivityParkBinding;

import java.io.IOException;
import java.util.ArrayList;

public class ParkActivity extends AppCompatActivity {

    private ActivityParkBinding binding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private ArrayList<Park> parkArrayList;
    private ParkAdapter parkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParkBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String district = intent.getStringExtra("district");
        parkArrayList = new ArrayList<>();

        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseHelper.createDatabase();
            sqLiteDatabase = databaseHelper.getReadableDatabase();

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM coordinates WHERE ilce = ?", new String[] {String.valueOf(district)});

            int nameInd = cursor.getColumnIndex("mahal_adi");
            int districtInd = cursor.getColumnIndex("ilce");
            int coordinateInd = cursor.getColumnIndex("koordinat");

            while(cursor.moveToNext()) {

                Park park = new Park(cursor.getString(nameInd),cursor.getString(districtInd),cursor.getString(coordinateInd));
                parkArrayList.add(park);

            }

            cursor.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        binding.recyclerParkView.setLayoutManager(new LinearLayoutManager(ParkActivity.this));
        parkAdapter = new ParkAdapter(parkArrayList);
        binding.recyclerParkView.setAdapter(parkAdapter);

    }

}