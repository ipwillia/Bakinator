package ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakinator.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewModels.IngredientViewModel;
import viewModels.RecipeStepViewModel;

/**
 * Created by Ian on 1/6/2018.
 */

public class StepAdapter extends ArrayAdapter<RecipeStepViewModel> {

    @BindView(R.id.iv_step_thumbnail) ImageView stepThumbnailImageView;
    @BindView(R.id.tv_step_short_description) TextView stepShortDescriptionTextView;

    public StepAdapter(Context context, RecipeStepViewModel[] recipeStepViewModels) {
        super(context, 0, recipeStepViewModels);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RecipeStepViewModel recipeStepViewModel = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_list_detail, parent, false);
        }

        ButterKnife.bind(this, convertView);

        if(recipeStepViewModel.ThumbnailUrl != null &&
           recipeStepViewModel.ThumbnailUrl.length() > 0) {
            Picasso.with(this.getContext())
                    .load(recipeStepViewModel.ThumbnailUrl)
                    .into(stepThumbnailImageView);
        } else {
            stepThumbnailImageView.setVisibility(View.INVISIBLE);
        }

        stepShortDescriptionTextView.setText(recipeStepViewModel.ShortDescription);

        return convertView;
    }
}
