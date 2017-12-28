package utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import viewModels.IngredientViewModel;
import viewModels.RecipeStepViewModel;
import viewModels.RecipeViewModel;

/**
 * Created by Ian on 12/27/2017.
 */

public class RecipeJSONUtilities {

    private static final String LOG_TAG = RecipeJSONUtilities.class.getSimpleName();

    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";

    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_MEASURE = "measure";
    private static final String INGREDIENT_NAME = "ingredient";

    private static final String STEP_ID = "id";
    private static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEP_FULL_DESCRIPTION = "description";
    private static final String STEP_VIDEO_URL = "videoURL";
    private static final String STEP_THUMBNAIL_URL = "thumbnailURL";

    public static RecipeViewModel[] getRecipeViewModelsFromJson(String recipeJsonString)
            throws JSONException {
        RecipeViewModel[] recipeViewModels = null;

        Log.d(LOG_TAG, "Getting recipe JSON object array");
        JSONArray recipeJSONArray = new JSONArray(recipeJsonString);

        int recipeCount = recipeJSONArray.length();
        Log.d(LOG_TAG, "Found " + recipeCount + " recipes");

        recipeViewModels = new RecipeViewModel[recipeJSONArray.length()];

        for(int i = 0; i < recipeCount; i++) {
            JSONObject singleRecipeJSON = recipeJSONArray.getJSONObject(i);

            String recipeId = singleRecipeJSON.getString(RECIPE_ID);
            String recipeName = singleRecipeJSON.getString(RECIPE_NAME);
            double recipeServings = singleRecipeJSON.getDouble(RECIPE_SERVINGS);
            String recipeImage = singleRecipeJSON.getString(RECIPE_IMAGE);

            JSONArray ingredientJSONArray = singleRecipeJSON.getJSONArray(RECIPE_INGREDIENTS);
            IngredientViewModel[] ingredientViewModels = getIngredientViewModelsFromJson(ingredientJSONArray);

            JSONArray stepJSONArray = singleRecipeJSON.getJSONArray(RECIPE_STEPS);
            RecipeStepViewModel[] recipeStepViewModels = getRecipeStepViewModelsFromJson(stepJSONArray);

            RecipeViewModel singleRecipeViewModel = new RecipeViewModel(recipeId, recipeName, recipeServings, recipeImage, ingredientViewModels, recipeStepViewModels);

            recipeViewModels[i] = singleRecipeViewModel;
        }

        return recipeViewModels;
    }

    public static IngredientViewModel[] getIngredientViewModelsFromJson(JSONArray ingredientJSONArray)
            throws JSONException {
        IngredientViewModel[] ingredientViewModels = null;

        int ingredientCount = ingredientJSONArray.length();
        Log.d(LOG_TAG, "Found " + ingredientCount + " ingredients");

        ingredientViewModels = new IngredientViewModel[ingredientCount];

        for(int i = 0; i < ingredientCount; i++) {
            JSONObject singleIngredientJSON = ingredientJSONArray.getJSONObject(i);

            double ingredientQuantity = singleIngredientJSON.getDouble(INGREDIENT_QUANTITY);
            String ingredientMeasure = singleIngredientJSON.getString(INGREDIENT_MEASURE);
            String ingredientName = singleIngredientJSON.getString(INGREDIENT_NAME);

            IngredientViewModel singleIngredientViewModel = new IngredientViewModel(ingredientQuantity, ingredientMeasure, ingredientName);
            ingredientViewModels[i] = singleIngredientViewModel;
        }

        return ingredientViewModels;
    }

    public static RecipeStepViewModel[] getRecipeStepViewModelsFromJson(JSONArray stepJSONArray)
            throws JSONException {
        RecipeStepViewModel[] recipeStepViewModels = null;

        int stepCount = stepJSONArray.length();
        Log.d(LOG_TAG, "Found " + stepCount + " steps");

        recipeStepViewModels = new RecipeStepViewModel[stepCount];

        for(int i = 0; i < stepCount; i++) {
            JSONObject singleRecipeStepJSON = stepJSONArray.getJSONObject(i);

            String stepId = singleRecipeStepJSON.getString(STEP_ID);
            String stepShortDescription = singleRecipeStepJSON.getString(STEP_SHORT_DESCRIPTION);
            String stepFullDescription = singleRecipeStepJSON.getString(STEP_FULL_DESCRIPTION);
            String stepVideoURL = singleRecipeStepJSON.getString(STEP_VIDEO_URL);
            String stepThumbnailURL = singleRecipeStepJSON.getString(STEP_THUMBNAIL_URL);

            RecipeStepViewModel singleRecipeStepViewModel = new RecipeStepViewModel(stepId, stepShortDescription, stepFullDescription, stepVideoURL, stepThumbnailURL);
            recipeStepViewModels[i] = singleRecipeStepViewModel;
        }

        return recipeStepViewModels;
    }

}
