package viewModels;

import org.parceler.Parcel;

/**
 * Created by Ian on 12/27/2017.
 */

@Parcel
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
