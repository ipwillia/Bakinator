package viewModels;

/**
 * Created by Ian on 12/27/2017.
 */

public class IngredientViewModel {

    int Quantity;
    String Measure;
    String Ingredient;

    public IngredientViewModel() {

    }

    public IngredientViewModel(int quantity, String measure, String ingredient) {
        Quantity = quantity;
        Measure = measure;
        Ingredient = ingredient;
    }



}
