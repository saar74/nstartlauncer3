package com.poc.nstartlauncher;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean bShowHomeScreen;
    private HomeScreenFragment mHomeFragment;
    private fragment_apps_drawer mAppsFragment;
    private boolean mbSwitch2Home;
    private boolean mbFlashlightOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mbSwitch2Home = true;
        super.onCreate(savedInstanceState);
        mAppsFragment = null;
        setContentView(R.layout.activity_main);
        mHomeFragment = new HomeScreenFragment();
        loadFragment(mHomeFragment);
        bShowHomeScreen = true;
        mbFlashlightOn = false;
    }


        private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }

        return false;

    }

    public void onShowAppsClick(View view) {
        if (mAppsFragment == null) {
            mAppsFragment = new fragment_apps_drawer();
        }
        loadFragment(mAppsFragment);
        bShowHomeScreen = false;
    }

    public void onSettingClick(View view) {
        startActivity( new Intent(Settings.ACTION_SETTINGS), /*options:*/ null);
    }


    public void onFlashlightClick(View view) {
        try {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                camManager.setTorchMode(cameraId, !mbFlashlightOn);
                mbFlashlightOn = !mbFlashlightOn;

            }
        }
        catch(Exception e) {

        }
//        Camera cam = cameraProvider.bindToLifecycle((LifecycleOwner)this,
//                cameraSelector, imageAnalysis, preview);
//
//        if ( cam.getCameraInfo().hasFlashUnit() ) {
//            cam.getCameraControl().enableTorch(true); // or false
//        }
    }

    public void onSearchlick(View view) {
        EditText txtw = (EditText) findViewById(R.id.txtSearch);

        String url = "https://search.nstart.online/pse/search?&query=" + txtw.getText() +"&spid=2&sspid=1&source=nstart-launcher&source_version=nsl1000;";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        // Note the Chooser below. If no applications match,
        // Android displays a system message.So here there is no need for try-catch.
        startActivity(Intent.createChooser(intent, "Browse with"));
    }

    public void onDailerClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onCameraClick(View view) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onChromeClick(View view) {
        Intent launchGoogleChrome = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
        startActivity(launchGoogleChrome);
    }


    public void onWazeClick(View view) {
        try {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.waze");
            startActivity(launchIntent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.waze.com/"));
            // Note the Chooser below. If no applications match,
            // Android displays a system message.So here there is no need for try-catch.
            startActivity(Intent.createChooser(intent, "Browse with"));

        }
    }

    public void onWhatsappClick(View view) {
        try {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            startActivity(launchIntent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com/"));
            // Note the Chooser below. If no applications match,
            // Android displays a system message.So here there is no need for try-catch.
            startActivity(Intent.createChooser(intent, "Browse with"));

        }
    }


    public void onGmailClick(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {

        }
    }

    public void onCalendarClick(View view) {
        try {
            long startMillis =0;

            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, startMillis);
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {

        }
    }

    public void onYoutubeClick(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com"));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
            // Note the Chooser below. If no applications match,
            // Android displays a system message.So here there is no need for try-catch.
            startActivity(Intent.createChooser(intent, "Browse with"));

        }
    }

    public void onCalculatorClick(View view) {
        try {
            ArrayList<HashMap<String,Object>> items =new ArrayList<HashMap<String,Object>>();
            PackageManager pm;
            final PackageManager p = getPackageManager();
            List<PackageInfo> packs = p.getInstalledPackages(0);
            for (PackageInfo pi : packs) {
                if( pi.packageName.toString().toLowerCase().contains("calcul")){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("appName", pi.applicationInfo.loadLabel(p));
                    map.put("packageName", pi.packageName);
                    items.add(map);
                }
            }
            if(items.size()>=1){
                String packageName = (String) items.get(0).get("packageName");
                Intent i = p.getLaunchIntentForPackage(packageName);
                if (i != null)
                    startActivity(i);
            }
            else{
                // Application not found
            }

        } catch (android.content.ActivityNotFoundException anfe) {

        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if (mAppsFragment == null || mHomeFragment == null) {
            return;
        }

        if (mAppsFragment.getIsVisibleNow() && mHomeFragment.getIsVisibleNow() == false ) {
            loadFragment(mHomeFragment);
        }
        else if ((mAppsFragment.getIsVisibleNow() == false) && (mHomeFragment.getIsVisibleNow() == false) ) {
            loadFragment(mAppsFragment);
        }
    }

    @Override
    protected void onPause(){

        super.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }
}