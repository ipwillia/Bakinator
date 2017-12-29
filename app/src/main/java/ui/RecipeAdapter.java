package ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakinator.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.RecipeAdapterClickHandler;
import viewModels.RecipeViewModel;

/**
 * Created by Ian on 12/28/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private RecipeViewModel[] mRecipeViewModels = new RecipeViewModel[0];

    private final RecipeAdapterClickHandler mRecipeAdapterClickHandler;

    public RecipeAdapter(RecipeAdapterClickHandler recipeAdapterClickHandler) {
        mRecipeAdapterClickHandler = recipeAdapterClickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name) TextView recipeNameTextView;
        @BindView(R.id.iv_recipe) ImageView recipeImageView;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            RecipeViewModel singleRecipeViewModel = mRecipeViewModels[adapterPosition];
            mRecipeAdapterClickHandler.onRecipeClick(singleRecipeViewModel);
        }

    }

    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int listItemLayoutID = R.layout.recipe_list_detail;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(listItemLayoutID, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    public void onBindViewHolder(RecipeAdapterViewHolder recipeAdapterViewHolder, int position) {
        Context context = recipeAdapterViewHolder.itemView.getContext();
        RecipeViewModel singleRecipeViewModel = mRecipeViewModels[position];

        //Load image
        String recipeImageURL = singleRecipeViewModel.Image;
        if(recipeImageURL != null &&
                recipeImageURL.length() > 0) {
            Log.d(LOG_TAG, recipeImageURL);


            //Would have preferred to use placeholder images for loading and error, but the json did not include any reference images
            Picasso.with(context)
                    .load(recipeImageURL)
                    .into(recipeAdapterViewHolder.recipeImageView);
        }

        //Assign text
        recipeAdapterViewHolder.recipeNameTextView.setText(singleRecipeViewModel.Name);
    }

    public int getItemCount() {
        int itemCount = 0;
        if(mRecipeViewModels != null) {
            itemCount = mRecipeViewModels.length;
        }
        return itemCount;
    }

    public void setRecipeData(RecipeViewModel[] recipeViewModels) {
        mRecipeViewModels = recipeViewModels;
        notifyDataSetChanged();
    }

}
