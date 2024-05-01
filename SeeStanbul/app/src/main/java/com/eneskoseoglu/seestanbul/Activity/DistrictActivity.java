package com.eneskoseoglu.seestanbul.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eneskoseoglu.seestanbul.Adapter.DistrictAdapter;
import com.eneskoseoglu.seestanbul.Helper.DatabaseHelper;
import com.eneskoseoglu.seestanbul.Model.District;
import com.eneskoseoglu.seestanbul.R;
import com.eneskoseoglu.seestanbul.databinding.ActivityDistrictBinding;

import java.io.IOException;
import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;

    private ActivityDistrictBinding binding;
    private ArrayList<District> districtArrayList;
    private DistrictAdapter districtAdapter;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDistrictBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        try {
            dbHelper = new DatabaseHelper(getApplicationContext());
            dbHelper.createDatabase();
            db = dbHelper.getReadableDatabase();
     /*       c = db.rawQuery("SELECT * from coordinates",null);
            while (c.moveToNext()){
                Log.d("deneme",c.getString(c.getColumnIndex("mahalle_adi")));
            }   */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        districtArrayList = new ArrayList<District>();

        addToList();

        districtAdapter = new DistrictAdapter(districtArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(DistrictActivity.this));
        binding.recyclerView.setAdapter(districtAdapter);

    }

    @SuppressLint("Range")
    public void addToList() {

        c = db.rawQuery("SELECT * from district", null);
        while (c.moveToNext()) {
            String ilce_adi = c.getString(c.getColumnIndex("ilce_adi"));
            String ilce_resim = c.getString(c.getColumnIndex("resim_kaynagi"));
            int resId = getResources().getIdentifier(
                    ilce_resim,
                    "drawable",
                    getPackageName()
            );

            districtArrayList.add(new District(toUpperCaseTurkish(ilce_adi), resId));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }

        });
        return true;
    }

    private void filter(String text) {

        ArrayList<District> filteredlist = new ArrayList<District>();

        for (District item : districtArrayList) {

            if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);

            }
        }

        if (filteredlist.isEmpty()) {

            Toast.makeText(this, "Böyle bir ilçe yok!", Toast.LENGTH_SHORT).show();

        } else {

            districtAdapter.filterList(filteredlist);
        }

    }

    public static String toUpperCaseTurkish(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case 'i':
                    result.append('İ'); // İ harfi
                    break;
                case 'ı':
                    result.append('I'); // I harfi
                    break;
                default:
                    result.append(Character.toUpperCase(c));
                    break;
            }
        }

        return result.toString();
    }


}
