package jb.prodution.recipesapp.util;

import java.util.HashMap;

public class Constants {

    public static final String BASE_URL = "https://api.spoonacular.com";

    // jems bond
//    public static final String API_KEY = "a4d2a06c9f4f41418014caa33edf26ba";
    //
    public static final String API_KEY = "ad9d86476cf54d29a97c8667386c66c4";

    public static final int NETWORK_TIMEOUT = 4000; // milli seconds

//    public static final String[] DEFAULT_SEARCH_CATEGORY = {
//            "Tea", "Breakfast", "Italian", "Brunch", "Paneer", "Bread", "Drinks", "Dinner", "Cheese", "Cake"
//    };
//
//    public static final String[] DEFAULT_SEARCH_CATEGORY_IMAGE = {
//
//    };

    public static final HashMap<String, String> DEFAULT_SEARCH_CATEGORY_ITEM = new HashMap<String,String>(){{
        put("Tea","tea");
        put("Breakfast","breakfast");
        put("Italian", "italian");
        put("Brunch", "brunch");
        put("Paneer", "paneer");
        put("Bread", "bread");
        put("Drinks", "drinks");
        put("Dinner", "dinner");
        put("Cheese", "cheese");
        put("Cake", "cake");
    }};
}
