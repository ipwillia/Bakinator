package com.example.android.bakinator.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import interfaces.RecipeStepAdapterClickHandler;
import ui.RecipeIngredientFragment;
import ui.RecipeStepFragment;
import utilities.Constants;
import viewModels.RecipeViewModel;

public class RecipeDetailActivity extends AppCompatActivity
                                    implements RecipeStepAdapterClickHandler {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean mWideLayout = false;
    private RecipeViewModel mRecipeViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get wide layout status
        mWideLayout = getResources().getBoolean(R.bool.wide_layout);
        setContentView(R.layout.activity_recipe_detail);

        //Get Recipe View Model
        Bundle argumentsBundle = getIntent().getExtras();
        mRecipeViewModel = Parcels.unwrap(argumentsBundle.getParcelable(Constants.KEY_RECIPE_VIEWMODEL));

        if(mWideLayout) {

        } else {
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putParcelable(Constants.KEY_RECIPE_VIEWMODEL, Parcels.wrap(mRecipeViewModel));

            RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
            recipeIngredientFragment.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_ingredient_container, recipeIngredientFragment)
                    .commit();

            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_container, recipeStepFragment)
                    .commit();
        }
    }

    public void onRecipeStepClick(RecipeViewModel recipeViewModel, int stepIndex) {
        Context context = this;
        Log.d(LOG_TAG, "Clicked [" + stepIndex + "] " + recipeViewModel.RecipeStepViewModels[stepIndex]);


    }

}
