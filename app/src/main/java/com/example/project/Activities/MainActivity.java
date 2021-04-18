package com.example.project.Activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.project.Adapters.GridAdapter;
import com.example.project.Models.ResultModel;
import com.example.project.R;
import com.example.project.Models.Character;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    ArrayList<Character> characterArrayList;
    GridView gvCharacterList;

    private int currentPage =1;
    private int totalPage ;

    boolean loading = true;
    GridAdapter gridAdapter;
    int myLastVisiblePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findComponents();
        getCharacter();

    }

    public void findComponents(){
        gvCharacterList = findViewById(R.id.gv_characterList);
    }

    public void getCharacter() {
        showProgress(this);
        Call<ResultModel<ArrayList<Character>>> call = BaseActivity.apiService.getCharacter();
        call.enqueue(new Callback<ResultModel<ArrayList<Character>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultModel<ArrayList<Character>>> call, @NonNull Response<ResultModel<ArrayList<Character>>> response) {
                hideProgress(loadingDialog);
                if (response.isSuccessful()) {
                    characterArrayList = response.body().getResult();
                    totalPage = response.body().getinfo().getPages();
                    populateGrid(characterArrayList);
                } else {
                    System.out.println("Response not succasful");
                    showAlert(MainActivity.this);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResultModel<ArrayList<Character>>> call, @NonNull Throwable t) {
                hideProgress(loadingDialog);
                showAlert(MainActivity.this);
                Log.d("failure","getCharacter failure");
            }
        });
    }

    public void getCharacterNext(int page) {
        showProgress(this);
        Call<ResultModel<ArrayList<Character>>> call = BaseActivity.apiService.getCharacter( page);
        call.enqueue(new Callback<ResultModel<ArrayList<Character>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultModel<ArrayList<Character>>> call, @NonNull Response<ResultModel<ArrayList<Character>>> response) {
                hideProgress(loadingDialog);
                if (response.isSuccessful()) {
                    System.out.println(response.body().getResult());
                    characterArrayList.addAll(response.body().getResult());// I prefer to add all next items to list they already downloaded rom api
                    gridAdapter.notifyDataSetChanged();

                } else {
                    System.out.println("Response not succasful");
                    showAlert(MainActivity.this);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResultModel<ArrayList<Character>>> call, @NonNull Throwable t) {
                Log.d("failure","getCharacter failure");
                hideProgress(loadingDialog);
                showAlert(MainActivity.this);
            }
        });
    }

    public void populateGrid(final ArrayList<Character> characterArrayList){

        gridAdapter = new GridAdapter(this, characterArrayList);
        gvCharacterList.setAdapter(gridAdapter);

        gvCharacterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, CharacterDetailActivity.class);
                i.putExtra("id",characterArrayList.get(position).getId());
                i.putExtra("name",characterArrayList.get(position).getName());
                i.putExtra("image",characterArrayList.get(position).getImage());
                i.putExtra("status",characterArrayList.get(position).getStatus());
                i.putExtra("species",characterArrayList.get(position).getSpecies());
                startAnotherActivity(MainActivity.this, CharacterDetailActivity.class, i);
                System.out.println("tıklanan "+characterArrayList.get(position).getName());
            }
        });

        myLastVisiblePos = gvCharacterList.getFirstVisiblePosition();
        gvCharacterList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int currentFirstVisPos = view.getFirstVisiblePosition();
                if(currentFirstVisPos > myLastVisiblePos) {
                    if (loading) {
                        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last item !");
                            currentPage+=1;
                            getCharacterNext(currentPage);
                            loading = true;
                        }
                    }
                }
                if(currentFirstVisPos < myLastVisiblePos) {
                    //scroll up
                }
                myLastVisiblePos = currentFirstVisPos;
            }
        });
    }
}