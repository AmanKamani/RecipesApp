package jb.prodution.recipesapp;

import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import jb.prodution.recipesapp.models.Recipe;
import jb.prodution.recipesapp.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity {

    private RecipeListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        viewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        subscribeObservers();
    }

    private void searchRecipeApi(String query, String diet, int skipRecords){
        viewModel.searchRecipeApi(query, diet, skipRecords);
    }

    private void subscribeObservers(){
//        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
//            @Override
//            public void onChanged(List<Recipe> recipes) {
//
//            }
//        });

//        or can write the above observe with the JAVA lambda function

        viewModel.getRecipes().observe(this, recipes -> {

        });
    }
}