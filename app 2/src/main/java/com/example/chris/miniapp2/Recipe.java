package com.example.chris.miniapp2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 3/6/18.
 */

public class Recipe {

    public String title;
    public String imageUrl;
    public String instructionUrl;
    public String description;
    public String label;
    public String prepTime;
    public String servings;
    public int rowNumber;
    public String prepTimeLabel;
    public String servingLabel;
    public ArrayList<String> searchLabel;
    // constructor
    // default

    // method
    // static methods that read the json file in and load into Recipe

    // static method that loads our recipes.json using the helper method
    // this method will return an array list of recipes constructed from the JSON
    // file
    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context) {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();


        // try to read from JSON file
        // get information by using the tags
        // construct a Recipe Object for each recipe in JSON
        // add the object to arraylist
        // return arraylist
        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            // for loop to go through each recipe in your recipes array

            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.imageUrl = recipes.getJSONObject(i).getString("image");
                recipe.instructionUrl = recipes.getJSONObject(i).getString("url");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.servings = recipes.getJSONObject(i).getString("servings");
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");
                recipe.label = recipes.getJSONObject(i).getString("dietLabel");
                recipe.searchLabel = new ArrayList<>();
                recipe.rowNumber = i;

                //convert serving number from string to int
                int servingInt = Integer.parseInt(recipe.servings);

                // add number of servings to each recipe
                if(servingInt < 4) {
                    recipe.servingLabel = "less than 4";
                }
                else if(servingInt <=6){
                    recipe.servingLabel ="4-6";
                }
                else if(servingInt <= 9){
                    recipe.servingLabel = "7-9";
                }
                else{
                    recipe.servingLabel = "More Than 10";
                }
                //add preptime to each recipe
                //if has word "hour" in preptime, then must be >=hour
                if (recipe.prepTime.contains("hour")){
                    recipe.prepTimeLabel = "More Than 1 Hour";
                }
                //will find if <=30 mins in result activity
                else {
                    recipe.prepTimeLabel = "Less Than 1 Hour";
                }

                //set label, preptimelabel, servinglabels for each recipe
                recipe.searchLabel.add(recipe.label);
                recipe.searchLabel.add(recipe.prepTimeLabel);
                recipe.searchLabel.add(recipe.servingLabel);

                //add recipe to arraylist of recipes
                recipeList.add(recipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    // helper method that loads from any Json file
    public static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
