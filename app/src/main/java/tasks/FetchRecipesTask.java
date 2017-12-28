package tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.bakinator.R;

import java.net.URL;

import interfaces.FetchRecipesTaskCaller;
import utilities.NetworkUtilities;
import utilities.RecipeJSONUtilities;
import viewModels.RecipeViewModel;

/**
 * Created by Ian on 12/27/2017.
 */

public class FetchRecipesTask extends AsyncTask<Void, Void, RecipeViewModel[]> {

    private final String LOG_TAG = FetchRecipesTask.class.getSimpleName();

    FetchRecipesTaskCaller mFetchRecipesTaskCaller;

    public FetchRecipesTask(FetchRecipesTaskCaller fetchRecipesTaskCaller) {
        mFetchRecipesTaskCaller = fetchRecipesTaskCaller;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        mFetchRecipesTaskCaller.recipeDataRequestInitiated();
    }

    @Override
    protected RecipeViewModel[] doInBackground(Void... voids) {

        RecipeViewModel[] recipeViewModels = null;

        String recipeSource = mFetchRecipesTaskCaller.getContext().getString(R.string.recipe_json_url);
        Log.d(LOG_TAG, "Recipe Source: " + recipeSource);

        try {
            URL recipeSourceURL = new URL(Uri.parse(recipeSource).toString());
            Log.d(LOG_TAG, "Recipe URL: " + recipeSourceURL.toString());

            String recipeJSONString = NetworkUtilities.getResponseFromHttpUrl(recipeSourceURL);
            Log.d(LOG_TAG, "Recipe JSON: " + recipeJSONString);

            recipeViewModels = RecipeJSONUtilities.getRecipeViewModelsFromJson(recipeJSONString);
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "Forming URL " + e.toString());
        }

        return recipeViewModels;
    }

    @Override
    protected void onPostExecute(RecipeViewModel[] recipeViewModels) {
        if(recipeViewModels != null) {
            mFetchRecipesTaskCaller.receiveRecipeData(recipeViewModels);
        } else {
            mFetchRecipesTaskCaller.errorLoadingRecipeData();
        }
    }
}
