package viewModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ian on 12/27/2017.
 */

@org.parceler.Parcel
public class RecipeViewModel {

    public int Id;
    public String Name;
    public List<IngredientViewModel> IngredientViewModels;
    public List<RecipeStepViewModel> RecipeStepViewModels;
    public int Servings;
    public String Image;

    public RecipeViewModel() {

    }

    public RecipeViewModel(int id, String name, List<IngredientViewModel> ingredientViewModels, List<RecipeStepViewModel> recipeStepViewModels, int servings, String image) {
        Id = id;
        Name = name;
        IngredientViewModels = ingredientViewModels;
        RecipeStepViewModels = recipeStepViewModels;
        Servings = servings;
        Image = image;
    }
}
