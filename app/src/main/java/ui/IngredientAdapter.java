package ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.bakinator.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewModels.IngredientViewModel;

/**
 * Created by Ian on 1/6/2018.
 */

public class IngredientAdapter extends ArrayAdapter<IngredientViewModel> {

    @BindView(R.id.tv_ingredient_name) TextView ingredientNameTextView;
    @BindView(R.id.tv_ingredient_quantity_measure) TextView ingredientQuantityMeasureTextView;

    public IngredientAdapter(Context context, IngredientViewModel[] ingredientViewModels) {
        super(context, 0, ingredientViewModels);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        IngredientViewModel ingredientViewModel = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_detail, parent, false);
        }

        ButterKnife.bind(this, convertView);
        ingredientNameTextView.setText(ingredientViewModel.Ingredient);
        ingredientQuantityMeasureTextView.setText(ingredientViewModel.getQuantityMeasureString());

        return convertView;
    }
}
