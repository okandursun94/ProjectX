package com.example.project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.project.Models.Character;

import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Character> {


    ArrayList<Character> characterArrayList;
    Context context;


    public GridAdapter(@NonNull Context context, ArrayList<Character> characterArrayList) {
        super(context, 0, characterArrayList);
        this.context = context;
        this.characterArrayList = characterArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view, parent, false);
        }
        Character character = getItem(position);
        TextView tvCharacterName = listitemView.findViewById(R.id.tv_characterName);
        TextView tvStatus = listitemView.findViewById(R.id.tv_status);
        TextView tvSpecies = listitemView.findViewById(R.id.tv_species);
        ImageView ivCharacter = listitemView.findViewById(R.id.img_character);


        System.out.println(character.getName());
        tvCharacterName.setText(character.getName());
        tvStatus.setText(character.getStatus());
        tvSpecies.setText(character.getSpecies());

        // Picasso is a library that is using for images
        final String url = character.getImage();

        Picasso.get()
                .load(url)
                .into(ivCharacter);

        return listitemView;
    }
}