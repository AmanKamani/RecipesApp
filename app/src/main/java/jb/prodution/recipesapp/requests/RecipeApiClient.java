package jb.prodution.recipesapp.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import jb.prodution.recipesapp.AppExecutors;
import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.requests.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.Response;

import static jb.prodution.recipesapp.util.Constants.API_KEY;
import static jb.prodution.recipesapp.util.Constants.NETWORK_TIMEOUT;

// Actual connected with the database or make the api call from here
public class RecipeApiClient {

    private String TAG = "$$$";

    private static RecipeApiClient instance;
    private static MutableLiveData<List<Recipe>> recipes;
    private RetrieveRecipesRunnable retrieveRecipesRunnable;

    private int recordsToSkip;

    private RecipeApiClient(){
        recipes = new MutableLiveData<>();
    }

    public static RecipeApiClient getInstance() {
        if(instance == null)
            instance = new RecipeApiClient();
        return instance;
    }

    public int getRecordsToSkip(){
        return recordsToSkip;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public void searchRecipesApi(String query, String diet, int skipRecords){
        if(retrieveRecipesRunnable != null)
            retrieveRecipesRunnable = null;

        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, diet, skipRecords);

        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveRecipesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {

                // add code to make user know that it is cancelled.

                // if the network request took more than NETWORK_TIMEOUT then it should cancel.
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable{

        private String query;
        private String diet;
        private int skipRecords;
        private boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, String diet, int skipRecords){
            this.query = query;
            this.skipRecords = skipRecords;
            this.diet = diet;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(this.query,this.diet, this.skipRecords).execute();
                if(cancelRequest)
                    return;
                else if(response.code() == 200){
                    RecipeSearchResponse recipeSearchResponse = (RecipeSearchResponse)response.body();

                    recordsToSkip = recipeSearchResponse.getCount()+recipeSearchResponse.getOffset();

                    List<Recipe> list = new ArrayList<>(recipeSearchResponse.getRecipes());

                    /*
                    postValue() is used in the background thread means post this to the main thread
                    setValue() is used in the main thread
                     */
                    if(skipRecords == 0)
                        recipes.postValue(list);
                    else{
                        /*
                        If the skipRecords != 0 means we don't require to create the new list of recipes,
                        we just require to add the new recipes to the current mutable List recipe object.
                         */
                        List<Recipe> currentRecipes = recipes.getValue();
                        currentRecipes.addAll(list);
                        recipes.postValue(currentRecipes);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: "+error);
                    recipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipes.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, String diet, int skipRecords){
            return ServiceGenerator.getRecipeApi().
                    searchRecipe(API_KEY, query, diet, String.valueOf(skipRecords));
        }

        private void cancelRequest(){
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        retrieveRecipesRunnable.cancelRequest();
    }
}
