package ui;

/**
 * This class modified from the following android source document: https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.FetchRecipesTaskCaller;
import interfaces.RecipeAdapterClickHandler;
import tasks.FetchRecipesTask;
import utilities.Constants.Constants;
import viewModels.RecipeViewModel;


public class RecipeRecyclerViewFragment extends Fragment
        implements FetchRecipesTaskCaller, RecipeAdapterClickHandler {

    private static final String LOG_TAG = RecipeRecyclerViewFragment.class.getSimpleName();
    private static final String TAG = "RecipeRecyclerViewFragment";

    private static final int SPAN_COUNT = 3;

    protected RecipeAdapter mRecipeAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private RecipeViewModel[] mRecipeViewModels = null;
    private boolean mWideLayout = false;
    private RecipeAdapterClickHandler mRecipeAdapterClickHandler = null;

    @BindView(R.id.rv_recipes) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message) TextView mErrorMessageTextView;
    @BindView(R.id.pb_loading_indicator) ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        rootView.setTag(TAG);

        Log.d(LOG_TAG, "Butterknifing views");
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey(Constants.KEY_WIDE_LAYOUT)) {
                Log.d(LOG_TAG, "Getting wide layout from saved instance state");
                mWideLayout = (boolean) savedInstanceState.getSerializable(Constants.KEY_WIDE_LAYOUT);
            }

            if(savedInstanceState.containsKey(Constants.KEY_RECIPE_VIEWMODELS)) {
                Log.d(LOG_TAG, "Getting view models from saved instance state");
                mRecipeViewModels = Parcels.unwrap(savedInstanceState.getParcelable(Constants.KEY_RECIPE_VIEWMODELS));
            }
        }


        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        setRecyclerViewLayoutManager();

        if(mRecipeViewModels == null) {
            loadRecipeData();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mRecipeAdapterClickHandler = (RecipeAdapterClickHandler) context;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        if(mWideLayout) {
            mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        } else {
            mLayoutManager = new LinearLayoutManager(getActivity());
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(Constants.KEY_WIDE_LAYOUT, mWideLayout);
        savedInstanceState.putParcelable(Constants.KEY_RECIPE_VIEWMODELS, Parcels.wrap(mRecipeViewModels));
        super.onSaveInstanceState(savedInstanceState);
    }

    private void loadRecipeData() {
        new FetchRecipesTask(this).execute();
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
        return getActivity();
    }

    @Override
    public void onRecipeClick(RecipeViewModel recipeViewModel) {
        mRecipeAdapterClickHandler.onRecipeClick(recipeViewModel);
    }
}
