package com.example.android.bakinator.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakinator.R;

import interfaces.RecipeAdapterClickHandler;
import viewModels.RecipeViewModel;

public class MainActivity extends AppCompatActivity
                            implements RecipeAdapterClickHandler{

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean mWideLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWideLayout = getResources().getBoolean(R.bool.wide_layout);
        if(mWideLayout) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        setContentView(R.layout.activity_main);
    }

    public void onRecipeClick(RecipeViewModel recipeViewModel) {
        Context context = this;
        Log.d(LOG_TAG, "Clicked [" + recipeViewModel.Id + "] " + recipeViewModel.Name);
        //Start new detail activity
    }
}
