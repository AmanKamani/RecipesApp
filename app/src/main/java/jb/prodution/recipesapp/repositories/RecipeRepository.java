package jb.prodution.recipesapp.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.requests.RecipeApiClient;

// It is a Data hub as it is connected with webservice(network) or database
// Fetching data from WebService(Network - API)
public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient recipeApiClient;

    private RecipeRepository(){
        recipeApiClient = RecipeApiClient.getInstance();
    }

    public static RecipeRepository getInstance(){
        if(instance == null)
            instance = new RecipeRepository();
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeApiClient.getRecipes();
    }
}
