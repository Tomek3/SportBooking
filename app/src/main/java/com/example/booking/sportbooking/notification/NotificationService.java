package com.example.booking.sportbooking.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.object.ReservationObject;
import com.example.booking.sportbooking.objectItem.ObjectItemActionActivity;
import com.example.booking.sportbooking.objectItem.ReservationObjectItem;
import com.example.booking.sportbooking.reservation.Reservation;
import com.example.booking.sportbooking.reservation.ReservationActivity;
import com.example.booking.sportbooking.reservation.ReservationAdapter;
import com.example.booking.sportbooking.service.ApiClient;
import com.example.booking.sportbooking.service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_WATCH = "com.example.booking.sportbooking.notification.action.WATCH";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.booking.sportbooking.notification.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.booking.sportbooking.notification.extra.PARAM2";

    public NotificationService() {
        super("NotificationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionWatch(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_WATCH);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void stopActionWatch(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_WATCH);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.stopService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WATCH.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionWatch(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWatch(String param1, String param2) {
        try {
            do {
                Thread.sleep(10000);

                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                Integer userId = settings.getInt("UserId", -1);

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<List<Notification>> call = apiService.doGetListUserNotification(userId.toString());
                call.enqueue(new Callback<List<Notification>>() {
                    @Override
                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                        List<Notification> notificationList = response.body();

                        for (Notification notification : notificationList) {
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            if (notification.getReservationId() != null && notification.getReservationId() > 0) {
                                Intent intent = new Intent(getBaseContext(), ReservationActivity.class);
                                PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), (int) System.currentTimeMillis(), intent, 0);

                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(getBaseContext())
                                                .setSmallIcon(R.drawable.ic_notification_icon)
                                                .setContentTitle(getString(R.string.incomingReservation))
                                                .setContentText(String.format("%s - %s", notification.getObjectName(), notification.getDateFrom().substring(0, notification.getDateFrom().length() - 2)))
                                                .setContentIntent(pIntent)
                                                .setAutoCancel(true);


                                mNotifyMgr.notify(notification.getId(), mBuilder.build());
                            } else {
                                ReservationObjectItem roi = new ReservationObjectItem();
                                roi.setId(notification.getReservationObjectItemId());
                                roi.setDateFrom(notification.getDateFrom());
                                roi.setDateTo(notification.getDateTo());
                                roi.setAvailable(true);

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("reservationObjectItem", roi);
                                bundle.putString("objectName", notification.getObjectName());

                                Intent intent = new Intent(getBaseContext(), ObjectItemActionActivity.class);
                                intent.putExtras(bundle);
                                PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), (int) System.currentTimeMillis(), intent, 0);

                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(getBaseContext())
                                                .setSmallIcon(R.drawable.ic_notification_icon)
                                                .setContentTitle(getString(R.string.watchingReservationAvailable))
                                                .setContentText(String.format("%s - %s", notification.getObjectName(), notification.getDateFrom().substring(0, notification.getDateFrom().length() - 2)))
                                                .setContentIntent(pIntent)
                                                .setAutoCancel(true);


                                mNotifyMgr.notify(notification.getId(), mBuilder.build());

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Notification>> call, Throwable t) {
                        //Toast.makeText(getApplicationContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                    }
                });
            }while(true);

//            Intent intent = new Intent(this, ReservationActivity.class);
//            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.drawable.ic_notification_icon)
//                            .setContentTitle("My notification")
//                            .setContentText("Hello World!")
//                            .setContentIntent(pIntent)
//                            .setAutoCancel(true);
//
//
//            int mNotificationId = 001;
//
//            NotificationManager mNotifyMgr =
//                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//            mNotifyMgr.notify(mNotificationId, mBuilder.build());

        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }
}
