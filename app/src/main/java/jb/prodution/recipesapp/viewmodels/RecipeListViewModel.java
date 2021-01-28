package jb.prodution.recipesapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.repositories.RecipeRepository;

// Fetching data from repository
public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;
    private boolean isShowingRecipes;

    public RecipeListViewModel(){
        isShowingRecipes = false;
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipeApi(String query, String diet, int skipRecords){
        isShowingRecipes = true;
        recipeRepository.searchRecipeApi(query, diet, skipRecords);
    }

    public void setShowingRecipes(boolean showRecipes){
        isShowingRecipes = showRecipes;
    }

    public boolean isShowingRecipes() {
        return isShowingRecipes;
    }
}