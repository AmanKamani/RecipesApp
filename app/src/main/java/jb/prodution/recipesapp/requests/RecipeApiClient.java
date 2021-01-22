package jb.prodution.recipesapp.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import jb.prodution.recipesapp.models.Recipe;

// Actual connected with the database or make the api call from here
public class RecipeApiClient {

    private static RecipeApiClient instance;
    private static MutableLiveData<List<Recipe>> recipes;


    private RecipeApiClient(){
        recipes = new MutableLiveData<>();
    }

    public static RecipeApiClient getInstance() {
        if(instance == null)
            instance = new RecipeApiClient();
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
