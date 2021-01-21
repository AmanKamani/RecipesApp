package jb.prodution.recipesapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jb.prodution.recipesapp.models.Ingredient;

public class RecipeResponse{

    private String id;
    private String title;
    private String image;
    private String sourceName;
    private int readyInMinutes;
    @SerializedName("extendedIngredients")
    @Expose()
    private List<Ingredient> ingredients;

    public String getId() {
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

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", ingredients=" + ingredients +
                '}';
    }
}
