package com.example.chris.miniapp2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Chris on 3/7/18.
 */

public class RecipeAdapter extends BaseAdapter{
    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;


    // constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList){

        // initialize instances variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // methods
    // a list of methods we need to override

    // gives you the number of recipes in the data source
    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //check if the view already exists
        //if yes, you don't need to inflate and findviewbyid again
        if(convertView == null) {
            // inflate
            convertView = mInflater.inflate(R.layout.list_item_recipe, parent, false);
            // add the views to the holder
            holder = new ViewHolder();
            // views
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_serving);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.prepTimeTextView = convertView.findViewById(R.id.recipe_list_prep_time);
            holder.wantToMakeButton = convertView.findViewById(R.id.want_to_make_button);

            //add holder to the view for future use
            convertView.setTag(holder);
        }
        else{
            // get the view holder from converview
            holder = (ViewHolder)convertView.getTag();
        }

        // get corresonpinding recipe for each row
        final Recipe recipe = (Recipe) getItem(position);

        // get relavate subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        TextView prepTimeTextView = holder.prepTimeTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        TextView numberOfRecipesTextView = holder.numberOfRecipesTextView;
        ImageButton wantToMakeButton = holder.wantToMakeButton;

        // update the row view's textviews and imageview to display the information
        titleTextView.setText(recipe.title);
        titleTextView.setTextSize(18);

        servingTextView.setText(recipe.servings + " servings");
        servingTextView.setTextSize(14);

        prepTimeTextView.setText(recipe.prepTime);
        prepTimeTextView.setTextSize(14);

        /*numberOfRecipesTextView.setText(getCount());
        numberOfRecipesTextView.setTextSize(18);*/

        // imageView
        // use Picasso library to load image from the image url
        Picasso.with(mContext).load(recipe.imageUrl).into(thumbnailImageView);

        Picasso.with(mContext).load(R.drawable.chef2).into(wantToMakeButton);

        holder.wantToMakeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext,"default");
                builder.setStyle(new NotificationCompat.BigTextStyle(builder).bigText("The " + recipe.title + " Recipe Can Be Found Here!").setBigContentTitle("Cooking Instructions"));
                builder.setSmallIcon(R.drawable.chef);
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
                notificationIntent.setData(Uri.parse(recipe.instructionUrl));
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(1,builder.build());
            }
        });

        return convertView;

    }



    private static class ViewHolder {
        public TextView titleTextView;
        public TextView servingTextView;
        public TextView prepTimeTextView;
        public ImageView thumbnailImageView;

        public TextView numberOfRecipesTextView;
        public ImageButton wantToMakeButton;
    }



}
