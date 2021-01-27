package jb.prodution.recipesapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import jb.prodution.recipesapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, source, time, price, people;
    AppCompatImageView image;
    OnRecipeListener onRecipeListener;

    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);

        this.onRecipeListener = onRecipeListener;

        title = itemView.findViewById(R.id.recipe_title);
        image = itemView.findViewById(R.id.recipe_image);
        source = itemView.findViewById(R.id.recipe_source);
        time = itemView.findViewById(R.id.recipe_prepare_time);
        price = itemView.findViewById(R.id.recipe_price);
        people = itemView.findViewById(R.id.recipe_people);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
    }
}
