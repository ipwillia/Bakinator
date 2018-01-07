package com.example.android.bakinator.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import ui.RecipeInstructionFragment;
import ui.RecipeStepFragment;
import utilities.Constants;
import viewModels.RecipeViewModel;

public class RecipeDetailActivity extends AppCompatActivity
                                    implements RecipeStepAdapterClickHandler {

    private final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
    private boolean mWideLayout = false;
    private RecipeViewModel mRecipeViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get wide layout status
        mWideLayout = getResources().getBoolean(R.bool.wide_layout);
        if(mWideLayout) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        setContentView(R.layout.activity_recipe_detail);

        //Get Recipe View Model
        Bundle argumentsBundle = getIntent().getExtras();
        mRecipeViewModel = Parcels.unwrap(argumentsBundle.getParcelable(Constants.KEY_RECIPE_VIEWMODEL));

        //Set fragments
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

        //Special actions for wide layout?
        if(mWideLayout) {
            if(mRecipeViewModel.RecipeStepViewModels != null &&
                    mRecipeViewModel.RecipeStepViewModels.length > 0) {
                LoadInstructionFragment(0);
            }
        } else {

        }
    }

    public void LaunchRecipeInstructionActivity(int stepIndex) {
        Bundle recipeInstructionBundle = new Bundle();
        recipeInstructionBundle.putParcelable(Constants.KEY_RECIPE_VIEWMODEL, Parcels.wrap(mRecipeViewModel));
        recipeInstructionBundle.putInt(Constants.KEY_RECIPE_INSTRUCTION_INDEX, stepIndex);

        Log.d(LOG_TAG, "Building intent");
        final Intent recipeInstructionIntent = new Intent(this, RecipeInstructionActivity.class);
        recipeInstructionIntent.putExtras(recipeInstructionBundle);

        Log.d(LOG_TAG, "Launching intent");
        startActivity(recipeInstructionIntent);
    }

    public void onRecipeStepClick(int stepIndex) {

        Log.d(LOG_TAG, "Clicked [" + stepIndex + "] " + mRecipeViewModel.RecipeStepViewModels[stepIndex]);
        if(mWideLayout) {
            LoadInstructionFragment(stepIndex);
        } else {
            LaunchRecipeInstructionActivity(stepIndex);
        }
    }

    private void LoadInstructionFragment(int stepIndex) {
        Boolean navigationAllowed = (!mWideLayout);
        Boolean nextAvailable = (stepIndex != mRecipeViewModel.RecipeStepViewModels.length - 1);
        Boolean prevAvailable = (stepIndex != 0);

        Bundle instructionBundle = new Bundle();
        instructionBundle.putParcelable(Constants.KEY_RECIPE_STEP_VIEWMODEL, Parcels.wrap(mRecipeViewModel.RecipeStepViewModels[stepIndex]));
        instructionBundle.putBoolean(Constants.KEY_RECIPE_STEP_NAVIGATION_ALLOWED, navigationAllowed);
        instructionBundle.putBoolean(Constants.KEY_RECIPE_STEP_NEXT_AVAILABLE, nextAvailable);
        instructionBundle.putBoolean(Constants.KEY_RECIPE_STEP_PREV_AVAILABLE, prevAvailable);

        RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
        recipeInstructionFragment.setArguments(instructionBundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_instruction_container, recipeInstructionFragment)
                .commit();
    }

}
