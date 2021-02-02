package jb.production.recipesapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import jb.production.recipesapp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title;
    CircleImageView image;
    OnRecipeListener listener;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener recipeListener) {
        super(itemView);
        title = itemView.findViewById(R.id.category_title);
        image = itemView.findViewById(R.id.category_image);
        this.listener = recipeListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onCategoryClick(title.getText().toString());
    }
}
