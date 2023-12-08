package com.example.loginapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ContentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);

            if (isAirplaneModeOn) {
                // Airplane mode is ON
                Toast.makeText(context, "LoginApp:Airplane mode is ON", Toast.LENGTH_SHORT).show();

                Log.d("RECEIVER_MESSAGE","Airplane mode is ON");
            } else {
                // Airplane mode is OFF
                Toast.makeText(context, "LoginApp:Airplane mode is OFF", Toast.LENGTH_SHORT).show();
                Log.d("RECEIVER_MESSAGE","Airplane mode is OFF");
            }
        }else if(intent.getAction()!=null && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context, "LoginApp:POWER CONNECTED", Toast.LENGTH_SHORT).show();
        }else if(intent.getAction()!=null && intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            Toast.makeText(context, "LoginApp:POWER DISCONNECTED", Toast.LENGTH_SHORT).show();
        }else if(intent.getAction()!=null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Toast.makeText(context, "LoginApp:BOOT COMPLETED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "LoginApp:RANDOM ACTION", Toast.LENGTH_SHORT).show();
        }
        Log.d("RECEIVER_MESSAGE","INSIDE_RECEIVER");
    }
}
