package com.eneskoseoglu.seestanbul.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eneskoseoglu.seestanbul.Activity.ParkActivity;
import com.eneskoseoglu.seestanbul.Helper.DatabaseHelper;
import com.eneskoseoglu.seestanbul.Model.District;
import com.eneskoseoglu.seestanbul.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictHolder> {

    private ArrayList<District> districtArrayList;

    public DistrictAdapter(ArrayList<District> districtArrayList) {

        this.districtArrayList = districtArrayList;

    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<District> filterlist){
        // below line is to add our filtered
        // list in our course array list.
        districtArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DistrictHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DistrictHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.recyclerRowBinding.recyclerViewImageView.setImageResource(districtArrayList.get(position).getImage());
        holder.recyclerRowBinding.recyclerViewTextView.setText(districtArrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), ParkActivity.class);
                intent.putExtra("district",districtArrayList.get(position).getName());
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return districtArrayList.size();
    }

    public class DistrictHolder extends RecyclerView.ViewHolder {

        private RecyclerRowBinding recyclerRowBinding;

        public DistrictHolder(@NonNull RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }

}
