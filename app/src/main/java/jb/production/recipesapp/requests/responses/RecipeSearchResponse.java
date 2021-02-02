package jb.production.recipesapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jb.production.recipesapp.models.Recipe;

public class RecipeSearchResponse {

    @SerializedName("results")
    @Expose()
    private List<Recipe> recipes;

    @SerializedName("number")
    @Expose()
    private int count;

    @SerializedName("offset")
    @Expose()
    private int offset;

    @Expose()
    private int totalResults;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "recipes=" + recipes +
                ", count=" + count +
                ", offset=" + offset +
                ", totalResults=" + totalResults +
                '}';
    }
}
