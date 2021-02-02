package jb.prodution.recipesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import jb.prodution.recipesapp.adapters.OnRecipeListener;
import jb.prodution.recipesapp.adapters.RecipeRecyclerAdapter;
import jb.prodution.recipesapp.util.Utility;
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

        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // if recyclerview can not be scrolled vertically then it is time to load more data
                if(!mRecyclerView.canScrollVertically(1)){
                    // load more results
                    viewModel.searchNextRecords();
                }
            }
        });
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

                if(recipes.size() == 0)
                    mAdapter.setQueryExhausted();
            }
        });

        viewModel.isApiQuotaExceeded().observe(this, aBoolean -> {
            if(aBoolean){
                Utility.showErrorBox(RecipeListActivity.this,viewModel);
            }
        });

        viewModel.isQueryExhausted().observe(this, aBoolean -> {
            if(aBoolean) {
                mAdapter.setQueryExhausted();
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
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe",mAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String Category) {
        mAdapter.displayLoading();
        viewModel.searchRecipeApi(Category,"vegetarian",0);
    }

    @Override
    public void onBackPressed() {
        if(viewModel.shouldExit())
           super.onBackPressed();
        else
            displaySearchCategories();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_categories)
            displaySearchCategories();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}