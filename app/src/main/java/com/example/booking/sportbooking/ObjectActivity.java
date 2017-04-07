package com.example.booking.sportbooking;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ObjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(0).setChecked(true);
        setTitle(R.string.nav_item_objects);
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }
}
