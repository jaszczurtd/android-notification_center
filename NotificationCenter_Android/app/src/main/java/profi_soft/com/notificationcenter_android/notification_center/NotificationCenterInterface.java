package profi_soft.com.notificationcenter_android.notification_center;

/**
 * NotificationCenter Android
 * Created by Marcin Kielesiński on 11.07.2017.
 */

public interface NotificationCenterInterface {
    void onReceiveNotification(String name, Object object, Object userInfo);
}
