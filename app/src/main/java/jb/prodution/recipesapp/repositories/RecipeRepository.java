package jb.prodution.recipesapp.repositories;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.requests.RecipeApiClient;
import jb.prodution.recipesapp.requests.responses.RecipeResponse;

// It is a Data hub as it is connected with webservice(network) or database
// Fetching data from WebService(Network - API)
public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient recipeApiClient;
    private String mDiet;
    private String mQuery;

    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();

    //Mediator in the sense take the data, modify it and then pass it further.
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();

    private RecipeRepository(){
        recipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }

    public static RecipeRepository getInstance(){
        if(instance == null)
            instance = new RecipeRepository();
        return instance;
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSource = recipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                }
                else{
                    // means there is no data, it can be due to the network issue. So we can check from the local cache (database)
                    // search database cache.
                    doneQuery(null);
                }
            }
        });
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    private void doneQuery(List<Recipe> list){
        if(list != null){
            if(list.size() % 10 != 0) // 10 is the number of results by default retrieved from the api.
                mIsQueryExhausted.setValue(true);
        }
        else
            mIsQueryExhausted.setValue(true);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipeApi(String query, String diet, int skipRecords){
        if(TextUtils.isEmpty(diet))
            diet = "vegetarian";
        if(skipRecords < 0)
            skipRecords = 0;

        mQuery = query;
        mDiet = diet;
        mIsQueryExhausted.setValue(false);
        recipeApiClient.searchRecipesApi(query, diet, skipRecords);
    }

    public LiveData<Boolean> isApiQuotaExceeded(){
        return recipeApiClient.isApiQuotaExceeded();
    }

    public void searchNextRecords(){
        int recordsToSkip = recipeApiClient.getRecordsToSkip();
        searchRecipeApi(mQuery, mDiet, recordsToSkip);
    }

    public LiveData<RecipeResponse> getRecipeResponse(){
        return recipeApiClient.getRecipeResponse();
    }

    public void searchRecipeById(String recipeId){
        recipeApiClient.searchRecipeById(recipeId);
    }

    public LiveData<Boolean> isRecipeRequestTimeOut(){
        return recipeApiClient.isRecipeRequestTimeOut();
    }

    public void cancelRequest(){
        recipeApiClient.cancelRequest();
    }

    public void setIsApiQuotaExceeded(boolean isApiQuotaExceeded){
        recipeApiClient.setIsApiQuotaExceeded(isApiQuotaExceeded);
    }
}
