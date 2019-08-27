package buradayim.lyadirga.com.buradayim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class AyarlarActivity extends PreferenceActivity implements
    OnSharedPreferenceChangeListener {

  private SharedPreferences preferences;



  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.ayarlar);

    preferences= PreferenceManager.getDefaultSharedPreferences(this);
    preferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    boolean isKonum = sharedPreferences.getBoolean("konumSwitch",false);

    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent servisIntent = new Intent(this , KonumServisi.class);
    PendingIntent pi = PendingIntent.getService(this, TakipListesiActivity.ALARM_MUHRU, servisIntent, 0);

    if (isKonum){

      if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pi);
      }else {
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,pi);
      }
    }else {
      alarmManager.cancel(pi);
    }
  }
}
