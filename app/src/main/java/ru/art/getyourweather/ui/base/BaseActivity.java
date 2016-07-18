package ru.art.getyourweather.ui.base;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

  public boolean isConnected(){
    ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext()
        .getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected())
      return true;
    else
      return false;
  }

}
