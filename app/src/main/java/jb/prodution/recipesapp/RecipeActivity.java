package jb.prodution.recipesapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;

import jb.prodution.recipesapp.models.Ingredient;
import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.requests.responses.RecipeResponse;
import jb.prodution.recipesapp.util.ApiKeyUtility;
import jb.prodution.recipesapp.util.Utility;
import jb.prodution.recipesapp.viewmodels.RecipeViewModel;

public class RecipeActivity extends BaseActivity {

    private AppCompatImageView mRecipeImage;
    private TextView mRecipeTitle, mRecipePrepareTime, mRecipePrice, mRecipeServings;
    private LinearLayout mRecipeIngredientsContainer;
    private ScrollView mScrollView;

    private RecipeViewModel recipeViewModel;;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mScrollView = findViewById(R.id.parent);
        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeTitle = findViewById(R.id.recipe_title);
        mRecipePrepareTime = findViewById(R.id.recipe_prepare_time);
        mRecipePrice = findViewById(R.id.recipe_price);
        mRecipeServings = findViewById(R.id.recipe_people);
        mRecipeIngredientsContainer = findViewById(R.id.ingredients_container);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        subscribeObserver();
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            final Recipe recipe = getIntent().getParcelableExtra("recipe");
            showProgressBar(true);
            recipeViewModel.searchRecipeById(String.valueOf(recipe.getId()));
        }
    }

    private void subscribeObserver() {

        recipeViewModel.getRecipeResponse().observe(this, recipeResponse -> {
            if (recipeResponse != null
                    && recipeResponse.getId() == Integer.parseInt(recipeViewModel.getRecipeId())) {
                /*
                 * Here the second condition is for displaying progressbar for the second time.
                 * Problem : first time clicking on the item, will first show the progress bar and
                 * make the request to the api and store the data to the view model.
                 * after that if this activity will be closed then it will be destroyed but viewModel won't because
                 * that is the main purpose of the viewModel to hold the data.
                 * So, now again if I will select any item it will open this activity,
                 * but it will directly display the data which is available in the viewModel
                 * and when the network request completes, it will change the data and display the updated data.
                 * But during this it will not show the progress bar.
                 *
                 * Solution :
                 * So, to solve that we checked that if the requested recipeId is the same as the recipeId stored in the viewModel
                 * then, directly display the data from the viewModel else do not display that data, and wait for the new data.
                 * So it will not enter to this if block and progress bar will not be hidden.
                 * Once the new data will be stored in the viewModel then this if block will be executed and the setProperties()
                 * executes, which hides the progress bar. Till that much of time progress bar will be displayed.
                 */
                recipeViewModel.setNetworkError(false);
                setProperties(recipeResponse);
                recipeViewModel.setRetrievedRecipe(true);
            }
        });

        recipeViewModel.isRecipeRequestTimeOut().observe(this, isTimeOut -> {

                if (isTimeOut && !recipeViewModel.hasRetrievedRecipe()) {
                    // Time out
                    recipeViewModel.setNetworkError(true);
                    recipeViewModel.setRetrievedRecipe(true);
                }
                if(recipeViewModel.hasNetworkError())
                    displayErrorScreen();
        });

        recipeViewModel.isApiQuotaExceeded().observe(this, aBoolean -> {
            if(aBoolean){
                Utility.showErrorBox(RecipeActivity.this, recipeViewModel);
            }
        });
    }

    private void displayErrorScreen(){
        mRecipePrepareTime.setVisibility(View.INVISIBLE);
        mRecipePrice.setVisibility(View.INVISIBLE);
        mRecipeServings.setVisibility(View.INVISIBLE);

        mRecipeTitle.setText(R.string.recipe_error_msg1);
        Glide.with(RecipeActivity.this)
                .load(R.drawable.ic_launcher_background)
                .into(mRecipeImage);

        mRecipeIngredientsContainer.removeAllViews();

        TextView error = new TextView(this);
        error.setText(R.string.recipe_error_msg2);
        error.setTextSize(15);
        error.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRecipeIngredientsContainer.addView(error);

        showProgressBar(false);
        showParent();
    }

    private void setProperties(RecipeResponse recipeResponse) {

        if (recipeResponse != null) {

            Glide.with(RecipeActivity.this)
                    .applyDefaultRequestOptions(Utility.getImageOptions())
                    .load(recipeResponse.getImage())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mRecipeImage);

            mRecipeTitle.setText(recipeResponse.getTitle());
            mRecipeServings.setText(String.valueOf(recipeResponse.getServings()));
            mRecipePrice.setText(String.format("%.2f/person", recipeResponse.getPricePerServing()));
            mRecipePrepareTime.setText(String.format("%d min", recipeResponse.getPrepareTime()));

            mRecipeIngredientsContainer.removeAllViews();

            for (Ingredient ingredient : recipeResponse.getIngredientList()) {
                TextView ing = new TextView(RecipeActivity.this);
                ing.setText(ingredient.getName());
                ing.setTextSize(15);
                ing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                mRecipeIngredientsContainer.addView(ing);
            }

            showParent();
            showProgressBar(false);
        }
    }

    private void showParent() {
        mScrollView.setVisibility(View.VISIBLE);
    }
}
