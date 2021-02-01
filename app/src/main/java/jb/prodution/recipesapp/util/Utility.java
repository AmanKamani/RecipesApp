package jb.prodution.recipesapp.util;

import com.bumptech.glide.request.RequestOptions;

import jb.prodution.recipesapp.R;

public class Utility {

    public static RequestOptions getImageOptions(){
        return new RequestOptions()
                .placeholder(R.drawable.eclipse_spinner);
    }

    //https://medium.com/mobile-app-development-publication/glide-image-loader-the-basic-798db220bb44
}
