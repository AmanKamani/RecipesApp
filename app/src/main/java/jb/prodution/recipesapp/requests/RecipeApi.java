package jb.prodution.recipesapp.requests;

import jb.prodution.recipesapp.requests.responses.RecipeResponse;
import jb.prodution.recipesapp.requests.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {

    // SEARCH
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("apiKey") String key, // ?
            @Query("query") String query,  // &
            @Query("diet") String diet,  // &
            @Query("offset") String skipRecords // &
    );

    // GET SPECIFIC RECIPE
    @GET("recipes/{id}/information?includeNutrition=false")
    Call<RecipeResponse> getRecipe(
            // always path arguments should be first and then query
            @Path(value = "id",encoded = true) String id,
            @Query("apiKey") String key
    );
}
