package jb.production.recipesapp.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.request.RequestOptions;

import jb.production.recipesapp.R;
import jb.production.recipesapp.viewmodels.RecipeListViewModel;
import jb.production.recipesapp.viewmodels.RecipeViewModel;

public class Utility {

    public static RequestOptions getImageOptions(){
        return new RequestOptions()
                .placeholder(R.drawable.eclipse_spinner);
    }

    public static void showErrorBox(Context context, ViewModel viewModel){

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage("Daily API Quota is exceeded. Please restart this application to activate the new API Key.")
                .setPositiveButton("Restart The App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiKeyUtility.changeToNextApiKey(context);

                        if(viewModel instanceof RecipeViewModel) {
                            RecipeViewModel recipeViewModel = (RecipeViewModel)viewModel;
                            recipeViewModel.setIsApiQuotaExceeded(false);
                        }
                        else if(viewModel instanceof RecipeListViewModel){
                            RecipeListViewModel recipeListViewModel = (RecipeListViewModel)viewModel;
                            recipeListViewModel.setIsApiQuotaExceeded(false);
                        }
                        System.exit(0);
                    }
                })
                .setCancelable(false)
                .create();

        dialog.show();
    }
}
