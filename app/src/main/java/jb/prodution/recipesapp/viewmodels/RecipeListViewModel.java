package jb.prodution.recipesapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.repositories.RecipeRepository;

// Fetching data from repository
public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;
    private boolean isShowingRecipes;
    private boolean isPerformingQuery;

    public RecipeListViewModel(){
        isShowingRecipes = false;
        isPerformingQuery = false;
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeRepository.getRecipes();
    }

    public LiveData<Boolean> isQueryExhausted(){
        return recipeRepository.isQueryExhausted();
    }

    public void searchRecipeApi(String query, String diet, int skipRecords){
        isShowingRecipes = true;
        isPerformingQuery = true;
        recipeRepository.searchRecipeApi(query, diet, skipRecords);
    }

    public void searchNextRecords(){
        if(!isPerformingQuery && isShowingRecipes && !isQueryExhausted().getValue()){
            recipeRepository.searchNextRecords();
        }
    }

    public void setShowingRecipes(boolean showRecipes){
        isShowingRecipes = showRecipes;
    }

    public boolean isShowingRecipes() {
        return isShowingRecipes;
    }

    public boolean isPerformingQuery(){
        return isPerformingQuery;
    }

    public void setPerformingQuery(boolean isPerformingQuery) {
        this.isPerformingQuery = isPerformingQuery;
    }

    /* If the recipe category is on the screen then only exit from the app
        So return true
        else return false
     */
    public boolean shouldExit(){
        if(isPerformingQuery) {
            recipeRepository.cancelRequest();
            return false;
        }
        if(isShowingRecipes)
            return false;

        return true;
    }
}