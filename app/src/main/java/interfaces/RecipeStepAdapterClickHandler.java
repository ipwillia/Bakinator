package interfaces;

import viewModels.RecipeViewModel;

/**
 * Created by Ian on 12/28/2017.
 */

public interface RecipeStepAdapterClickHandler {
    void onRecipeStepClick(RecipeViewModel recipeViewModel, int stepIndex);
}
