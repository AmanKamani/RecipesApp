package jb.prodution.recipesapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import jb.prodution.recipesapp.repositories.RecipeRepository;
import jb.prodution.recipesapp.requests.responses.RecipeResponse;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;

    public RecipeViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<RecipeResponse> getRecipeResponse(){
        return mRecipeRepository.getRecipeResponse();
    }

    public void searchRecipeById(String recipeId){
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId(){
        return mRecipeId;
    }
}
