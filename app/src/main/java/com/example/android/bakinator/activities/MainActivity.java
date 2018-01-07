package com.example.android.bakinator.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import interfaces.RecipeAdapterClickHandler;
import utilities.Constants;
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
        Log.d(LOG_TAG, "Clicked [" + recipeViewModel.Id + "] " + recipeViewModel.Name);

        Log.d(LOG_TAG, "Putting recipe viewmodel in bundle");
        Bundle recipeDetailBundle = new Bundle();
        recipeDetailBundle.putParcelable(Constants.KEY_RECIPE_VIEWMODEL, Parcels.wrap(recipeViewModel));

        Log.d(LOG_TAG, "Building intent");
        final Intent recipeDetailIntent = new Intent(this, RecipeDetailActivity.class);
        recipeDetailIntent.putExtras(recipeDetailBundle);

        Log.d(LOG_TAG, "Launching intent");
        startActivity(recipeDetailIntent);
    }
}
