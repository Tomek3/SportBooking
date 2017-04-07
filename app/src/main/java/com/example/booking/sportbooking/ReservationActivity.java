package com.example.booking.sportbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReservationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(1).setChecked(true);
        setTitle(R.string.nav_item_reservation);
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }
}
