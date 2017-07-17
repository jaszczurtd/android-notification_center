package profi_soft.com.notificationcenter_android;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import profi_soft.com.notificationcenter_android.notification_center.Config;
import profi_soft.com.notificationcenter_android.notification_center.NotificationCenter;
import profi_soft.com.notificationcenter_android.notification_center.NotificationCenterInterface;
import profi_soft.com.notificationcenter_android.notification_center.helpers.MutableDictionary;

public class MainActivity extends AppCompatActivity {

    private List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //start the thread
        NotificationCenter.init();

        final View surface = View.inflate(MainActivity.this, R.layout.activity_main, null);
        setContentView(surface);

        ListView myList = (ListView) surface.findViewById(R.id.surface_view);

        for(int a = 0; a < Config.CONTENT_SIZE; a++){
            items.add(String.format(Locale.getDefault(), getString(R.string.item_counter), a));
        }

        Button postNotificationFromMainActivity = (Button) surface.findViewById(R.id.button_send_notifications_to_items);
        postNotificationFromMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCenter.post(Config.NOTIFICATION_FROM_MAIN_ACTIVITY, null, null);
            }
        });

        NotificationCenter.addObserver(Config.NOTIFICATION_FROM_LIST, this, new NotificationCenterInterface() {
            @Override
            public void onReceiveNotification(String name, Object object, final Object userInfo) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        TextView gotMessageTextView = (TextView) surface.findViewById(R.id.display_message);
                        gotMessageTextView.setText(String.format(Locale.getDefault(), getString(R.string.got_message_from_item), (Integer)userInfo));
                    }
                });
            }
        });

        MainActivity.ExampleListAdapter myListAdapter = new MainActivity.ExampleListAdapter();
        myList.setAdapter(myListAdapter);
        myListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        //remove notification thread, and all pending notifications
        NotificationCenter.removeObserver(this);
        NotificationCenter.exit();
    }

    private class ExampleListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String listItem = items.get(position);
            ListSingleItem cell = new ListSingleItem(listItem, MainActivity.this, parent, position);
            cell.setupObserver();

            return cell.view;
        }
    }


}
