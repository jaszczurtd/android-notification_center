package profi_soft.com.notificationcenter_android.notification_center.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * NotificationCenter Android
 * Created by Marcin Kielesiński on 07.07.2017.
 */

@SuppressWarnings("unused")
public class MutableDictionary {
    private Map<Object, Object> map = new HashMap<>();

    public void removeAllObjects(){
        map = new HashMap<>();
    }

    public void setObject(Object value,  Object key){
        if(key != null && value != null){
            map.put(key, value);
        }
    }

    public Object objectForKey(Object key){
        if(key != null){
            return map.get(key);
        }
        return null;
    }

    public int count() {
        return map.size();
    }

    public MutableDictionary copy() {

        MutableDictionary cp = new MutableDictionary();
        cp.map = new HashMap<>(map);
        return cp;
    }

    public MutableDictionary mutableCopy(){
        return copy();
    }
}