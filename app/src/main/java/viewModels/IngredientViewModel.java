package viewModels;

import org.parceler.Parcel;

import java.text.DecimalFormat;

/**
 * Created by Ian on 12/27/2017.
 */

@Parcel
public class IngredientViewModel {

    public double Quantity;
    public String Measure;
    public String Ingredient;

    public IngredientViewModel() {

    }

    public IngredientViewModel(double quantity, String measure, String ingredient) {
        Quantity = quantity;
        Measure = measure;
        Ingredient = ingredient;
    }

    public String getQuantityMeasureString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        return decimalFormat.format(Quantity) + " " + Measure;
    }

}
