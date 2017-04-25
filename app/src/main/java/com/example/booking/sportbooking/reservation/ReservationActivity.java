package com.example.booking.sportbooking.reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.booking.sportbooking.BaseActivity;
import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.object.ReservationObject;
import com.example.booking.sportbooking.object.ReservationObjectAdapter;
import com.example.booking.sportbooking.objectItem.ObjectItemActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(1).setChecked(true);
        setTitle(R.string.nav_item_reservation);

        final ListView listView = new ListView(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.flContent);
        frameLayout.addView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservation selectedReservation = (Reservation)parent.getAdapter().getItem(position);
                //TO DO: delete reservation
            }
        });

        prgDialog.show();

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        Integer userId = settings.getInt("UserId",-1);

        Call<List<Reservation>> call = apiService.doGetListUserReservation(userId.toString());
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>>call, Response<List<Reservation>> response) {
                List<Reservation> reservations = response.body();

                ReservationAdapter adapter = new ReservationAdapter(getBaseContext(), reservations);
                listView.setAdapter(adapter);
                prgDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Reservation>>call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                prgDialog.hide();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }
}
