package jb.prodution.recipesapp.adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jb.prodution.recipesapp.R;
import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.util.Constants;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private static final String LOADING_STRING = "LOADING...";
    private static final String EXHAUSTED_STRING = "EXHAUSTED...";

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter( OnRecipeListener mOnRecipeListener) {
        this.mOnRecipeListener = mOnRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case RECIPE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            case LOADING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item,parent, false);
                return new LoadingViewHolder(view);
            case CATEGORY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, mOnRecipeListener);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if(itemViewType == RECIPE_TYPE) {

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(options)
                    .load(mRecipes.get(position).getImage())
                    .into(((RecipeViewHolder) holder).image);

            RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;
            recipeViewHolder.title.setText(mRecipes.get(position).getTitle());
            recipeViewHolder.source.setText(mRecipes.get(position).getSourceName());
            recipeViewHolder.time.setText(String.format("%d min", mRecipes.get(position).getPrepareTime()));
            recipeViewHolder.price.setText(String.valueOf(mRecipes.get(position).getPricePerServing()));
            recipeViewHolder.people.setText(String.valueOf(mRecipes.get(position).getServings()));
        }
        else if(itemViewType == CATEGORY_TYPE){
            CategoryViewHolder categoryViewHolder = (CategoryViewHolder)holder;

            Uri path = Uri.parse("android.resource://"+holder.itemView.getContext().getPackageName()+"/drawable/"+mRecipes.get(position).getImage());

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(options)
                    .load(path)
                    .into(categoryViewHolder.image);

            categoryViewHolder.title.setText(mRecipes.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mRecipes.get(position).getPrepareTime() == -1)
            return CATEGORY_TYPE;
        else if(mRecipes.get(position).getTitle().equals(LOADING_STRING))
            return LOADING_TYPE;
        else if(position == mRecipes.size() -1
                && position != 0
                && !mRecipes.get(position).getTitle().equals(EXHAUSTED_STRING))
            return LOADING_TYPE;
        else
            return RECIPE_TYPE;
    }

    public void displayLoading(){
        if(!isLoading()){
           Recipe recipe = new Recipe();
           recipe.setTitle(LOADING_STRING);
           mRecipes = new ArrayList<>();
           mRecipes.add(recipe);
           notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if(mRecipes != null && mRecipes.size() > 0)
            if(mRecipes.get(mRecipes.size()-1).getTitle().equals(LOADING_STRING))
                return true;
        return false;
    }

    public void displaySearchCategories(){
        mRecipes = new ArrayList<>();
        for(Map.Entry<String,String> entry : Constants.DEFAULT_SEARCH_CATEGORY_ITEM.entrySet()){
            Recipe recipe = new Recipe();
            recipe.setTitle(entry.getKey());
            recipe.setImage(entry.getValue());
            recipe.setPrepareTime(-1);
            mRecipes.add(recipe);
        }
        notifyDataSetChanged();
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
