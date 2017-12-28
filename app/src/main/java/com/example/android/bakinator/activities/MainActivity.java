package com.example.android.bakinator.activities;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import interfaces.FetchRecipesTaskCaller;
import tasks.FetchRecipesTask;
import viewModels.RecipeViewModel;

public class MainActivity extends AppCompatActivity
                            implements FetchRecipesTaskCaller{

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecipeViewModel[] mRecipeViewModels = null;

    static final String STATE_RECIPE_VIEWMODELS = "recipe_viewmodels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load recipe data
        if(savedInstanceState != null) {
            Log.d(LOG_TAG, "Recovering from previous state");
            mRecipeViewModels = Parcels.unwrap(savedInstanceState.getParcelable(STATE_RECIPE_VIEWMODELS));
        } else {
            Log.d(LOG_TAG, "Loading fresh recipe data");
            loadRecipeData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        Log.d(LOG_TAG, "Putting recipe view models in bundle");
        outState.putParcelable(STATE_RECIPE_VIEWMODELS, Parcels.wrap(mRecipeViewModels));
    }

    private void loadRecipeData() {
        new FetchRecipesTask(this).execute();
    }

    @Override
    public void recipeDataRequestInitiated() {
        Log.d(LOG_TAG, "Recipe Request Initiated");
    }

    @Override
    public void receiveRecipeData(RecipeViewModel[] recipeViewModels) {
        Log.d(LOG_TAG, "Received " + recipeViewModels.length + " recipes");
    }

    @Override
    public void errorLoadingRecipeData() {
        Log.d(LOG_TAG, "Error loading recipe data");
    }

    @Override
    public Context getContext() {
        return this;
    }
}
