package jb.prodution.recipesapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jb.prodution.recipesapp.models.Ingredient;

public class RecipeResponse{

    private int id;
    private String title;
    private String image;
    private String sourceName;
    private int servings;
    private float pricePerServing;
    @SerializedName("readyInMinutes")
    @Expose()
    private int prepareTime;
    @SerializedName("extendedIngredients")
    @Expose()
    private List<Ingredient> ingredientList;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getSourceName() {
        return sourceName;
    }

    public int getServings() {
        return servings;
    }

    public float getPricePerServing() {
        return pricePerServing;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", servings=" + servings +
                ", pricePerServing=" + pricePerServing +
                ", prepareTime=" + prepareTime +
                ", ingredientList=" + ingredientList +
                '}';
    }
}
