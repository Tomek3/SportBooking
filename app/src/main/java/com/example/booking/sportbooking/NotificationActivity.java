package com.example.booking.sportbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(2).setChecked(true);
        setTitle(R.string.nav_item_watched);
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }
}
