package com.example.chris.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import javax.xml.transform.Result;

/**
 * Created by Chris on 3/6/18.
 */

public class SearchActivity extends AppCompatActivity {

    private Context mContext;
    private Spinner dietSpinner;
    private Spinner servingSpinner;
    private Spinner preparationTimeSpinner;
    private Button searchButton;

    private TextView practiceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mContext = this;
        dietSpinner = findViewById(R.id.dietDropdown);
        servingSpinner = findViewById(R.id.servingDropdown);
        preparationTimeSpinner = findViewById(R.id.prepTimeDropdown);
        searchButton = findViewById(R.id.searchButton);

        //arraylists for restrictions
        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        ArrayList<String> dietRestrictionLabel = new ArrayList<>();
        ArrayList<String> servingLabel = new ArrayList<>();
        ArrayList<String> prepTimeLabel = new ArrayList<>();

        //initialize arrays for spinners with default value "Select One"
        dietRestrictionLabel.add("Select One");
        servingLabel.add("Select One");
        prepTimeLabel.add("Select One");
        //add <=30 here
        prepTimeLabel.add("30 Minutes or Less");

        for(int i = 0; i<recipeList.size(); i++) {
            String newDietLabel = recipeList.get(i).label;
            String servingSize = recipeList.get(i).servingLabel;
            String cookTime = recipeList.get(i).prepTimeLabel;

            //if the array doesn't already contain this label add it
            if(!dietRestrictionLabel.contains(newDietLabel)) {
                dietRestrictionLabel.add(newDietLabel);
            }
            //if doesn't have serving size, add it
            if(!servingLabel.contains(servingSize)) {
                servingLabel.add(servingSize);
            }
            //if doesn't have preptime, add it
            if(!prepTimeLabel.contains(cookTime)) {
                prepTimeLabel.add(cookTime);
            }
        }

        servingLabel.add("Less than 4");

        //add adapters for arrays
        ArrayAdapter<String> dietRestrictionArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dietRestrictionLabel);
        ArrayAdapter<String> servingSizeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servingLabel);
        ArrayAdapter<String> prepTimeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, prepTimeLabel);

        //add adapters to spinners
        dietSpinner.setAdapter(dietRestrictionArrayAdapter);
        servingSpinner.setAdapter(servingSizeArrayAdapter);
        preparationTimeSpinner.setAdapter(prepTimeArrayAdapter);

        //set onClick listeners

        dietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDietSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        servingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedServingSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        preparationTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedPrepTimeSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*servingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i)
                        + " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent(mContext, ResultActivity.class);
                resultIntent.putExtra("newDietLabel", dietSpinner.getSelectedItem().toString());
                resultIntent.putExtra("servingSize", servingSpinner.getSelectedItem().toString());
                resultIntent.putExtra("cookTime", preparationTimeSpinner.getSelectedItem().toString());

                startActivity(resultIntent);
            }
        });

    }

    private void launchActivity() {
        Intent intent = new Intent(mContext, ResultActivity.class);

        startActivityForResult(intent, 1);
    }

}