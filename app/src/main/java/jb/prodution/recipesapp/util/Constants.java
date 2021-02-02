package jb.prodution.recipesapp.util;

import java.util.HashMap;

public class Constants {

    public static final String CURRENT_API_KEY_PREFERENCE = "CURRENT_API_KEY_PREFERENCE";

    public static final String BASE_URL = "https://api.spoonacular.com";

//     jems bond
    public static String API_KEY = "a4d2a06c9f4f41418014caa33edf26ba";

//     polot96027@lovomon.com
//     public static final String API_KEY = "ad9d86476cf54d29a97c8667386c66c4";

//     shaylin35@overcomeoj.com
//    public static final String API_KEY = "d007f85d8ea24813a1b01edf5ca11b72";


    public static final String[] API_KEYS = {
            "a4d2a06c9f4f41418014caa33edf26ba", // jems bond
            "ad9d86476cf54d29a97c8667386c66c4", // polot96027@lovomon.com
            "d007f85d8ea24813a1b01edf5ca11b72", // shaylin35@overcomeoj.com
            "1e1a98810acd4fb5aa247cd436791d98", // colbia34@dsecurelyx.com
            "ac00c17314b94cc28c1bd21261941d6c", // authur.c4@dsecurelyx.com
            "0089f320275049d59c60ed07888a6a98", // arland.69@dsecurelyx.com
            "0997b28a30cd4b1db30677b5b8dea4bc", // meribethc52@dsecurelyx.com
            "1d44a1f336744548a4a3c1fb2d2bc1b6", // ballard62@dsecurelyx.com
            "8d7894f78aa9464e9a4b9232c6486b46", // muriel.754@dsecurelyx.com
            "045d34e74d5d4e44a6dbb2621e762bbe", // lightcoralf29@dsecurelyx.com
            "f0e2f85c5f5947c8aa69869602b6aa39", // orangeb03@dsecurelyx.com
            "96600b41e37f4e1ebd2b7f16b56dc783", // shadeed@dsecurelyx.com
            "d0f39aee3ced49a7a7a591eb40cfb37b", // lillianna.81@dsecurelyx.com
            "75123e90d5f6458787cfe2e12003a16f", // sarayu@dsecurelyx.com
            "70de1598749543619e8123c50cc75e12", // anneth089@dsecurelyx.com
            "68cbe4b5a9504b7980b5e6b8b89aac8f", // fionna4@dsecurelyx.com
            "833c15d10d484b379d0d01f2e6f37f3b", // geraldin@dsecurelyx.com
            "6209a405760743b087012e814774293c", // keet@dsecurelyx.com
            "bac93c7408cd4a598956811fca4fdeb2", // gavanb0@dsecurelyx.com
            "542821a4b29746128ef71a13ccc57b3b", // jerad32@dsecurelyx.com
            "d39c1f0ddf6144aab3bdb295c86a877d", // zohair03@dsecurelyx.com
    };

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
