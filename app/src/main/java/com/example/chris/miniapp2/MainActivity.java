package com.example.chris.miniapp2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button mLoginButton;
    static final String CHANNEL_ID = "COOKING_NOTIFICATIONS_CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

        RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

        //get button from xml
        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setTextSize(14);

        //start 2nd activity after clicking loginbutton
        mLoginButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchActivity();
            }
        });
    }
    //launch
    private void launchActivity() {
        Intent intent = new Intent(mContext, SearchActivity.class);

        startActivityForResult(intent, 1);
    }


}
