package com.example.project.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapters.EpisodesAdapter;
import com.example.project.Models.Character;

import com.example.project.Models.Episode;
import com.example.project.R;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends  BaseActivity {
    Character selectedCharacter ;
    RecyclerView rvEpisodeList;
    LinearLayoutManager layoutManager;
    TextView tvCharacterName, tvStatus, tvSpecies;
    ImageView ivCharacter;
    TextInputLayout edtNumberOfEpisodes, edtGender;
    Button btnEpisodes;
    Button btn;
    Boolean isGrid;
    Boolean expansion = false;

    ArrayList<Integer> episodeIds= new ArrayList<Integer>();
    ArrayList<Episode> episodeArrayList= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_detail_activity);

        findComponents();
        setListener();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {//The key argument here must match that used in the other activity
            int id = extras.getInt("id");
            getCharacterById(id);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CharacterDetailActivity.this, MainActivity.class);
        i.putExtra("isGrid",isGrid);
        startAnotherActivity(CharacterDetailActivity.this, MainActivity.class, i);
    }

    public void findComponents(){
        tvCharacterName = findViewById(R.id.tv_characterName);
        tvStatus = findViewById(R.id.tv_status);
        tvSpecies = findViewById(R.id.tv_species);
        ivCharacter = findViewById(R.id.img_character);
        edtNumberOfEpisodes = findViewById(R.id.num_episodes);
        edtGender = findViewById(R.id.gender);
        btnEpisodes = findViewById(R.id.btn_episodes);
        rvEpisodeList = findViewById(R.id.rv_episodeList);
    }

    public void setListener(){

        btnEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expansion) {
                    rvEpisodeList.setVisibility(View.VISIBLE);
                    Drawable drawable = ContextCompat.getDrawable(CharacterDetailActivity.this, R.drawable.ic_baseline_expand_less_24);
                    drawable = DrawableCompat.wrap(drawable);
                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    btnEpisodes.setCompoundDrawables(null, null, drawable, null);

                    expansion = true;
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(CharacterDetailActivity.this, R.drawable.ic_baseline_expand_more_24);
                    drawable = DrawableCompat.wrap(drawable);
                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    btnEpisodes.setCompoundDrawables(null, null, drawable, null);

                    rvEpisodeList.setVisibility(View.INVISIBLE);
                    expansion = false;
                }
            }
        });
    }

    void setData() {
        this.tvCharacterName.setText(selectedCharacter.getName());
        this.tvStatus.setText(selectedCharacter.getStatus());
        this.tvSpecies.setText(selectedCharacter.getSpecies());

        // Picasso is a library that is using for images
        final String url = selectedCharacter.getImage();
        Picasso.get()
                .load(url)
                .into(ivCharacter);

        edtNumberOfEpisodes.getEditText().setText(String.valueOf(selectedCharacter.getEpisode().size()));
        edtGender.getEditText().setText(selectedCharacter.getGender());

        getEpisodesids();

    }

    public void getCharacterById(int id) {
        showProgress(this);
        Call<Character> call = BaseActivity.apiService.getCharacterById(id);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(@NonNull Call<Character> call, @NonNull Response<Character> response) {
                hideProgress(loadingDialog);
                if (response.isSuccessful()) {
                    selectedCharacter = response.body();// I prefer to add all next items to list they already downloaded rom api

                    //getEpisode(selectedCharacter.getEpisode().get(selectedCharacter.getEpisode().size()-1));
                    setData();
                } else {
                    System.out.println("Response not succasful");
                    showAlert(CharacterDetailActivity.this);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Character> call, @NonNull Throwable t) {
                hideProgress(loadingDialog);
                showAlert(CharacterDetailActivity.this);
                Log.d("failure","getCharacterById failure");
            }
        });
    }

    public void populateEpisodes(){
        RecyclerView recyclerView = findViewById(R.id.rv_episodeList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        EpisodesAdapter adapter = new EpisodesAdapter(this, episodeArrayList);

        recyclerView.setAdapter(adapter);
    }

    public void getEpisodesids(){
        for(int i=0;i<selectedCharacter.getEpisode().size();i++){
            String parts[] = selectedCharacter.getEpisode().get(i).split("/");
            String dir1 = parts[parts.length-1];
            episodeIds.add(Integer.parseInt(dir1));
        }
        getEpisode();
    }

    public void getEpisode() {
        String url ="https://rickandmortyapi.com/api/episode/"+episodeIds.toString().replace(" ", "");
        showProgress(this);
        Call<ArrayList<Episode>> call = BaseActivity.apiService.getEpisode(url);
        call.enqueue(new Callback<ArrayList<Episode>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Episode>> call, @NonNull Response<ArrayList<Episode>> response) {
                hideProgress(loadingDialog);
                if (response.isSuccessful()) {
                    episodeArrayList=response.body();// I prefer to add all next items to list they already downloaded rom api
                    populateEpisodes();

                } else {
                    System.out.println("Response not succasful");
                    showAlert(CharacterDetailActivity.this);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Episode>> call, @NonNull Throwable t) {
                Log.d("failure","getCharacter failure");
                hideProgress(loadingDialog);
                showAlert(CharacterDetailActivity.this);
            }
        });
    }


}

