package com.example.android.bakinator.activities;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import adapters.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.FetchRecipesTaskCaller;
import interfaces.RecipeAdapterClickHandler;
import tasks.FetchRecipesTask;
import viewModels.RecipeViewModel;

public class MainActivity extends AppCompatActivity
                            implements FetchRecipesTaskCaller, RecipeAdapterClickHandler{

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecipeAdapter mRecipeAdapter;

    @BindView(R.id.rv_recipes) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message) TextView mErrorMessageTextView;
    @BindView(R.id.pb_loading_indicator) ProgressBar mProgressBar;

    private RecipeViewModel[] mRecipeViewModels = null;

    static final String STATE_RECIPE_VIEWMODELS = "recipe_viewmodels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "Butterknifing views");
        ButterKnife.bind(this);

        Log.d(LOG_TAG, "Setting up layout manager");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.requestLayout();
        mRecyclerView.setLayoutManager(layoutManager);

        Log.d(LOG_TAG, "Setting up RecipeAdapter");
        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        //Load recipe data
        if(savedInstanceState != null) {
            Log.d(LOG_TAG, "Recovering from previous state");
            mRecipeViewModels = Parcels.unwrap(savedInstanceState.getParcelable(STATE_RECIPE_VIEWMODELS));
            mRecipeAdapter.setRecipeData(mRecipeViewModels);
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

    public void onClick(RecipeViewModel recipeViewModel) {
        Context context = this;
        Log.d(LOG_TAG, "Clicked [" + recipeViewModel.Id + "] " + recipeViewModel.Name);
        //Start new detail activity
    }

    private void showRecipeDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessageTextView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void recipeDataRequestInitiated() {
        Log.d(LOG_TAG, "Recipe Request Initiated");
        showLoadingIndicator();
    }

    @Override
    public void receiveRecipeData(RecipeViewModel[] recipeViewModels) {
        Log.d(LOG_TAG, "Received " + recipeViewModels.length + " recipes");
        mRecipeViewModels = recipeViewModels;
        mRecipeAdapter.setRecipeData(mRecipeViewModels);
        showRecipeDataView();
    }

    @Override
    public void errorLoadingRecipeData() {
        Log.d(LOG_TAG, "Error loading recipe data");
        showErrorMessageTextView();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
