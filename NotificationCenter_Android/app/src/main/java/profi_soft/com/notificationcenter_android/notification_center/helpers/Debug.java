package profi_soft.com.notificationcenter_android.notification_center.helpers;

import profi_soft.com.notificationcenter_android.BuildConfig;

/**
 * For debug purposes
 * Created by Marcin Kielesiński on 17.07.2017.
 */

public interface Debug {
    boolean ENABLED = BuildConfig.DEBUG;
    boolean DEEP = (ENABLED && false);
}
