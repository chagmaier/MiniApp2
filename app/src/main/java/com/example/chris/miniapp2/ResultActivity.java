package com.example.chris.miniapp2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Chris on 3/7/18.
 */

public class ResultActivity extends AppCompatActivity{
    private Context mContext;
    private ListView mListView;
    private ArrayList<Recipe> resultRecipes;
    private ArrayList<Recipe> recipeList;
    private TextView recipeResults;
    private RecipeAdapter adapter;
    private ImageButton searchButton;

    //NotificationCompat.Builder notification;
    private static final int uniqueID = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_result_view);

        //searchButton = findViewById(R.layout.list_item_recipe).findViewById(R.id.want_to_make_button);

        mContext = this;
        recipeResults = findViewById(R.id.result_view);
        recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        resultRecipes = new ArrayList<>();

        String dietLabel = this.getIntent().getExtras().getString("newDietLabel");
        String servingSize = this.getIntent().getExtras().getString("servingSize");
        String prepTime = this.getIntent().getExtras().getString("cookTime");

        //for loop to check parameters and add to recipe result list
        for(int i = 0; i<recipeList.size(); i++) {
            if(checkAll(dietLabel, servingSize, prepTime, recipeList.get(i))){
                resultRecipes.add(recipeList.get(i));
            }
        }

        recipeResults.setText(resultRecipes.size() + " recipes match search!");

        //set adapter to listview
        adapter = new RecipeAdapter(this, resultRecipes);
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);

        this.createNotificaiton();

    }

    private void createNotificaiton(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notificationID", "notificationName", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String notificationText = "";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notificationName")
                .setContentTitle("Cooking Instruction")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setSmallIcon(R.drawable.chef)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                ;

        final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(970970, builder.build());
    }

    //methods to check if dietrestrictions, servingsizes, and preptimes match up
    //must include default option "Select One" which includes all recipes
    public boolean checkDietLabels(String dietLabel, Recipe recipe) {
        if(dietLabel.equals("Select One") || recipe.searchLabel.contains(dietLabel)) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean checkServingSizes(String servingSize, Recipe recipe){
        if (servingSize.equals("Select One") || recipe.searchLabel.contains(servingSize)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkPrepTime(String prepTime, Recipe recipe){
        if (prepTime.equals("Select One")|| recipe.searchLabel.contains(prepTime)){
            return true;
        }
        //determine if less than 30 mins or in between 30 mins and 1 hr
        else if(prepTime.equals("30 Minutes or Less") && recipe.prepTimeLabel.equals("Less Than 1 Hour")){
            String prepTimeString = recipe.prepTime.substring(0, 2);
            int mins = Integer.parseInt(prepTimeString);
            if (mins <= 30){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //puts together checks to see if is a perfect match
    public boolean checkAll(String dietLabel, String servingSize, String prepTime, Recipe recipe) {
        boolean sameDietLabel = checkDietLabels(dietLabel, recipe);
        boolean sameServingSize = checkServingSizes(servingSize, recipe);
        boolean samePrepTime = checkPrepTime(prepTime, recipe);

        if(sameDietLabel && sameServingSize && samePrepTime) {
            return true;
        }
        else{
            return false;
        }
    }


}
