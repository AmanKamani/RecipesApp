package jb.production.recipesapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import static jb.production.recipesapp.util.Constants.API_KEYS;
import static jb.production.recipesapp.util.Constants.CURRENT_API_KEY_PREFERENCE;

public class ApiKeyUtility {

    private static final String CURRENT_API_KEY_INDEX = "CURRENT_API_KEY_INDEX";
    private static SharedPreferences sharedPreferences;

    private static void setSharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(CURRENT_API_KEY_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void setUpApiKey(Context context){
        setSharedPreferences(context);
        if(isApiKeyAvailable(context)){
            int currentIndex = getCurrentApiKeyIndex();
            setCurrentApiKey(currentIndex);
        }
        else{
            changeToNextApiKey(context);
        }
    }

    public static void changeToNextApiKey(Context context){
        setSharedPreferences(context);

        int currentIndex = getCurrentApiKeyIndex();

        int nextIndex = (currentIndex + 1)%Constants.API_KEYS.length;
        setApiKeyIndex(nextIndex);

        setCurrentApiKey(nextIndex);

    }

    private static void setCurrentApiKey(int index){
        Constants.API_KEY = API_KEYS[index];
    }

    private static void setApiKeyIndex(int nextIndex){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(CURRENT_API_KEY_INDEX, nextIndex);
        edit.apply();
        edit.commit();
    }

    private static int getCurrentApiKeyIndex(){
        return sharedPreferences.getInt(CURRENT_API_KEY_INDEX,-1);
    }

    private static boolean isApiKeyAvailable(Context context){
        return context.getSharedPreferences(CURRENT_API_KEY_PREFERENCE,Context.MODE_PRIVATE).contains(CURRENT_API_KEY_INDEX);
    }
}
