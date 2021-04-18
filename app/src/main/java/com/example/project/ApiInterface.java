package com.example.project;

import com.example.project.Models.Episode;
import com.example.project.Models.ResultModel;
import com.example.project.Models.Character;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("character")
    Call<ResultModel<ArrayList<Character>>> getCharacter();

    @GET("character")
    Call<ResultModel<ArrayList<Character>>> getCharacter(
            @Query("page") int page);

    @GET("character/{id}")
    Call<Character> getCharacterById(
            @Path("id") int id);

    @GET
    Call<ArrayList<Episode>> getEpisode(@Url String url);
}