package jb.prodution.recipesapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import jb.prodution.recipesapp.adapters.OnRecipeListener;
import jb.prodution.recipesapp.adapters.RecipeRecyclerAdapter;
import jb.prodution.recipesapp.util.VerticalSpacingItemDecorator;
import jb.prodution.recipesapp.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private RecipeListViewModel viewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.recipe_list);

        viewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!viewModel.isShowingRecipes())
            displaySearchCategories();
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus(); // clear focus so the back navigation works properly.
                mAdapter.displayLoading();
                viewModel.searchRecipeApi(query.trim(), "vegetarian", 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
            if(viewModel.isShowingRecipes() && recipes != null){
                viewModel.setPerformingQuery(false);
                mAdapter.setRecipes(recipes);
            }
        });
    }

    private void displaySearchCategories(){
        viewModel.setShowingRecipes(false);
        viewModel.setPerformingQuery(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String Category) {
        mAdapter.displayLoading();
        viewModel.searchRecipeApi(Category,"vegetarian",0);
    }

    @Override
    public void onBackPressed() {
        Log.e("$$$","back pressed");
        if(viewModel.shouldExit())
           super.onBackPressed();
        else
            displaySearchCategories();
    }
}