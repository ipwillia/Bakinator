package com.example.android.bakinator.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import interfaces.RecipeInstructionNavigationClickHandler;
import interfaces.RecipeStepAdapterClickHandler;
import ui.RecipeIngredientFragment;
import ui.RecipeInstructionFragment;
import ui.RecipeStepFragment;
import utilities.Constants;
import viewModels.RecipeStepViewModel;
import viewModels.RecipeViewModel;

public class RecipeInstructionActivity extends AppCompatActivity
                                        implements RecipeInstructionNavigationClickHandler {

    private final String LOG_TAG = RecipeInstructionActivity.class.getSimpleName();
    private RecipeViewModel mRecipeViewModel = null;
    private int mRecipeStepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_instruction);

        //Get Recipe View Model
        Bundle argumentsBundle = getIntent().getExtras();
        mRecipeViewModel = Parcels.unwrap(argumentsBundle.getParcelable(Constants.KEY_RECIPE_VIEWMODEL));
        mRecipeStepIndex = argumentsBundle.getInt(Constants.KEY_RECIPE_INSTRUCTION_INDEX);

        //Set fragment
        LoadInstructionFragment(mRecipeStepIndex);
    }

    private void LoadInstructionFragment(int stepIndex) {
        Boolean navigationAllowed = true;
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

    @Override
    public void onNextInstruction() {
        mRecipeStepIndex++;
        LoadInstructionFragment(mRecipeStepIndex);
    }

    @Override
    public void onPrevInstruction() {
        mRecipeStepIndex--;
        LoadInstructionFragment(mRecipeStepIndex);
    }
}
