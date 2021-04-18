package com.example.project.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel<T> {

    @SerializedName("info")
    @Expose
    private Info info = null;

    public Info getinfo() {
        return info;
    }

    @SerializedName("results")
    @Expose
    private T result = null;

    public T getResult() {
        return result;
    }

}
