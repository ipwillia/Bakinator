package viewModels;

/**
 * Created by Ian on 12/27/2017.
 */

@org.parceler.Parcel
public class IngredientViewModel {

    double Quantity;
    String Measure;
    String Ingredient;

    public IngredientViewModel() {

    }

    public IngredientViewModel(double quantity, String measure, String ingredient) {
        Quantity = quantity;
        Measure = measure;
        Ingredient = ingredient;
    }



}
