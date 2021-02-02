package jb.prodution.recipesapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import jb.prodution.recipesapp.repositories.RecipeRepository;
import jb.prodution.recipesapp.requests.responses.RecipeResponse;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean mHasRetrievedRecipe;
    private boolean mHasNetworkError;


    public RecipeViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
        mHasRetrievedRecipe = false;
        mHasNetworkError = false;
    }

    public LiveData<RecipeResponse> getRecipeResponse(){
        return mRecipeRepository.getRecipeResponse();
    }

    public void searchRecipeById(String recipeId){
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public LiveData<Boolean> isRecipeRequestTimeOut(){
        return mRecipeRepository.isRecipeRequestTimeOut();
    }

    public String getRecipeId(){
        return mRecipeId;
    }

    public void setRetrievedRecipe(boolean retrievedRecipe){
        this.mHasRetrievedRecipe = retrievedRecipe;
    }

    public boolean hasRetrievedRecipe(){
        return mHasRetrievedRecipe;
    }

    public boolean hasNetworkError(){ return mHasNetworkError; }

    public void setNetworkError(boolean networkError){ this.mHasNetworkError = networkError; }

    public LiveData<Boolean> isApiQuotaExceeded(){
        return mRecipeRepository.isApiQuotaExceeded();
    }

    public void setIsApiQuotaExceeded(boolean isApiQuotaExceeded){
        mRecipeRepository.setIsApiQuotaExceeded(isApiQuotaExceeded);
    }
}
