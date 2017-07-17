package profi_soft.com.notificationcenter_android;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import profi_soft.com.notificationcenter_android.notification_center.Config;
import profi_soft.com.notificationcenter_android.notification_center.NotificationCenter;
import profi_soft.com.notificationcenter_android.notification_center.NotificationCenterInterface;

/**
 * NotificationCenter - single item list
 * Created by Marcin Kielesiński on 17.07.2017.
 */

class ListSingleItem {
    View view;

    ListSingleItem(String listItem, Context context, ViewGroup parent, final int position){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = inflater.inflate(R.layout.list_single_item, parent, false);

        TextView itemToNotify = (TextView) view.findViewById(R.id.textView);
        itemToNotify.setText(listItem);

        Button answerButton = (Button) view.findViewById(R.id.button);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCenter.post(Config.NOTIFICATION_FROM_LIST, null, position);
            }
        });
    }

    void setupObserver() {
        NotificationCenter.addObserver(Config.NOTIFICATION_FROM_MAIN_ACTIVITY, this, new NotificationCenterInterface() {
            @Override
            public void onReceiveNotification(String name, Object object, final Object userInfo) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        TextView itemToNotify = (TextView) view.findViewById(R.id.textView);
                        itemToNotify.setText(R.string.received_notification);
                    }
                });
            }
        });
    }
}
