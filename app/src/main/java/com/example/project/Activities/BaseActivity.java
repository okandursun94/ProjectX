package com.example.project.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.ApiClient;
import com.example.project.ApiInterface;
import com.example.project.R;

public class BaseActivity extends AppCompatActivity {
    public static Dialog loadingDialog = null;
    public static ApiInterface apiService = null;

    public static AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = ApiClient.getRetrofitSerialize().create(ApiInterface.class);
    }

    public void startAnotherActivity(Context contextClass, Class goToClass, Intent intent) {
        startActivity(intent);
    }

    public static Dialog showProgress(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_panel);
        loadingDialog.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loadingDialog.show();
        return loadingDialog;
    }

    public static void hideProgress(Dialog loadingDialog){
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public static void showAlert(Context context){
        alertDialog = new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("An error occured")
                .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                })
                .show();
    }

}