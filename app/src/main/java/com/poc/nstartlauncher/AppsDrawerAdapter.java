package com.poc.nstartlauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppsDrawerAdapter extends RecyclerView.Adapter {

    private static Context context;
    private static List<AppInfo> appsList;
    public AppsDrawerAdapter(Context c ) {
        context = c;
        setUpApps();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_view_list, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((ViewHolder)holder, position);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {


        String appLabel = appsList.get(position).label.toString();
        String appPackage = appsList.get(position).packageName.toString();
        Drawable appIcon = appsList.get(position).icon;

        TextView textView = holder.textView;
        textView.setText(appLabel);
        ImageView imageView = holder.img;
        imageView.setImageDrawable(appIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                context.startActivity(
                        context.getPackageManager().getLaunchIntentForPackage(appPackage));
            }
        });
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            textView =  itemView.findViewById(R.id.tv_app_name);
            img = itemView.findViewById(R.id.app_icon);



        }


    }


    public static void setUpApps(){
        PackageManager pManager = context.getPackageManager();

        appsList = new ArrayList<AppInfo>();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = pManager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pManager);
            app.packageName = ri.activityInfo.packageName;

            Log.i(" Log package ",app.packageName.toString());
            app.icon = ri.activityInfo.loadIcon(pManager);
            appsList.add(app);

        }

    }


}
