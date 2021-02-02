package jb.prodution.recipesapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import jb.prodution.recipesapp.util.ApiKeyUtility;
import jb.prodution.recipesapp.util.Constants;

public abstract class BaseActivity extends AppCompatActivity {
// Not required to create the create the instance of this class. So it is abstract

    public ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiKeyUtility.setUpApiKey(this);
        Log.e("$$$", Constants.API_KEY);
    }

    @Override
    public void setContentView(int layoutResID) {
        @SuppressLint("InflateParams")
        ConstraintLayout constraintLayout = (ConstraintLayout)getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_container);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        mProgressBar = constraintLayout.findViewById(R.id.progressBar);

        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
