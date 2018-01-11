package com.example.chandrakanth.itunestoppaidapps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chandrakanth on 2/22/2017.
 */

public class ApplicationAdapter extends ArrayAdapter<Application> {
    AlertDialog.Builder builder;
    List<Application> mData;
    Context mContext;
    int mResource;
    int mtextType;
    public ApplicationAdapter(Context context, int resource, int textId, List<Application> objects) {
        super(context, resource, textId, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
        this.mtextType = textId;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        final Application application=mData.get(position);
        TextView AppListView=(TextView)convertView.findViewById(R.id.LVtextView);
        AppListView.setText(application.getAppName()+"\n"+"Price: "+application.getAppPriceCurrency()+" "+application.getAppPrice());

        ImageView AppimageView=(ImageView)convertView.findViewById(R.id.imageView);

        Picasso.with(convertView.getContext()).load(application.getAppImageUrl())
                .placeholder(R.mipmap.user_placeholder)
                .error(R.mipmap.user_placeholder_error)
                .into(AppimageView);
        final ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.ImgBtn);
        final SharedPreferences myPref=getContext().getSharedPreferences("com.example.chandrakanth.itunestoppaidapps", MODE_PRIVATE);
        final SharedPreferences.Editor prefEditor=myPref.edit();
        final Set<String> NullSet=new ArraySet<String>();
        final Set<String> Set=myPref.getStringSet("AppFavorite",NullSet);
        if(Set.contains(application.getAppName()))
        {
            imageButton.setImageResource(R.drawable.black);
        }
        else{
            imageButton.setImageResource(R.drawable.white);
        }



        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
                public void onClick(View v)
                {
                    if(Set.contains(application.getAppName())) {
                        new AlertDialog.Builder(v.getRootView().getContext())
                                .setTitle("Add to Favourites")
                                .setMessage("Are you sure that you want to remove this App to favourites")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Set.remove(application.getAppName());
                                        imageButton.setImageResource(R.drawable.white);
                                        ArrayList<Application>  favorites = new ArrayList<>();
                                        final SharedPreferences myPrefs = getContext().getSharedPreferences("com.example.chandrakanth.itunestoppaidapps", MODE_PRIVATE);
                                        final Set<String> nullSet = new ArraySet<String>();
                                        final Set<String> Set = myPrefs.getStringSet("AppFavorite",nullSet);
                                        for (Application app:mData)
                                        {
                                            if(Set.contains(app.getAppName()))
                                                favorites.add(app);
                                        }

                                        if(mContext instanceof FavoriteActivity){
                                            ((FavoriteActivity)mContext).FillAppsList(favorites);
                                        }
                                        Toast.makeText(getContext(),"Removed successfully",Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                    else{


                        new AlertDialog.Builder(v.getRootView().getContext())
                                .setTitle("Add to Favourites")
                                .setMessage("Are you sure that you want to add this App to favourites")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                         Set.add(application.getAppName());
                                        imageButton.setImageResource(R.drawable.black);
                                        Log.d("demoset",Set.toString());

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                        prefEditor.putStringSet("AppFavorite",Set);
                        prefEditor.commit();


                    }


                }
        });

        return convertView;
    }


}
