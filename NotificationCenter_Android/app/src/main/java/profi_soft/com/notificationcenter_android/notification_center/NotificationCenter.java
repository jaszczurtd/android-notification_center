package profi_soft.com.notificationcenter_android.notification_center;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import profi_soft.com.notificationcenter_android.notification_center.helpers.Debug;
import profi_soft.com.notificationcenter_android.notification_center.helpers.MutableDictionary;

/**
 * NotificationCenter - Android
 * Created by Marcin Kielesiński on 11.07.2017.
 */

public class NotificationCenter {

    private static final String NOTIFY_T_NAME           = "NOTIFY_T_NAME";
    private static final String NOTIFY_T_TARGET         = "NOTIFY_T_TARGET";
    private static final String NOTIFY_T_HANDLER        = "NOTIFY_T_HANDLER";
    private static final String NOTIFY_T_TARGET_NAME    = "NOTIFY_T_TARGET_NAME";

    private static final String NOTIFICATION_NAME       = "NOTIFICATION_NAME";
    private static final String NOTIFICATION_OBJECT     = "NOTIFICATION_OBJECT";
    private static final String NOTIFICATION_USER_DATA  = "NOTIFICATION_USER_DATA";

    private static boolean isRunned = false;
    private static List<Object> notificationObjects = new CopyOnWriteArrayList<>();
    private static List<Object> notificationTargets = new CopyOnWriteArrayList<>();

    public static void init(){

        isRunned = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunned) {
                    try {
                        for(Object notification: notificationObjects){

                            MutableDictionary entry = (MutableDictionary)notification;
                            String notificationName = (String)entry.objectForKey(NOTIFICATION_NAME);

                            for(Object e : notificationTargets){

                                MutableDictionary target = (MutableDictionary)e;
                                if(target.objectForKey(NOTIFY_T_NAME).equals(notificationName)){

                                    NotificationCenterInterface handler = (NotificationCenterInterface)target.objectForKey(NOTIFY_T_HANDLER);
                                    handler.onReceiveNotification(notificationName,
                                            entry.objectForKey(NOTIFICATION_OBJECT),
                                            entry.objectForKey(NOTIFICATION_USER_DATA));
                                }
                            }
                            notificationObjects.remove(notification);
                        }

                        Thread.sleep(1);
                        Thread.yield();
                    } catch (Exception e) {
                        if (Debug.ENABLED) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();
    }

    public static void exit(){

        notificationObjects.clear();
        notificationTargets.clear();
        notificationObjects = null;
        notificationTargets = null;
        isRunned = false;
        System.gc();
    }


    public static void post(String name, Object object, Object userData){

        MutableDictionary notification = new MutableDictionary();
        notification.setObject(name, NOTIFICATION_NAME);
        notification.setObject(object, NOTIFICATION_OBJECT);
        notification.setObject(userData, NOTIFICATION_USER_DATA);

        notificationObjects.add(notification);
    }

    public static void addObserver(String name, Object target, NotificationCenterInterface handler){

        MutableDictionary entry = new MutableDictionary();
        entry.setObject(name, NOTIFY_T_NAME);
        entry.setObject(target, NOTIFY_T_TARGET);
        entry.setObject(handler, NOTIFY_T_HANDLER);
        entry.setObject(target.getClass().getSimpleName(), NOTIFY_T_TARGET_NAME);

        notificationTargets.add(entry);
    }

    public static void removeObserver(Object target){
        String targetName = target.getClass().getSimpleName();

        for(Object e : notificationTargets){

            MutableDictionary entry = (MutableDictionary)e;
            if(entry.objectForKey(NOTIFY_T_TARGET_NAME).equals(targetName)){
                notificationTargets.remove(e);
            }
        }
    }

}
