package com.poc.nstartlauncher;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import able.endpoint.android.AbleSDK;

public class NStartApplication extends Application {
    private final String REFERRAL_KEY = "referralSent";
    @Override
    public void onCreate() {
        super.onCreate();

        AbleSDK.init(getApplicationContext(), "nstartlauncher1", true);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sp.getBoolean(REFERRAL_KEY, false)) {

            InstallReferrerClient client = InstallReferrerClient.newBuilder(this).build();
            client.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    try {
                        if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                            String installReferrer = client.getInstallReferrer().getInstallReferrer();
                            String cid = "1";
                            String pid = "1";
                            String version = BuildConfig.VERSION_NAME;
                            String state = "ai";

                            String[] params = installReferrer.split("&");

                            for (String param : params){
                                String[] keyValue = param.split("=");
                                String key = keyValue[0];
                                String value = keyValue[1];

                                switch (key){
                                    case "cid":
                                        cid = value;
                                        break;
                                    case "pid":
                                        pid = value;
                                        break;
                                }
                            }

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                            String url = getString(R.string.pixel_url, pid, cid, version, state);
                            StringRequest req = new StringRequest(Request.Method.GET, url,
                                    (Response.Listener<String>) response -> {}, (Response.ErrorListener) error -> { });

                            queue.add(req);
                        }
                    } catch (RemoteException e) {
                        Log.e("onInstallReferrerSetupFinished", e.getMessage());
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {

                }
            });

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(REFERRAL_KEY, true);
            editor.commit();
        }
    }
}
