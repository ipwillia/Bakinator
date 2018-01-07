package ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.RecipeStepAdapterClickHandler;
import utilities.Constants;
import viewModels.RecipeViewModel;

/**
 * Created by Ian on 1/6/2018.
 */

public class RecipeStepFragment extends Fragment
                                    implements RecipeStepAdapterClickHandler {

    private static final String LOG_TAG = RecipeStepFragment.class.getSimpleName();
    private static final String TAG = "RecipeStepFragment";

    protected StepAdapter mStepAdapter;

    private RecipeViewModel mRecipeViewModel = null;
    private RecipeStepAdapterClickHandler mRecipeStepAdapterClickHandler;

    @BindView(R.id.ll_steps) LinearLayout mStepLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        rootView.setTag(TAG);

        Log.d(LOG_TAG, "Butterknifing views");
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey(Constants.KEY_RECIPE_VIEWMODEL)) {
                Log.d(LOG_TAG, "Getting view model from saved instance state");
                mRecipeViewModel = Parcels.unwrap(savedInstanceState.getParcelable(Constants.KEY_RECIPE_VIEWMODEL));
            } else {
                Log.e(LOG_TAG, "No view model in saved instance state");
            }
        } else {
            Bundle argumentsBundle = this.getArguments();
            mRecipeViewModel = Parcels.unwrap(argumentsBundle.getParcelable(Constants.KEY_RECIPE_VIEWMODEL));
        }

        if(mRecipeViewModel != null) {
            mStepAdapter = new StepAdapter(this.getContext(), mRecipeViewModel.RecipeStepViewModels);
            for (int i = 0; i < mRecipeViewModel.RecipeStepViewModels.length; i++) {
                final View singleStepView = mStepAdapter.getView(i, null, null);
                singleStepView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int stepIndex = mStepLinearLayout.indexOfChild(view);
                        onRecipeStepClick(stepIndex);
                    }
                });
                mStepLinearLayout.addView(singleStepView);
            }
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mRecipeStepAdapterClickHandler = (RecipeStepAdapterClickHandler) context;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Putting parcelable in saved instance state");
        savedInstanceState.putParcelable(Constants.KEY_RECIPE_VIEWMODEL, Parcels.wrap(mRecipeViewModel));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRecipeStepClick(int stepIndex) {
        Log.d(LOG_TAG, "Clicked [" + stepIndex + "] " + mRecipeViewModel.RecipeStepViewModels[stepIndex].ShortDescription);
    }
}
