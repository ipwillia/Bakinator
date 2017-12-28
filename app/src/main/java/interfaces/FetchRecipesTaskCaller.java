package interfaces;

import android.content.Context;

import viewModels.RecipeViewModel;

/**
 * Created by Ian on 12/27/2017.
 */

public interface FetchRecipesTaskCaller {
    public void recipeDataRequestInitiated();
    public void receiveRecipeData(RecipeViewModel[] recipeViewModels);
    public void errorLoadingRecipeData();
    public Context getContext();
}
