package ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.RecipeInstructionNavigationClickHandler;
import interfaces.RecipeStepAdapterClickHandler;
import utilities.Constants;
import viewModels.RecipeStepViewModel;
import viewModels.RecipeViewModel;

/**
 * Created by Ian on 1/6/2018.
 */

public class RecipeInstructionFragment extends Fragment {

    private static final String LOG_TAG = RecipeInstructionFragment.class.getSimpleName();
    private static final String TAG = "RecipeInstructionFragment";

    private RecipeStepViewModel mRecipeStepViewModel = null;
    private RecipeInstructionNavigationClickHandler mRecipeInstructionNavigationClickHandler;

    private boolean mNavigationAllowed;
    private boolean mNextAvailable;
    private boolean mPrevAvailable;

    @BindView(R.id.tv_recipe_instructions) TextView mRecipeInstructionsTextView;
    @BindView(R.id.ll_instruction_navigation) LinearLayout mNavigationLinearLayout;
    @BindView(R.id.bt_next_instruction) Button mNavigationNextButton;
    @BindView(R.id.bt_previous_instruction) Button mNavigationPrevButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_instructions, container, false);
        rootView.setTag(TAG);

        ButterKnife.bind(this, rootView);

        Bundle argumentsBundle = savedInstanceState;
        if(argumentsBundle == null) {
            argumentsBundle = this.getArguments();
        }

        mRecipeStepViewModel = Parcels.unwrap(argumentsBundle.getParcelable(Constants.KEY_RECIPE_STEP_VIEWMODEL));
        mNavigationAllowed = argumentsBundle.getBoolean(Constants.KEY_RECIPE_STEP_NAVIGATION_ALLOWED);
        mNextAvailable = argumentsBundle.getBoolean(Constants.KEY_RECIPE_STEP_NEXT_AVAILABLE);
        mPrevAvailable = argumentsBundle.getBoolean(Constants.KEY_RECIPE_STEP_PREV_AVAILABLE);

        mRecipeInstructionsTextView.setText(mRecipeStepViewModel.FullDescription);
        if(mNavigationAllowed == false) {
            mNavigationLinearLayout.setVisibility(View.GONE);
        } else {

            if(mNextAvailable == true) {
                mNavigationNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mRecipeInstructionNavigationClickHandler != null) {
                            mRecipeInstructionNavigationClickHandler.onNextInstruction();
                        }
                    }
                });
            } else {
                mNavigationNextButton.setEnabled(false);
            }

            if(mPrevAvailable == true) {
                mNavigationPrevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mRecipeInstructionNavigationClickHandler != null) {
                            mRecipeInstructionNavigationClickHandler.onPrevInstruction();
                        }
                    }
                });
            } else {
                mNavigationPrevButton.setEnabled(false);
            }
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRecipeInstructionNavigationClickHandler = (RecipeInstructionNavigationClickHandler) context;
        } catch(Exception e) {}
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Putting parcelable in saved instance state");
        savedInstanceState.putParcelable(Constants.KEY_RECIPE_STEP_VIEWMODEL, Parcels.wrap(mRecipeStepViewModel));
        savedInstanceState.putBoolean(Constants.KEY_RECIPE_STEP_NAVIGATION_ALLOWED, mNavigationAllowed);
        savedInstanceState.putBoolean(Constants.KEY_RECIPE_STEP_NEXT_AVAILABLE, mNextAvailable);
        savedInstanceState.putBoolean(Constants.KEY_RECIPE_STEP_PREV_AVAILABLE, mPrevAvailable);
        super.onSaveInstanceState(savedInstanceState);
    }
}
