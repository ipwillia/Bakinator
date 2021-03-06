package ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android.bakinator.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import utilities.Constants;
import viewModels.RecipeViewModel;

/**
 * Created by Ian on 1/6/2018.
 */

public class RecipeIngredientFragment extends Fragment {

    private static final String LOG_TAG = RecipeRecyclerViewFragment.class.getSimpleName();
    private static final String TAG = "RecipeIngredientFragment";

    protected IngredientAdapter mIngredientAdapter;

    private RecipeViewModel mRecipeViewModel = null;

    @BindView(R.id.ll_ingredients) LinearLayout mIngredientLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        rootView.setTag(TAG);

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
            mIngredientAdapter = new IngredientAdapter(this.getContext(), mRecipeViewModel.IngredientViewModels);
            for (int i = 0; i < mRecipeViewModel.IngredientViewModels.length; i++) {
                View singleIngredientView = mIngredientAdapter.getView(i, null, null);
                mIngredientLinearLayout.addView(singleIngredientView);
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Putting parcelable in saved instance state");
        savedInstanceState.putParcelable(Constants.KEY_RECIPE_VIEWMODEL, Parcels.wrap(mRecipeViewModel));
        super.onSaveInstanceState(savedInstanceState);
    }
}
