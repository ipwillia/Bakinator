package viewModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ian on 12/27/2017.
 */

@org.parceler.Parcel
public class RecipeViewModel {

    public String Id;
    public String Name;
    public double Servings;
    public String Image;

    public IngredientViewModel[] IngredientViewModels;
    public RecipeStepViewModel[] RecipeStepViewModels;

    public RecipeViewModel() {

    }

    public RecipeViewModel(String id, String name, double servings, String image, IngredientViewModel[] ingredientViewModels, RecipeStepViewModel[] recipeStepViewModels) {
        Id = id;
        Name = name;
        Servings = servings;
        Image = image;
        IngredientViewModels = ingredientViewModels;
        RecipeStepViewModels = recipeStepViewModels;
    }
}
