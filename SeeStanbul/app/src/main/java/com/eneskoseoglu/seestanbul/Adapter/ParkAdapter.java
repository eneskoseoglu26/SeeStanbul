package com.eneskoseoglu.seestanbul.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eneskoseoglu.seestanbul.Activity.MapsActivity;
import com.eneskoseoglu.seestanbul.Activity.ParkActivity;
import com.eneskoseoglu.seestanbul.Model.Park;
import com.eneskoseoglu.seestanbul.databinding.RecyclerParkBinding;

import java.util.ArrayList;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ParkHolder> {

    private ArrayList<Park> parkArrayList;

    public ParkAdapter(ArrayList<Park> parkArrayList) {

        this.parkArrayList = parkArrayList;

    }

    @NonNull
    @Override
    public ParkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerParkBinding recyclerParkBinding = RecyclerParkBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ParkHolder(recyclerParkBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkHolder holder, @SuppressLint("RecyclerView") int position) {

        if(parkArrayList.isEmpty()) {

            holder.recyclerParkBinding.recyclerParkText.setText("Bu ilçede gösterilecek park verisi bulunamamıştır!");
            Toast.makeText(holder.recyclerParkBinding.recyclerParkText.getContext(), "Bu ilçede gösterilecek park verisi bulunamamıştır!", Toast.LENGTH_LONG).show();

        } else {

            holder.recyclerParkBinding.recyclerParkText.setText(parkArrayList.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String[] coordinateArray = parkArrayList.get(position).getCoordinate().split(",");

                    if (coordinateArray.length == 2) {
                        try {
                            double latitude = (Double.parseDouble(coordinateArray[0].trim()));
                            double longitude = (Double.parseDouble(coordinateArray[1].trim()));

                            Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                            intent.putExtra("lat",latitude);
                            intent.putExtra("long",longitude);
                            intent.putExtra("name",parkArrayList.get(position).getName());
                            holder.itemView.getContext().startActivity(intent);

                        } catch (NumberFormatException e) {
                            System.out.println("Koordinatları integer'a dönüştürme hatası: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Geçersiz koordinat formatı");
                    }

                }
            });

        }

    }

    @Override
    public int getItemCount() {

        // Eğer hiç park verisi yoksa arrayList.size 0 döneceği için uyarı mesajı veremeyecektik bundan dolayı mesajımızı göstermek için return 1 yaptık!
        if(parkArrayList.size() == 0) {

            return 1;

        }
        return parkArrayList.size();
    }

    public class ParkHolder extends RecyclerView.ViewHolder {

        RecyclerParkBinding recyclerParkBinding;

        public ParkHolder(@NonNull RecyclerParkBinding recyclerParkBinding) {
            super(recyclerParkBinding.getRoot());
            this.recyclerParkBinding = recyclerParkBinding;
        }
    }

}
