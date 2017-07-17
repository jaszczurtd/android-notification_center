package profi_soft.com.notificationcenter_android.notification_center.helpers;

import android.util.Log;

/**
 * debug
 * Created by Marcin Kielesiński on 17.07.2017.
 */

public class d {
    public static String TAG;

    public static void deb(String d){

        if(TAG == null){
            TAG = new d().getClass().getPackage().getName();
        }

        Log.v(TAG, d);
    }
}
