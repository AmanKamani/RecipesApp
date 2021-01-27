package jb.prodution.recipesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import jb.prodution.recipesapp.R;
import jb.prodution.recipesapp.models.Recipe;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter( OnRecipeListener mOnRecipeListener) {
        this.mOnRecipeListener = mOnRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);

        return new RecipeViewHolder(view, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(options)
                .load(mRecipes.get(position).getImage())
                .into(((RecipeViewHolder)holder).image);

        RecipeViewHolder recipeViewHolder = (RecipeViewHolder)holder;
        recipeViewHolder.title.setText(mRecipes.get(position).getTitle());
        recipeViewHolder.source.setText(mRecipes.get(position).getSourceName());
        recipeViewHolder.time.setText(String.format("%d min", mRecipes.get(position).getPrepareTime()));
        recipeViewHolder.price.setText(String.valueOf(mRecipes.get(position).getPricePerServing()));
        recipeViewHolder.people.setText(String.valueOf(mRecipes.get(position).getServings()));
    }

    @Override
    public int getItemCount() {
        if(mRecipes != null)
            return mRecipes.size();
        return 0;
    }

    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}
