package jb.prodution.recipesapp.repositories;

import android.text.TextUtils;

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

    public void searchRecipeApi(String query, String diet, int skipRecords){
        if(TextUtils.isEmpty(diet))
            diet = "vegetarian";
        if(skipRecords < 0)
            skipRecords = 0;

        recipeApiClient.searchRecipesApi(query, diet, skipRecords);
    }
}
