package com.faisalnazir.project;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

    public class NotificationHandler implements OneSignal.OSNotificationOpenedHandler {

        Context context;

        public NotificationHandler(Context context) {
            this.context = context;
        }


        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            OSNotificationAction.ActionType actionType = result.getAction().getType();
            JSONObject data = result.getNotification().getAdditionalData();
            String customKey = "";


            if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.e("OneSignalExample", "customkey set with value: " + customKey);
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.getAction().getActionId());
            if (!customKey.isEmpty()) {
                Intent intent = new Intent(context, UrlActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("address", customKey);
                Toast.makeText(context,"this block is reached",Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }

        }

    }

