package com.educate.petsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.educate.petsapp.data.Pets;

import java.util.ArrayList;

public class PetsAdapter extends ArrayAdapter<Pets> {

    public PetsAdapter(Context context, ArrayList<Pets> pets){
        super(context, 0, pets);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pets pet = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(
                R.layout.item_pet, parent, false);

        TextView petName = convertView.findViewById(R.id.textview_pet_name);
        TextView petBreed = convertView.findViewById(R.id.textview_pet_breed);
        LinearLayout layoutItem = convertView.findViewById(R.id.layout_item);

        petName.setText(pet.name);
        petBreed.setText(pet.breed);

        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click item : " + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
